package ru.mipt.bit.platformer.model;

import java.util.*;

/**
 * Хранит набор слушателей по типу объекта.
 * Соответствует блоку Observable на диаграмме:
 *   addListener(Observer<Class> o, Class type)
 */
public class Observable {

    private final Map<Class<?>, List<Observer<?>>> listeners = new HashMap<>();

    public <T extends GameObject> void addListener(Observer<T> observer, Class<T> type) {
        listeners
                .computeIfAbsent(type, t -> new ArrayList<>())
                .add(observer);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> void notifyCreated(T obj) {
        Class<?> cls = obj.getClass();
        for (var entry : listeners.entrySet()) {
            if (entry.getKey().isAssignableFrom(cls)) {
                for (Observer<?> o : entry.getValue()) {
                    ((Observer<T>) o).onCreated(obj);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> void notifyRemoved(T obj) {
        Class<?> cls = obj.getClass();
        for (var entry : listeners.entrySet()) {
            if (entry.getKey().isAssignableFrom(cls)) {
                for (Observer<?> o : entry.getValue()) {
                    ((Observer<T>) o).onRemoved(obj);
                }
            }
        }
    }
}
