package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class TankModelTest {

    @Test
    void moveAndUpdate_whenNotBlocked() {
        // Для теста даём высокую скорость, чтобы одно update завершило движение
        TankModel tank = new TankModel(new GridPoint2(1,1), 10f);

        Predicate<GridPoint2> neverBlocked = p -> false; // никогда не блокируем

        boolean started = tank.requestMove(Direction.RIGHT, neverBlocked);
        assertTrue(started, "Движение должно начаться");

        tank.update(1f); // большой delta -> должно завершиться

        assertEquals(2, tank.getCoordinates().x);
        assertEquals(1, tank.getCoordinates().y);
        assertEquals(1f, tank.getMovementProgress());
    }

    @Test
    void requestMove_blockedByTree() {
        TankModel tank = new TankModel(new GridPoint2(1,1));

        // Блокируем клетку (2,1)
        Predicate<GridPoint2> blockAt2_1 = p -> (p.x == 2 && p.y == 1);

        boolean started = tank.requestMove(Direction.RIGHT, blockAt2_1);
        assertFalse(started, "Движение должно быть заблокировано");
    }
}
