package ru.mipt.bit.platformer.model;

/**
 * Обобщённый наблюдатель за объектами уровня.
 * T ограничен WorldObject (на схеме GameObject).
 */
public interface Observer<T extends GameObject> {
    void onCreated(T obj);
    void onRemoved(T obj);
}
