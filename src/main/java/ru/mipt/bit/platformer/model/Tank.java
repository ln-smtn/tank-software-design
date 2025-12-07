package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.UUID;

public class Tank implements GameObject {

    private final String id = "tank-" + UUID.randomUUID();
    private final GridPoint2 position;
    private GridPoint2 previous;
    private float moveProgress = 1f;
    private Direction direction = Direction.UP;
    private int health = 100;

    public Tank(GridPoint2 start) {
        position = new GridPoint2(start);
        previous = new GridPoint2(start);
    }

    public GridPoint2 getPosition() {
        return new GridPoint2(position);
    }

    public GridPoint2 getPreviousPosition() {
        return new GridPoint2(previous);
    }

    public float getMoveProgress() {
        return moveProgress;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        health -= amount;
    }



    public void requestMove(Direction dir, Level level) {
        if (moveProgress < 1f) return;

        GridPoint2 next = new GridPoint2(position.x + dir.dx, position.y + dir.dy);

        if (level != null && level.isBlocked(next, this)) {
            return; // занято
        }

        previous.set(position);
        position.set(next);
        direction = dir;
        moveProgress = 0f;
    }

    public Bullet shoot(int damage) {
        GridPoint2 start = getPosition().add(direction.dx, direction.dy);
        return new Bullet(start, direction, damage, this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.TANK;
    }

    @Override
    public boolean isRemovable() {
        return health <= 0;
    }

    @Override
    public void update(float deltaTime) {
        if (moveProgress < 1f) {
            moveProgress = Math.min(1f, moveProgress + deltaTime * 4f);
        }
    }
}
