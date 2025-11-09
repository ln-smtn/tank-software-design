package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    @Test
    void blockedByBordersAndObstacles() {
        Obstacle tree = new TreeModel(new GridPoint2(2, 2));
        GameWorld world = new GameWorld(List.of(tree), 5, 5);

        // внутри поля, свободно
        assertFalse(world.isBlocked(new GridPoint2(1, 1)));

        // дерево блокирует
        assertTrue(world.isBlocked(new GridPoint2(2, 2)));

        // границы блокируют
        assertTrue(world.isBlocked(new GridPoint2(-1, 0)));
        assertTrue(world.isBlocked(new GridPoint2(0, -1)));
        assertTrue(world.isBlocked(new GridPoint2(5, 0)));
        assertTrue(world.isBlocked(new GridPoint2(0, 4))); // верхняя граница
    }

    @Test
    void blockedByOtherTank() {
        GameWorld world = new GameWorld(List.of(), 5, 5);
        TankModel t1 = new TankModel(new GridPoint2(1, 1), 5f);
        TankModel t2 = new TankModel(new GridPoint2(2, 2), 5f);
        world.addTank(t1);
        world.addTank(t2);

        // проверяем блокировку позиций других танков
        assertTrue(world.isBlocked(new GridPoint2(2, 2), t1));
        assertFalse(world.isBlocked(new GridPoint2(0, 0), t1));
    }
}
