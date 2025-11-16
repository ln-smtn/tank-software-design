package ru.mipt.bit.platformer.model;

/**
 * GameWorld будет ObservableLevel — публиковать события добавления/удаления объектов.
 */
public interface ObservableLevel {
    void addObserver(LevelObserver observer);
    void removeObserver(LevelObserver observer);
}
