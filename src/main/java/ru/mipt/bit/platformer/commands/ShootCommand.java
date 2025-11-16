package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.TankModel;

/**
 * Команда "стрелять": просит танк создать пулю и регистрирует пулю в GameWorld.
 */
public class ShootCommand implements Command {
    private final TankModel tank;
    private final Direction direction;
    private final GameWorld world;
    private final int damage;

    public ShootCommand(TankModel tank, Direction direction, GameWorld world, int damage) {
        this.tank = tank;
        this.direction = direction;
        this.world = world;
        this.damage = damage;
    }

    @Override
    public void execute() {
        BulletModel bullet = tank.createBullet(direction, damage);
        if (bullet != null) {
            world.enqueueAdd(bullet);
        }
    }
}
