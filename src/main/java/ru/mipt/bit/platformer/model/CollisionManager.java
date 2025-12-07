package ru.mipt.bit.platformer.model;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    private final Level level;

    public CollisionManager(Level level) {
        this.level = level;
    }

    public void resolve() {
        // работаем со снимком, чтобы безопасно обходить список
        List<GameObject> snapshot = new ArrayList<>(level.getObjects());

        for (GameObject obj : snapshot) {
            if (obj.getType() != ObjectType.BULLET) continue;

            Bullet bullet = (Bullet) obj;

            if (bullet.isRemovable()) continue;

            // вышла за карту
            if (level.isOutOfBounds(bullet.getPosition())) {
                bullet.markRemoved();
                continue;
            }

            for (GameObject target : snapshot) {

                // ВАЖНО: пропускаем САМУ пулю
                if (target == bullet) continue;

                // не стреляем в владельца
                if (target == bullet.getOwner()) continue;

                // сравниваем клетки
                if (!target.getPosition().equals(bullet.getPosition())) continue;

                // столкновение => пуля исчезает
                bullet.markRemoved();

                if (target.getType() == ObjectType.TANK) {
                    Tank t = (Tank) target;
                    t.damage(bullet.getDamage());

                    if (t.isRemovable()) {
                        level.removeObject(t);
                    }
                }

                // если дерево — просто блокирует пулю
                // (дерево остаётся)

                break; // одно попадание = остановка
            }
        }
    }
}
