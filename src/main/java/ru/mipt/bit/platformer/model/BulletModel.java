package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class BulletModel implements WorldObject {

    private final String id = "bullet-" + java.util.UUID.randomUUID();
    private final GridPoint2 coordinates;
    private final Direction direction;
    private final int damage;
    private final String ownerTankId;
    private boolean removable = false;

    public BulletModel(GridPoint2 start, Direction dir, int damage, String ownerTankId) {
        this.coordinates = new GridPoint2(start);
        this.direction = dir;
        this.damage = damage;
        this.ownerTankId = ownerTankId;
    }

    public GridPoint2 getCoordinates() { return new GridPoint2(coordinates); }
    public Direction getDirection() { return direction; }
    public int getDamage() { return damage; }
    public String getOwnerId() { return ownerTankId; }
    public void markRemoved() { removable = true; }

    @Override public String getId() { return id; }
    @Override public ObjectType getType() { return ObjectType.BULLET; }
    @Override public boolean isRemovable() { return removable; }

    @Override
    public void live(float delta) {
        coordinates.add(direction.dx, direction.dy); // простое движение на одну клетку
    }
}
