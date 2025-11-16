package ru.mipt.bit.platformer.model;

public interface LevelObserver {
    void onObjectAdded(WorldObject obj);
    void onObjectRemoved(WorldObject obj);
}
