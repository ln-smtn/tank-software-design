package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface GameObject {
    String getId();
    ObjectType getType();
    GridPoint2 getPosition();
    boolean isRemovable();
    void update(float deltaTime);
}
