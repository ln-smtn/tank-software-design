package ru.mipt.bit.platformer.model;

public class CollisionManager {
    private final GameWorld world;

    public CollisionManager(GameWorld world) { this.world = world; }

    public void resolveCollisions() {
        for (WorldObject obj : world.getObjects()) {
            if (obj.getType() == ObjectType.BULLET) {
                BulletModel bullet = (BulletModel) obj;
                for (WorldObject target : world.getObjects()) {
                    if (target.getId().equals(bullet.getOwnerId())) continue;
                    if (target.getCoordinates().equals(bullet.getCoordinates())) {
                        bullet.markRemoved();
                        if (target.getType() == ObjectType.TANK) {
                            ((TankModel) target).takeDamage(bullet.getDamage());
                        }
                    }
                }
            }
        }
    }
}
