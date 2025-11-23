package ru.mipt.bit.platformer.keyboard;

import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.ShootCommand;
import ru.mipt.bit.platformer.model.*;

import java.util.Random;

public class RandomTankController {

    private final Tank tank;
    private final Level level;
    private final Random random = new Random();

    private final MoveCommand moveUp;
    private final MoveCommand moveDown;
    private final MoveCommand moveLeft;
    private final MoveCommand moveRight;
    private final ShootCommand shoot;

    public RandomTankController(Tank tank, Level level) {
        this.tank = tank;
        this.level = level;

        moveUp    = new MoveCommand(tank, Direction.UP, level);
        moveDown  = new MoveCommand(tank, Direction.DOWN, level);
        moveLeft  = new MoveCommand(tank, Direction.LEFT, level);
        moveRight = new MoveCommand(tank, Direction.RIGHT, level);

        shoot = new ShootCommand(tank, level, 10); // урон бота – по вкусу
    }

    public void updateRandom() {
        // иногда двигаемся
        if (random.nextFloat() < 0.05f) {
            Direction dir = Direction.random();
            switch (dir) {
                case UP:    moveUp.execute(); break;
                case DOWN:  moveDown.execute(); break;
                case LEFT:  moveLeft.execute(); break;
                case RIGHT: moveRight.execute(); break;
            }
        }

        // иногда стреляем
        if (random.nextFloat() < 0.01f) {
            shoot.execute();
        }
    }
}
