package ru.mipt.bit.platformer.model;
import com.badlogic.gdx.math.GridPoint2;

public interface WorldObject {
    String getId();
    ObjectType getType();
    boolean isRemovable();
    void live(float delta); // обновление состояния
    GridPoint2 getCoordinates(); // координаты
}
