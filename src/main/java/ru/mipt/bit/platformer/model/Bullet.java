package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.UUID;

public class Bullet implements GameObject {

    private final String id = "bullet-" + UUID.randomUUID();
    private final GridPoint2 position;
    private final Direction direction;
    private final int damage;
    private final Tank owner;
    private boolean removable = false;

    public Bullet(GridPoint2 start, Direction direction, int damage, Tank owner) {
        this.position = new GridPoint2(start);
        this.direction = direction;
        this.damage = damage;
        this.owner = owner;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }

    public Tank getOwner() {
        return owner;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.BULLET;
    }

    @Override
    public GridPoint2 getPosition() {
        return new GridPoint2(position);
    }

    @Override
    public boolean isRemovable() {
        return removable;
    }

    public void markRemoved() {
        removable = true;
    }

    @Override
    public void update(float deltaTime) {
        position.add(direction.dx, direction.dy);
    }
}
