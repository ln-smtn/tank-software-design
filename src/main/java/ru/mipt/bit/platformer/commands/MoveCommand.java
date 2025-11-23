package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommand implements Command {

    private final Tank tank;
    private final Direction direction;
    private final Level level;

    public MoveCommand(Tank tank, Direction direction, Level level) {
        this.tank = tank;
        this.direction = direction;
        this.level = level;
    }

    @Override
    public void execute() {
        tank.requestMove(direction, level);
    }
}
