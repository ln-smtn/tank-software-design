package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;                 // Текстура для дерева
import com.badlogic.gdx.graphics.g2d.Batch;               // Batch для отрисовки
import com.badlogic.gdx.graphics.g2d.TextureRegion;       // Region над текстурой
import com.badlogic.gdx.math.Rectangle;                   // Rectangle для позиции/размеров
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;     // Для размещения в центре тайла
import ru.mipt.bit.platformer.model.TreeModel;            // Модель дерева — просто координаты

import static ru.mipt.bit.platformer.util.GdxGameUtils.*; // Утилиты: createBoundingRectangle, moveRectangleAtTileCenter, drawTextureRegionUnscaled

/**
 * View для дерева — статического препятствия.
 * Сразу позиционирует rectangle в центре заданного тайла при создании.
 */
public class TreeView {

    private final Texture texture;         // текстура дерева
    private final TextureRegion graphics;  // region
    private final Rectangle rectangle;     // область для рисования
    private final TreeModel model;         // модель дерева (позиция)
    private final Batch batch;

    // Конструктор: принимает groundLayer, чтобы правильно вычислить позицию в пикселях
    public TreeView(TreeModel model, Batch batch, TiledMapTileLayer groundLayer) {
        this.model = model; // сохраняем модель
        this.batch = batch;

        this.texture = new Texture("images/greenTree.png"); // загружаем текстуру
        this.graphics = new TextureRegion(texture); // region
        this.rectangle = createBoundingRectangle(graphics); // создаём rectangle по размеру графики

        // Размещаем rectangle в центре тайла (нулевая прогрессия, статично)
        moveRectangleAtTileCenter(groundLayer, rectangle, model.getCoordinates());
    }

    // Рисуем дерево — здесь нет динамики, поэтому просто рисуем по rectangle
    public void render() {
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f); // rotation = 0f (дерево не вращается)
    }

    public void dispose() {
        texture.dispose();
    }
}

