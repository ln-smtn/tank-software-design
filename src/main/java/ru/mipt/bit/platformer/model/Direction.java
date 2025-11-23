package ru.mipt.bit.platformer.model;

import java.util.Random;

public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, 270f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0f);

    private static final Random RANDOM = new Random();

    public final int dx;
    public final int dy;
    private final float rotation;

    Direction(int dx, int dy, float rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public static Direction random() {
        Direction[] values = values();
        return values[RANDOM.nextInt(values.length)];
    }
}
