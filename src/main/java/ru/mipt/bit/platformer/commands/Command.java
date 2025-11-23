package ru.mipt.bit.platformer.commands;

/**
 * Базовая игровая команда.
 * Реализует Runnable, чтобы её можно было напрямую передавать в InputHandler.
 */
public interface Command extends Runnable {
    void execute();

    @Override
    default void run() {
        execute();
    }
}
