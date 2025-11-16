package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.model.*;

public class MoveCommand implements Command {
    private final TankModel tank;
    private final Direction direction;
    private final GameWorld world;

    public MoveCommand(TankModel tank, Direction direction, GameWorld world) {
        this.tank = tank;
        this.direction = direction;
        this.world = world;
    }

    @Override
    public void execute() {
        tank.requestMove(direction, pos -> world.isBlocked(pos, tank));
    }
}
