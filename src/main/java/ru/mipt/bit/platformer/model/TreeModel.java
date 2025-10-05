package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2; // класс для хранения целых координат


public class TreeModel {
    private final GridPoint2 coordinates; // позиция дерева (не изменяется)

    public TreeModel(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates); // копируем вход
    }

    // Возвращаем координаты (копия)
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates); // возвращаем копию
    }
}
