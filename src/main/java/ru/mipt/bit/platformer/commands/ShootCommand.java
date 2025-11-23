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
        // 1. если танк уже мёртв/удалён — не стреляем
        if (tank.isRemovable()) {
            return;
        }

        // 2. направление ствола
        Direction dir = tank.getDirection();

        // 3. пуля стартует ИЗ КЛЕТКИ ТАНКА
        GridPoint2 start = tank.getPosition();

        // 4. создаём пулю и добавляем в уровень
        Bullet bullet = new Bullet(start, dir, damage, tank);
        level.addObject(bullet);
    }
}
