package ru.mipt.bit.platformer;

// Перечисление (enum) для направления движения.
// Оно позволяет избавиться от copy-paste при проверке клавиш.
// У каждого направления есть:
// dx, dy — приращение координат (например, вверх = (0,1))
// rotation — угол поворота танка для отрисовки.
public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0f);

    public final int dx;        // смещение по X
    public final int dy;        // смещение по Y
    public final float rotation; // угол поворота в градусах

    Direction(int dx, int dy, float rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }
}