package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

// Класс "дерево" — статическое препятствие на карте
public class Tree {
    private final Texture texture;       // текстура дерева
    private final TextureRegion graphics;// часть текстуры (можно анимировать при желании)
    private final Rectangle rectangle;   // область для отрисовки
    private final GridPoint2 coordinates;// координаты на сетке (тайлы)

    public Tree(TiledMapTileLayer groundLayer, GridPoint2 coordinates) {
        this.texture = new Texture("images/greenTree.png");
        this.graphics = new TextureRegion(texture);
        this.coordinates = new GridPoint2(coordinates);

        // Создаем прямоугольник и помещаем его в центр нужного тайла
        this.rectangle = createBoundingRectangle(graphics);
        moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    // Отрисовка дерева
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }

    // Освобождение ресурсов
    public void dispose() {
        texture.dispose();
    }
}
