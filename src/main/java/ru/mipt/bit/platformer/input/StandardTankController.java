package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.model.CollisionChecker;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.TankModel;

/**
 * Стандартное управление танком с клавиатуры:
 * стрелки + WASD.
 * Работает через InputHandler, не зависит от GDX напрямую.
 */
public class StandardTankController implements TankController {

    private final TankModel tank;
    private final CollisionChecker collisionChecker;

    public StandardTankController(TankModel tank, CollisionChecker collisionChecker) {
        this.tank = tank;
        this.collisionChecker = collisionChecker;
    }

    @Override
    public void registerControls(InputHandler inputHandler) {
        // Движение вверх
        inputHandler.register(Keys.UP, () -> tank.requestMove(Direction.UP, collisionChecker::isBlocked));
        inputHandler.register(Keys.W, () -> tank.requestMove(Direction.UP, collisionChecker::isBlocked));

        // Движение вниз
        inputHandler.register(Keys.DOWN, () -> tank.requestMove(Direction.DOWN, collisionChecker::isBlocked));
        inputHandler.register(Keys.S, () -> tank.requestMove(Direction.DOWN, collisionChecker::isBlocked));

        // Движение влево
        inputHandler.register(Keys.LEFT, () -> tank.requestMove(Direction.LEFT, collisionChecker::isBlocked));
        inputHandler.register(Keys.A, () -> tank.requestMove(Direction.LEFT, collisionChecker::isBlocked));

        // Движение вправо
        inputHandler.register(Keys.RIGHT, () -> tank.requestMove(Direction.RIGHT, collisionChecker::isBlocked));
        inputHandler.register(Keys.D, () -> tank.requestMove(Direction.RIGHT, collisionChecker::isBlocked));
    }
}
