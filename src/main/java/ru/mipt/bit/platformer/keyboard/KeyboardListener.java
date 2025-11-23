package ru.mipt.bit.platformer.keyboard;

import ru.mipt.bit.platformer.commands.Command;

public interface KeyboardListener {
    void bindMoveUp(Command command);
    void bindMoveDown(Command command);
    void bindMoveLeft(Command command);
    void bindMoveRight(Command command);
    void bindShoot(Command command);
    void bindToggleHealth(Command command);

    void update();   // вызывать каждый кадр
}
