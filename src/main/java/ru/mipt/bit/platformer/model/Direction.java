package ru.mipt.bit.platformer.model;

import java.util.Random;

public enum Direction {
    UP(0,1,0f),
    DOWN(0,-1,180f),
    LEFT(-1,0,270f),
    RIGHT(1,0,90f);

    public final int dx, dy;
    public final float rotation;

    Direction(int dx, int dy, float rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }

    private static final Direction[] VALUES = values();
    private static final Random RANDOM = new Random();
    public static Direction random() { return VALUES[RANDOM.nextInt(VALUES.length)]; }
}
