package ru.mipt.bit.platformer.model;

public class DirectionTest {

    public static void main(String[] args) {
        basicProperties();
        System.out.println("DirectionTest: ALL TESTS PASSED");
    }

    private static void basicProperties() {
        // UP
        assertEquals(0, Direction.UP.dx, "UP.dx должен быть 0");
        assertEquals(1, Direction.UP.dy, "UP.dy должен быть 1");
        assertFloatEquals(90f, Direction.UP.getRotation(), 1e-6f, "rotation UP == 90f");

        // LEFT
        assertEquals(-1, Direction.LEFT.dx, "LEFT.dx должен быть -1");
        assertEquals(0, Direction.LEFT.dy, "LEFT.dy должен быть 0");
        assertFloatEquals(180f, Direction.LEFT.getRotation(), 1e-6f, "rotation LEFT == 180f");
    }

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
