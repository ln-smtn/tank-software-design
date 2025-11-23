package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.ShootCommand;
import ru.mipt.bit.platformer.graphics.*;
import ru.mipt.bit.platformer.keyboard.GdxKeyboardListener;
import ru.mipt.bit.platformer.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.keyboard.RandomTankController;
import ru.mipt.bit.platformer.levelloader.FileLevelGenerator;
import ru.mipt.bit.platformer.levelloader.LevelGenerator;
import ru.mipt.bit.platformer.levelloader.RandomLevelGenerator;
import ru.mipt.bit.platformer.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameDesktopLauncher extends ApplicationAdapter {

    private Batch batch;
    private GameField gameField;

    private Level level;
    private CollisionManager collisionManager;

    private LevelGraphics levelGraphics;
    private UIState uiState;

    private KeyboardListener keyboard;
    private Tank playerTank;

    private final List<RandomTankController> aiControllers = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();

        // карта TMX + слой
        gameField = new GameField(batch);
        TiledMapTileLayer groundLayer = gameField.getGroundLayer();

        // логический уровень
        level = new Level();

        // УСТАНАВЛИВАЕМ РАЗМЕРЫ УРОВНЯ ПО КАРТЕ !!!
        int mapWidth  = groundLayer.getWidth();   // количество тайлов по X
        int mapHeight = groundLayer.getHeight();  // количество тайлов по Y
        level.setBounds(mapWidth, mapHeight);

        // графический уровень
        levelGraphics = new LevelGraphics();
        uiState = new UIState();

        // Подписываемся на события создания/удаления объектов
        level.addListener(Tank.class,
                new OnNewTankGraphicsObserver(levelGraphics, batch, groundLayer));

        level.addListener(Bullet.class,
                new OnShootingObserver(levelGraphics, batch, groundLayer));

        level.addListener(Tree.class, new Observer<Tree>() {
            @Override
            public void onCreated(Tree tree) {
                levelGraphics.add(tree.getId(),
                        new TreeGraphics(tree, batch, groundLayer));
            }

            @Override
            public void onRemoved(Tree tree) {
                levelGraphics.remove(tree.getId());
            }
        });

        // --- генерация уровня ---

        // 1) фиксированная часть из файла
        LevelGenerator fileGen = new FileLevelGenerator("level.txt");
        fileGen.generate(level);

        // 2) случайные враги – используем размеры карты!
        int enemyCount = 3;
        LevelGenerator randomGen =
                new RandomLevelGenerator(mapWidth, mapHeight, enemyCount);
        randomGen.generate(level);

        // применяем добавленные объекты и создаём им графику
        level.applyPendingChanges();

        // находим игрока (первый танк из файла)
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                playerTank = (Tank) obj;
                break;
            }
        }

        // AI для остальных танков
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank && obj != playerTank) {
                aiControllers.add(new RandomTankController((Tank) obj, level));
            }
        }

        collisionManager = new CollisionManager(level);

        // управление игроком
        keyboard = new GdxKeyboardListener();
        keyboard.bindMoveUp(new MoveCommand(playerTank, Direction.UP, level));
        keyboard.bindMoveDown(new MoveCommand(playerTank, Direction.DOWN, level));
        keyboard.bindMoveLeft(new MoveCommand(playerTank, Direction.LEFT, level));
        keyboard.bindMoveRight(new MoveCommand(playerTank, Direction.RIGHT, level));
        keyboard.bindShoot(new ShootCommand(playerTank, level, 20));
        keyboard.bindToggleHealth(new ToggleHealthCommand(
                uiState, levelGraphics, level, batch, groundLayer));
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        // 1–2. команды от игрока и ИИ
        keyboard.update();
        for (RandomTankController ai : aiControllers) {
            ai.updateRandom();
        }

        // 3. логика
        level.update(dt);
        collisionManager.resolve();
        level.applyPendingChanges();

        // 4. отрисовка
        gameField.render();
        batch.begin();
        levelGraphics.renderAll();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameField.dispose();
        levelGraphics.disposeAll();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setWindowedMode(1280, 720);
        cfg.setTitle("Platformer");
        new Lwjgl3Application(new GameDesktopLauncher(), cfg);
    }
}
