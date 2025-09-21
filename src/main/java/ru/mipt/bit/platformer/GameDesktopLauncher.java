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

//Константы клавиш
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    // скорость перемещения
    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch; //инструмент отрисовки спрайтов

   /*
      Теперь в основном классе НЕ храним текстуры/rectangle игрока и дерева напрямую.
      Вынесли в отдельные классы, чтобы убрать смешение ответственности.
    */
    private GameField gameField; // отвечает за загрузку level.tmx, рендер и TileMovement
    private Tank tank;           // наш игрок (раньше часть логики была прямо в GameDesktopLauncher)
    private Tree tree;           // препятствие (раньше тоже было в GameDesktopLauncher)

    @Override
    // один раз при запуске
    public void create() {

        batch = new SpriteBatch();
        // Создаем уровень (карта и движение по тайлам)
        gameField = new GameField(batch);

        // Создаем игрока в клетке (1,1)
        tank = new Tank(new GridPoint2(1, 1));

        // Создаем дерево в клетке (1,3)
        tree = new Tree(gameField.getGroundLayer(), new GridPoint2(1, 3));
    }

    @Override
    // вызывается постоянно, ввод, обновление, отрисовка
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();


        // Обработка ввода без copy-paste
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            tank.requestMove(Direction.UP, tree);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            tank.requestMove(Direction.DOWN, tree);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            tank.requestMove(Direction.LEFT, tree);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            tank.requestMove(Direction.RIGHT, tree);
        }

        // Обновляем движение игрока
        tank.update(gameField.getMovement(), deltaTime);

        // Отрисовываем карту
        gameField.render();

        // Отрисовываем игрока и дерево
        batch.begin();
        tank.draw(batch);
        tree.draw(batch);
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
        tree.dispose(); // освобождает greenTreeTexture
        tank.dispose(); // освобождает blueTankTexture
        gameField.dispose();  // освобождает TiledMap
        batch.dispose(); // освобождает SpriteBatch
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
