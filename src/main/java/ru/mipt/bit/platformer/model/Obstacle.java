package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

// интерфейс препятствия — теперь дерево и другие объекты реализуют его
public interface Obstacle {
    GridPoint2 getCoordinates(); // все препятствия имеют координаты
}

