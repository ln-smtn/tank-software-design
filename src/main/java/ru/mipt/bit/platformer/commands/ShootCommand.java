package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class ShootCommand implements Command {

    private final Tank tank;
    private final Level level;
    private final int damage;

    public ShootCommand(Tank tank, Level level, int damage) {
        this.tank = tank;
        this.level = level;
        this.damage = damage;
    }

    @Override
    public void execute() {
        if (tank == null || level == null) return;
        if (tank.isRemovable()) return;

        Direction dir = tank.getDirection();


        GridPoint2 start = tank.getPosition();

        Bullet bullet = new Bullet(start, dir, damage, tank);
        level.addObject(bullet);
    }
}
