package ru.mipt.bit.platformer.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.commands.Command;

public class GdxKeyboardListener implements KeyboardListener {

    private Command moveUp, moveDown, moveLeft, moveRight;
    private Command shoot, toggleHealth;

    @Override
    public void bindMoveUp(Command command) { this.moveUp = command; }

    @Override
    public void bindMoveDown(Command command) { this.moveDown = command; }

    @Override
    public void bindMoveLeft(Command command) { this.moveLeft = command; }

    @Override
    public void bindMoveRight(Command command) { this.moveRight = command; }

    @Override
    public void bindShoot(Command command) { this.shoot = command; }

    @Override
    public void bindToggleHealth(Command command) { this.toggleHealth = command; }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    && moveUp != null)    moveUp.execute();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  && moveDown != null)  moveDown.execute();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  && moveLeft != null)  moveLeft.execute();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && moveRight != null) moveRight.execute();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shoot != null)     shoot.execute();

        // healthBar только по явному нажатию
        if (Gdx.input.isKeyJustPressed(Input.Keys.L) && toggleHealth != null) {
            toggleHealth.execute();
        }
    }
}
