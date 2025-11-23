package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModelTest {

    public static void main(String[] args) {
        coordinatesAreStored();
        System.out.println("TreeModelTest: ALL TESTS PASSED");
    }

    private static void coordinatesAreStored() {
        Tree t = new Tree(new GridPoint2(5, 6));
        GridPoint2 pos = t.getPosition();

        assertEquals(5, pos.x, "x дерева должен быть 5");
        assertEquals(6, pos.y, "y дерева должен быть 6");
    }

    // простой assert без JUnit
    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
