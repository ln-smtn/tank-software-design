package ru.mipt.bit.platformer.model;

public class CollisionManager {

    private final Level level;

    public CollisionManager(Level level) {
        this.level = level;
    }

    public void resolve() {
        for (GameObject obj : level.getObjects()) {
            if (obj.getType() != ObjectType.BULLET) {
                continue;
            }

            Bullet bullet = (Bullet) obj;

            // улетела за карту
            if (level.isOutOfBounds(bullet.getPosition())) {
                bullet.markRemoved();
                continue;
            }

            for (GameObject target : level.getObjects()) {
                if (target == bullet.getOwner()) continue;

                if (!target.getPosition().equals(bullet.getPosition())) continue;

                // есть столкновение
                bullet.markRemoved();

                if (target.getType() == ObjectType.TANK) {
                    Tank t = (Tank) target;
                    t.damage(bullet.getDamage());
                    if (t.isRemovable()) {
                        level.removeObject(t);
                    }
                } else if (target.getType() == ObjectType.BULLET) {
                    ((Bullet) target).markRemoved();
                }
                // дерево / другое — просто исчезает пуля
            }
        }
    }
}
