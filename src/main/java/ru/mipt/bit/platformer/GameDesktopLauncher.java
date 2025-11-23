package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.ShootCommand;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.graphics.*;
import ru.mipt.bit.platformer.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.keyboard.RandomTankController;
import ru.mipt.bit.platformer.levelloader.LevelGenerator;
import ru.mipt.bit.platformer.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameDesktopLauncher extends ApplicationAdapter {

    private AnnotationConfigApplicationContext context;

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
        // 1. Поднимаем Spring-контекст
        context = new AnnotationConfigApplicationContext(GameConfig.class);

        // 2. Получаем бины
        batch = context.getBean(Batch.class);
        gameField = context.getBean(GameField.class);
        level = context.getBean(Level.class);
        collisionManager = context.getBean(CollisionManager.class);
        levelGraphics = context.getBean(LevelGraphics.class);
        uiState = context.getBean(UIState.class);
        keyboard = context.getBean(KeyboardListener.class);

        // 3. Генераторы уровня
        LevelGenerator fileGen =
                context.getBean("fileLevelGenerator", LevelGenerator.class);
        LevelGenerator randomGen =
                context.getBean("randomLevelGenerator", LevelGenerator.class);

        // 4. Ground layer
        TiledMapTileLayer groundLayer = gameField.getGroundLayer();

        // 5. Подписываемся на создание объектов
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

        // 6. Генерация уровня
        fileGen.generate(level);
        randomGen.generate(level);
        level.applyPendingChanges();

        // 7. Находим танк игрока
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                playerTank = (Tank) obj;
                break;
            }
        }

        // 8. AI для остальных танков
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank && obj != playerTank) {
                aiControllers.add(new RandomTankController((Tank) obj, level));
            }
        }

        // 9. Биндим команды на клавиатуру
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

        keyboard.update();
        for (RandomTankController ai : aiControllers) {
            ai.updateRandom();
        }

        level.update(dt);
        collisionManager.resolve();
        level.applyPendingChanges();

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

        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setWindowedMode(1280, 720);
        cfg.setTitle("Platformer");
        new Lwjgl3Application(new GameDesktopLauncher(), cfg);
    }
}
