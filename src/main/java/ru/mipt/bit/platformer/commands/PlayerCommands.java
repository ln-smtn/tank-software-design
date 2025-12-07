package ru.mipt.bit.platformer.commands;

public class PlayerCommands {

    private final Command moveUp;
    private final Command moveDown;
    private final Command moveLeft;
    private final Command moveRight;
    private final Command shoot;
    private final Command toggleHealth;

    public PlayerCommands(Command moveUp,
                          Command moveDown,
                          Command moveLeft,
                          Command moveRight,
                          Command shoot,
                          Command toggleHealth) {
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.shoot = shoot;
        this.toggleHealth = toggleHealth;
    }

    public Command getMoveUp() {
        return moveUp;
    }

    public Command getMoveDown() {
        return moveDown;
    }

    public Command getMoveLeft() {
        return moveLeft;
    }

    public Command getMoveRight() {
        return moveRight;
    }

    public Command getShoot() {
        return shoot;
    }

    public Command getToggleHealth() {
        return toggleHealth;
    }
}
