package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.function.Predicate;

public class TankModel implements WorldObject {

    private final String id = "tank-" + java.util.UUID.randomUUID();
    private final GridPoint2 coordinates;
    private GridPoint2 destination;
    private float movementProgress = 1f;
    private Direction lastDirection = Direction.UP;
    private int health = 100;

    public TankModel(GridPoint2 start) {
        this.coordinates = new GridPoint2(start);
        this.destination = new GridPoint2(start);
    }

    public GridPoint2 getCoordinates() { return new GridPoint2(coordinates); }
    public GridPoint2 getDestination() { return new GridPoint2(destination); }
    public float getMovementProgress() { return movementProgress; }

    public void setLastDirection(Direction dir) { this.lastDirection = dir; }
    public Direction getLastDirection() { return lastDirection; }

    public int getHealth() { return health; }
    public void takeDamage(int dmg) { health -= dmg; }

    /** Попытка движения, проверка коллизий через предикат */
    public boolean requestMove(Direction dir, Predicate<GridPoint2> isBlocked) {
        if (movementProgress < 1f) return false; // ещё в движении

        GridPoint2 target = new GridPoint2(coordinates.x + dir.dx, coordinates.y + dir.dy);
        if (isBlocked != null && isBlocked.test(target)) return false;

        destination.set(target);
        movementProgress = 0f;
        lastDirection = dir;
        return true;
    }

    /** Обновление прогресса движения */
    public void update(float delta) {
        movementProgress = Math.min(1f, movementProgress + delta * 5f); // скорость 5
        if (movementProgress >= 1f) {
            coordinates.set(destination);
            movementProgress = 1f;
        }
    }

    /** Создание пули с направлением и уроном */
    public BulletModel createBullet(Direction dir, int damage) {
        GridPoint2 start = coordinates.cpy().add(dir.dx, dir.dy);
        return new BulletModel(start, dir, damage, id);
    }

    /** Создание пули по умолчанию (lastDirection, 20 урона) */
    public BulletModel createBullet() {
        return createBullet(lastDirection, 20);
    }

    @Override public String getId() { return id; }
    @Override public ObjectType getType() { return ObjectType.TANK; }
    @Override public boolean isRemovable() { return health <= 0; }

    @Override
    public void live(float delta) { update(delta); }
}
