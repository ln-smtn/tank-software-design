package ru.mipt.bit.platformer;

//интерфейс цикла create, render, resize, pause, resume/dispose.
import com.badlogic.gdx.ApplicationListener;
//вывод графика и время
import com.badlogic.gdx.Gdx;
//запуск
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//Batch — интерфейс для отрисовки; SpriteBatch — его реализация.
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//структура хранения координат
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;
import ru.mipt.bit.platformer.input.InputHandler;

//Константы клавиш
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch; //инструмент отрисовки спрайтов
    private GameField gameField; // отвечает за загрузку level.tmx, рендер и TileMovement
    // Model–View для танка
    private TankModel tankModel;
    private TankView tankView;
    // Model–View для дерева
    private TreeModel treeModel;
    private TreeView treeView;
    // простой обработчик ввода
    private InputHandler inputHandler;

    @Override
    // один раз при запуске
    public void create() {
// Создаём спрайтовый Batch (будет передаваться всем View)
        batch = new SpriteBatch();

        // Загружаем карту уровня (level.tmx) и TileMovement
        gameField = new GameField(batch);

        // Создаём модель танка в клетке (1,1)
        tankModel = new TankModel(new GridPoint2(1, 1));

        // Создаём View танка, передаём модель + batch + TileMovement
        tankView = new TankView(tankModel, batch, gameField.getMovement());
        // Создаём модель дерева в клетке (1,3)
        treeModel = new TreeModel(new GridPoint2(1, 3));

        // Создаём View дерева, передаём модель + batch + слой карты
        treeView = new TreeView(treeModel, batch, gameField.getGroundLayer());

        // Создаём обработчик ввода
        inputHandler = new InputHandler();

        // Регистрируем управление стрелками + WASD для движения
        inputHandler.register(UP, () -> tankModel.requestMove(Direction.UP, pos -> pos.equals(treeModel.getCoordinates())));
        inputHandler.register(W, () -> tankModel.requestMove(Direction.UP, pos -> pos.equals(treeModel.getCoordinates())));

        inputHandler.register(DOWN, () -> tankModel.requestMove(Direction.DOWN, pos -> pos.equals(treeModel.getCoordinates())));
        inputHandler.register(S, () -> tankModel.requestMove(Direction.DOWN, pos -> pos.equals(treeModel.getCoordinates())));

        inputHandler.register(LEFT, () -> tankModel.requestMove(Direction.LEFT, pos -> pos.equals(treeModel.getCoordinates())));
        inputHandler.register(A, () -> tankModel.requestMove(Direction.LEFT, pos -> pos.equals(treeModel.getCoordinates())));

        inputHandler.register(RIGHT, () -> tankModel.requestMove(Direction.RIGHT, pos -> pos.equals(treeModel.getCoordinates())));
        inputHandler.register(D, () -> tankModel.requestMove(Direction.RIGHT, pos -> pos.equals(treeModel.getCoordinates())));
    }


    @Override
    // вызывается постоянно, ввод, обновление, отрисовка
    public void render() {
        // Очищаем экран
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // Получаем время между кадрами
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Собираем все нажатые клавиши в Set
        java.util.Set<Integer> pressedKeys = new java.util.HashSet<>();
        if (Gdx.input.isKeyPressed(UP)) pressedKeys.add(UP);
        if (Gdx.input.isKeyPressed(DOWN)) pressedKeys.add(DOWN);
        if (Gdx.input.isKeyPressed(LEFT)) pressedKeys.add(LEFT);
        if (Gdx.input.isKeyPressed(RIGHT)) pressedKeys.add(RIGHT);
        if (Gdx.input.isKeyPressed(W)) pressedKeys.add(W);
        if (Gdx.input.isKeyPressed(S)) pressedKeys.add(S);
        if (Gdx.input.isKeyPressed(A)) pressedKeys.add(A);
        if (Gdx.input.isKeyPressed(D)) pressedKeys.add(D);

        // Передаём их в наш InputHandler
        inputHandler.handlePressedKeys(pressedKeys);

        // Обновляем состояние модели танка
        tankModel.update(deltaTime);

        // Рисуем карту
        gameField.render();

        // Отрисовываем объекты
        batch.begin();
        treeView.render();
        tankView.render();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override

    //при выходе из игры
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        treeView.dispose();
        tankView.dispose();
        gameField.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
