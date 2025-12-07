package ru.mipt.bit.platformer.keyboard;

import ru.mipt.bit.platformer.commands.PlayerCommands;

public class KeyboardInitializer {

    public KeyboardListener initializeKeyboard(PlayerCommands commands) {
        KeyboardListener keyboard = new GdxKeyboardListener();

        keyboard.bindMoveUp(commands.getMoveUp());
        keyboard.bindMoveDown(commands.getMoveDown());
        keyboard.bindMoveLeft(commands.getMoveLeft());
        keyboard.bindMoveRight(commands.getMoveRight());
        keyboard.bindShoot(commands.getShoot());
        keyboard.bindToggleHealth(commands.getToggleHealth());

        return keyboard;
    }
}
