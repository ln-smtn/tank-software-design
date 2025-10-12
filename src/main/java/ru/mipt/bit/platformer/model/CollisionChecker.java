package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

// интерфейс проверки, свободна ли клетка
public interface CollisionChecker {
    boolean isBlocked(GridPoint2 position);
}

