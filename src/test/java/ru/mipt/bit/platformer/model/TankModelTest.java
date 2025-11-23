package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class TankModelTest {

    public static void main(String[] args) {
        moveAndUpdate_whenNotBlocked();
        requestMove_blockedByTree();
        System.out.println("TankModelTest: ALL TESTS PASSED");
    }

    private static void moveAndUpdate_whenNotBlocked() {
        // Уровень без препятствий
        Level level = new Level();
        Tank tank = new Tank(new GridPoint2(1, 1));
        level.addObject(tank);
        level.applyPendingChanges();

        // Двигаемся вправо
        tank.requestMove(Direction.RIGHT, level);

        // update с достаточно большим dt, чтобы закончить движение
        tank.update(0.3f);

        GridPoint2 pos = tank.getPosition();
        assertEquals(2, pos.x, "x после движения вправо");
        assertEquals(1, pos.y, "y после движения вправо");
        assertFloatEquals(1f, tank.getMoveProgress(), 1e-6f, "moveProgress должен быть 1f");

        GridPoint2 prev = tank.getPreviousPosition();
        assertEquals(1, prev.x, "previous.x должен быть исходным");
        assertEquals(1, prev.y, "previous.y должен быть исходным");
    }

    private static void requestMove_blockedByTree() {
        Level level = new Level();
        Tank tank = new Tank(new GridPoint2(1, 1));
        Tree tree = new Tree(new GridPoint2(2, 1));

        level.addObject(tank);
        level.addObject(tree);
        level.applyPendingChanges();

        // Справа дерево — движение должно быть заблокировано
        tank.requestMove(Direction.RIGHT, level);

        GridPoint2 pos = tank.getPosition();
        assertEquals(1, pos.x, "танк не должен сдвинуться по x, так как справа дерево");
        assertEquals(1, pos.y, "танк не должен сдвинуться по y");
        assertFloatEquals(1f, tank.getMoveProgress(), 1e-6f, "moveProgress должен остаться 1f");
    }

    // ====== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ АСЕРТОВ (БЕЗ JUNIT) ======

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }

    private static void assertFloatEquals(float expected, float actual, float eps, String message) {
        if (Math.abs(expected - actual) > eps) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
