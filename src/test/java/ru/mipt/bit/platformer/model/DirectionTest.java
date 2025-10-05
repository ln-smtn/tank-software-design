package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void basicProperties() {
        // Проверяем свойства enum'а
        assertEquals(0, Direction.UP.dx);
        assertEquals(1, Direction.UP.dy);
        assertEquals(90f, Direction.UP.rotation);

        GridPoint2 p = Direction.LEFT.toGridPoint();
        assertEquals(-1, p.x);
        assertEquals(0, p.y);
    }
}
