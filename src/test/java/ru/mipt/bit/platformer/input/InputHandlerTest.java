package ru.mipt.bit.platformer.input;

// JUnit аннотации
import org.junit.jupiter.api.Test;

// Коллекции Java
import java.util.HashSet;
import java.util.Set;

// Статический импорт, чтобы писать assertEquals(…) вместо Assertions.assertEquals(…)
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputHandlerTest {

    @Test // помечаем метод как тест
    void registersAndCallsHandlers() {
        // создаём InputHandler — наш объект, который хранит реакции на клавиши
        InputHandler h = new InputHandler();

        final int KEY = 1000; // "виртуальная" клавиша для теста
        final int[] counter = {0}; // счётчик в массиве (чтобы изменять внутри лямбды)

        // регистрируем обработчик: при нажатии на KEY увеличиваем счётчик
        h.register(KEY, () -> counter[0] += 1);

        // создаём множество нажатых клавиш
        Set<Integer> pressed = new HashSet<>();
        pressed.add(KEY);

        // запускаем обработку
        h.handlePressedKeys(pressed);
        // проверяем, что обработчик вызвался один раз
        assertEquals(1, counter[0]);

        // снова обрабатываем
        h.handlePressedKeys(pressed);
        // теперь счётчик должен быть 2
        assertEquals(2, counter[0]);
    }
}


