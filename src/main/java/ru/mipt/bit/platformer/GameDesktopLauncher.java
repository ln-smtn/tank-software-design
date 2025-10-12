package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.StandardTankController;
import ru.mipt.bit.platformer.input.TankController;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import java.util.*;

/**
 * Главный класс запуска игры.
 * Разделяет:
 * - графику (View)
 * - игровую логику (Model)
 * - ввод (InputHandler + Controller)
 */
public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;                 // для отрисовки
    private GameField gameField;         // карта уровня
    private TankModel tankModel;         // логика танка
    private TankView tankView;           // отрисовка танка
    private TreeModel treeModel;         // логика дерева (препятствие)
    private TreeView treeView;           // отрисовка дерева
    private InputHandler inputHandler;   // система ввода

    // Реализация проверки коллизий
    private static class SimpleCollisionChecker implements CollisionChecker {
        private final List<Obstacle> obstacles;

        public SimpleCollisionChecker(List<Obstacle> obstacles) {
            this.obstacles = obstacles;
        }

        @Override
        public boolean isBlocked(GridPoint2 position) {
            return obstacles.stream().anyMatch(o -> o.getCoordinates().equals(position));
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameField = new GameField(batch);

        // Создаём модель танка
        tankModel = new TankModel(new GridPoint2(1, 1));
        tankView = new TankView(tankModel, batch, gameField.getMovement());

        // Создаём модель дерева
        treeModel = new TreeModel(new GridPoint2(1, 3));
        treeView = new TreeView(treeModel, batch, gameField.getGroundLayer());

        // Собираем все препятствия
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(treeModel);

        // Создаём проверку коллизий
        CollisionChecker collisionChecker = new SimpleCollisionChecker(obstacles);

        // Система ввода
        inputHandler = new InputHandler();

        // Создаём контроллер управления танком (стрелки + WASD)
        TankController controller = new StandardTankController(tankModel, collisionChecker);
        controller.registerControls(inputHandler);
    }

    @Override
    public void render() {
        // очищаем экран
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT);

        // время между кадрами
        float deltaTime = Gdx.graphics.getDeltaTime();

        // собираем нажатые клавиши
        Set<Integer> pressedKeys = new HashSet<>();
        for (int key = 0; key < 256; key++) {
            if (Gdx.input.isKeyPressed(key)) {
                pressedKeys.add(key);
            }
        }

        // передаём нажатия в обработчик
        inputHandler.handlePressedKeys(pressedKeys);

        // обновляем логику танка
        tankModel.update(deltaTime);

        // рисуем карту
        gameField.render();

        // рисуем объекты
        batch.begin();
        treeView.render();
        tankView.render();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        treeView.dispose();
        tankView.dispose();
        gameField.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
