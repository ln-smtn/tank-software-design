package ru.mipt.bit.platformer.keyboard;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Обработчик ввода — регистрирует действия на определённые клавиши.
 * Вызывает зарегистрированные Runnable при нажатии клавиш.
 */
public class InputHandler {

    private final Map<Integer, List<Runnable>> handlers = new HashMap<>();

    public void register(int keyCode, Runnable action) {
        handlers.computeIfAbsent(keyCode, k -> new CopyOnWriteArrayList<>()).add(action);
    }

    public void unregister(int keyCode, Runnable action) {
        List<Runnable> list = handlers.get(keyCode);
        if (list != null) list.remove(action);
    }

    public void handlePressedKeys(Set<Integer> pressedKeys) {
        if (pressedKeys == null || pressedKeys.isEmpty()) return;
        for (Integer key : pressedKeys) {
            List<Runnable> list = handlers.get(key);
            if (list == null) continue;
            for (Runnable r : list) {
                try {
                    r.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
