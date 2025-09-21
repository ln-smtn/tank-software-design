package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

// Класс отвечает за работу с уровнем (картой):
// - загрузка TMX-файла
// - рендер карты
// - хранение TileMovement для движения объектов
public class GameField {
    private final TiledMap level;         // сама карта
    private final MapRenderer renderer;   // отрисовщик карты
    private final TileMovement movement;  // отвечает за плавное движение по тайлам
    private final TiledMapTileLayer groundLayer;

    public GameField(Batch batch) {
        // Загружаем карту из файла level.tmx
        level = new TmxMapLoader().load("level.tmx");

        // Создаем рендер только для одного слоя
        renderer = createSingleLayerMapRenderer(level, batch);

        // Берем единственный слой карты
        groundLayer = getSingleLayer(level);

        // Создаем объект для интерполяции движения по клеткам
        movement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    // Даем доступ к TileMovement (нужен Tank)
    public TileMovement getMovement() {
        return movement;
    }


    // Отрисовываем карту (каждый кадр)
    public void render() {
        renderer.render();
    }

    // Освобождаем ресурсы (при выходе из игры)
    public void dispose() {
        level.dispose();
    }
}
