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
import ru.mipt.bit.platformer.view.TankViewWithHealth;
import ru.mipt.bit.platformer.view.TreeView;
import ru.mipt.bit.platformer.input.RandomTankController;
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
    private InputHandler inputHandler;   // система ввода
    private List<TreeView> treeViews;
    private List<RandomTankController> aiControllers;
    private boolean showHealthBars = false;
    private GameWorld world;


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

        LevelLoader loader = new LevelLoader(10, 8);
        LevelLoader.LevelData levelData;

        try {
            boolean useFile = true; // true — из файла, false — случайно
            if (useFile) {
                levelData = loader.loadFromFile("level.txt");
            } else {
                levelData = loader.generateRandom(8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке уровня", e);
        }
        world = new GameWorld(levelData.obstacles, loader.getWidth(), loader.getHeight());

        // Создаём танк игрока
        tankModel = new TankModel(levelData.playerStart, 5f);


        world.addTank(tankModel);
        tankView = new TankView(tankModel, batch, gameField.getMovement());

        // Создаём несколько AI-танков
        aiControllers = new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < 3; i++) { // три ИИ-танка
            GridPoint2 pos;
            do {
                pos = new GridPoint2(rand.nextInt(loader.getWidth()), rand.nextInt(loader.getHeight()));
            } while (world.isBlocked(pos, null));
            TankModel aiTank = new TankModel(pos, 5f);
            world.addTank(aiTank);
            aiControllers.add(new RandomTankController(aiTank, world));
        }


            // Создаём TreeView для всех деревьев
        treeViews = new ArrayList<>();
        for (Obstacle o : levelData.obstacles) {
            TreeModel tree = (TreeModel) o;
            treeViews.add(new TreeView(tree, batch, gameField.getGroundLayer()));
        }

        // Проверка коллизий через весь мир (границы + препятствия + танки)
        CollisionChecker collisionChecker = world::isBlocked;


        // Настройка ввода
        inputHandler = new InputHandler();
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
            if (Gdx.input.isKeyPressed(key)) pressedKeys.add(key);
        }
        inputHandler.handlePressedKeys(pressedKeys);

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            showHealthBars = !showHealthBars;
        }

        // обновляем логику танка
        tankModel.update(deltaTime);
        for (RandomTankController ai : aiControllers) {
            ai.update(deltaTime);
        }
        // рисуем карту
        gameField.render();

        //  начинаем batch ДО всех render()
        batch.begin();

        // рисуем деревья
        for (TreeView tv : treeViews) tv.render();

        // рисуем танки
        for (TankModel t : world.getTanks()) {
            if (showHealthBars) {
                new TankViewWithHealth(t, batch, gameField.getMovement()).render();
            } else {
                new TankView(t, batch, gameField.getMovement()).render();
            }
        }

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
        for (TreeView tv : treeViews) tv.dispose(); // корректная очистка всех деревьев
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
