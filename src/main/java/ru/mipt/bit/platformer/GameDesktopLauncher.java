package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.commands.PlayerCommands;
import ru.mipt.bit.platformer.graphics.GraphicsInitResult;
import ru.mipt.bit.platformer.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.keyboard.RandomTankController;
import ru.mipt.bit.platformer.levelloader.LevelInitResult;
import ru.mipt.bit.platformer.model.CollisionManager;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class GameDesktopLauncher extends ApplicationAdapter {

    private final Config config = new Config();

    private Batch batch;
    private GameField gameField;

    private Level level;
    private CollisionManager collisionManager;

    private ru.mipt.bit.platformer.graphics.LevelGraphics levelGraphics;
    private ru.mipt.bit.platformer.graphics.UIState uiState;

    private KeyboardListener keyboard;
    private Tank playerTank;

    private List<RandomTankController> aiControllers = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();

        // карта
        gameField = new GameField();
        TiledMapTileLayer groundLayer = gameField.getGroundLayer();

        // 1) initializeLevel (в levelloader)
        LevelInitResult levelInit = config.initializeLevel(groundLayer);
        level = levelInit.getLevel();
        playerTank = levelInit.getPlayerTank();
        aiControllers = levelInit.getAiControllers();

        // 2) initializeGraphics (в graphics)
        GraphicsInitResult gfxInit = config.initializeGraphics(level, batch, groundLayer);
        levelGraphics = gfxInit.getLevelGraphics();
        uiState = gfxInit.getUiState();

        // 3) createCommands (в commands)
        PlayerCommands commands = config.createCommands(
                playerTank, level, levelGraphics, uiState, batch, groundLayer
        );

        // 4) initializeKeyboard (в keyboard)
        keyboard = config.initializeKeyboard(commands);

        // сервис логики столкновений
        collisionManager = new CollisionManager(level);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        // 1–2. команды игрока + ИИ
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
