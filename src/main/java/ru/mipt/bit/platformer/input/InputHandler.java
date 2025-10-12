package ru.mipt.bit.platformer.input;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Обработчик ввода — регистрирует действия на определённые клавиши.
 * Позволяет вызывать зарегистрированные команды при нажатии клавиш.
 */
public class InputHandler {

    // ключ — код клавиши, значение — список действий (Runnable)
    private final Map<Integer, List<Runnable>> handlers = new HashMap<>();

    // регистрируем новое действие на клавишу
    public void register(int keyCode, Runnable action) {
        handlers.computeIfAbsent(keyCode, k -> new CopyOnWriteArrayList<>()).add(action);
    }

    // удаляем обработчик
    public void unregister(int keyCode, Runnable action) {
        List<Runnable> list = handlers.get(keyCode);
        if (list != null) list.remove(action);
    }

    // вызываем все действия для нажатых клавиш
    public void handlePressedKeys(Set<Integer> pressedKeys) {
        if (pressedKeys == null || pressedKeys.isEmpty()) return;
        for (Integer key : pressedKeys) {
            List<Runnable> list = handlers.get(key);
            if (list == null) continue;
            for (Runnable r : list) {
                try {
                    r.run();
                } catch (Exception e) {
                    e.printStackTrace(); // чтобы не ломались остальные обработчики
                }
            }
        }
    }
}
