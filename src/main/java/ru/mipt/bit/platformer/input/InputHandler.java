package ru.mipt.bit.platformer.input; // пакет для ввода

import ru.mipt.bit.platformer.input.InputHandler;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
    // обработсик ввода InputHandler.
    // Идея: регистрируем обработчики (Runnable) на код клавиши.
    // Для тестов есть метод handlePressedKeys(Set<Integer>), который не зависит от Gdx.
public class InputHandler {
    // map: keyCode -> list of handlers
    private final Map<Integer, List<Runnable>> handlers = new HashMap<>();
    // Регистрируем обработчик для keyCode
    public void register(int keyCode, Runnable action) {
    // используем CopyOnWriteArrayList чтобы можно было регистрировать/удалять из разных потоков безопаснее
        handlers.computeIfAbsent(keyCode, k -> new CopyOnWriteArrayList<>()).add(action);
    }
    // Удаление обработчика (полезно для тестов и динамики)
    public void unregister(int keyCode, Runnable action) {
        List<Runnable> list = handlers.get(keyCode);
        if (list != null) list.remove(action);
    }

    // Метод, который мы вызываем в игровом цикле: передаём набор нажатых клавиш.
// действия для всех нажатых клавишь
    public void handlePressedKeys(Set<Integer> pressedKeys) {
        if (pressedKeys == null || pressedKeys.isEmpty()) return; // нет нажатий
        for (Integer key : pressedKeys) {
            List<Runnable> list = handlers.get(key);
            if (list == null) continue;
            for (Runnable r : list) {
                try {
                    r.run(); // выполняем зарегистрированное действие
                } catch (Exception e) {
// Не даём одному падению сломать все обработчики — логируем при необходимости
                    e.printStackTrace();
                }
            }
        }
    }
}
