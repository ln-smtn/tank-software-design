package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.input.*;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.view.LevelViewObserver;

import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private GameField gameField;
    private InputHandler inputHandler;
    private GameWorld world;
    private CollisionManager collisionManager;
    private LevelViewObserver levelViewObserver;
    private TankModel playerTank;
    private List<RandomTankController> aiControllers;

    @Override
    public void create() {
        // --- Инициализация графики ---
        batch = new SpriteBatch();
        gameField = new GameField(batch);

        // --- Загрузка уровня ---
        LevelLoader loader = new LevelLoader(10, 8);
        LevelLoader.LevelData levelData;
        try {
            levelData = loader.loadFromFile("level.txt");
        } catch (java.io.IOException e) {
            throw new RuntimeException("Ошибка загрузки уровня", e);
        }

        // --- Создание игрового мира (только логика, без графики) ---
        world = new GameWorld();

        // --- Игрок ---
        playerTank = new TankModel(levelData.playerStart);
        world.enqueueAdd(playerTank);

        // --- AI танки ---
        aiControllers = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            GridPoint2 pos;
            do {
                pos = new GridPoint2(r.nextInt(loader.getWidth()), r.nextInt(loader.getHeight()));
            } while (world.isBlocked(pos, null));
            TankModel ai = new TankModel(pos);
            world.enqueueAdd(ai);
            aiControllers.add(new RandomTankController(ai, world));
        }

        // --- Применяем добавленные объекты ---
        world.applyPendingChanges();

        // --- Графический уровень (View) ---
        // LevelViewObserver только читает состояние моделей, не меняет их
        levelViewObserver = new LevelViewObserver(batch);
        world.addObserver(levelViewObserver);

        // --- Управление столкновениями ---
        collisionManager = new CollisionManager(world);

        // --- Ввод игрока ---
        inputHandler = new InputHandler();
        new StandardTankController(playerTank, world).registerControls(inputHandler);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        // --- Логика: обновление мира ---
        world.updateAll(dt);

        // --- AI танки ---
        for (RandomTankController ai : aiControllers) {
            ai.updateRandom();
        }

        // --- Решение коллизий ---
        collisionManager.resolveCollisions();
        world.applyPendingChanges();

        // --- Рендер ---
        gameField.render();          // фон и тайлы
        batch.begin();
        levelViewObserver.renderAll(); // рисует только View объектов
        batch.end();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {
        gameField.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setWindowedMode(1280, 720);
        new Lwjgl3Application(new GameDesktopLauncher(), cfg);
    }
}
