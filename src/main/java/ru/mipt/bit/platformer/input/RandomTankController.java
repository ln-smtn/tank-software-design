package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.*;
import com.badlogic.gdx.math.GridPoint2;
import java.util.Random;

public class RandomTankController {

    private final TankModel tank;
    private final GameWorld world;
    private final Random random = new Random();

    public RandomTankController(TankModel tank, GameWorld world) {
        this.tank = tank;
        this.world = world;
    }

    public void updateRandom() {
        if (random.nextFloat() < 0.05f) {
            Direction dir = Direction.random();
            tank.requestMove(dir, pos -> world.isBlocked(pos, tank));
        }

        if (random.nextFloat() < 0.01f) {
            GridPoint2 start = tank.getCoordinates();
            Direction dir = Direction.random();
            BulletModel bullet = new BulletModel(start, dir, 10, tank.getId());
            world.enqueueAdd(bullet);
        }
    }
}
