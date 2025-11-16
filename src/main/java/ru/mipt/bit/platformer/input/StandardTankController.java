package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.model.*;

public class StandardTankController implements TankController {

    private final TankModel tank;
    private final GameWorld world;

    public StandardTankController(TankModel tank, GameWorld world) {
        this.tank = tank;
        this.world = world;
    }

    @Override
    public void registerControls(InputHandler input) {
        input.register(Keys.UP,    () -> tank.requestMove(Direction.UP,    pos -> world.isBlocked(pos, tank)));
        input.register(Keys.DOWN,  () -> tank.requestMove(Direction.DOWN,  pos -> world.isBlocked(pos, tank)));
        input.register(Keys.LEFT,  () -> tank.requestMove(Direction.LEFT,  pos -> world.isBlocked(pos, tank)));
        input.register(Keys.RIGHT, () -> tank.requestMove(Direction.RIGHT, pos -> world.isBlocked(pos, tank)));

        input.register(Keys.SPACE, () -> {
            BulletModel bullet = tank.createBullet(); // теперь метод без аргументов работает
            world.enqueueAdd(bullet);
        });
    }
}
