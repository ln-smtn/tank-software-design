package ru.mipt.bit.platformer.model;

// Перечисление (enum) для направления движения.
// У каждого направления есть:
// dx, dy — приращение координат (например, вверх = (0,1))
// rotation — угол поворота танка для отрисовки.
// Используем GridPoint2 в качестве целочисленного вектора (совместимо с логикой тайлов).
import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0f);

    public final int dx;        // смещение по X
    public final int dy;        // смещение по Y
    public final float rotation; // угол поворота в градусах


    // конструктор enum
    Direction(int dx, int dy, float rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }

    // Утилита: вернуть смещение как GridPoint2 (удобно при вычислениях)
    public GridPoint2 toGridPoint() {
        return new GridPoint2(dx, dy); // создаём и возвращаем новую точку
    }
}