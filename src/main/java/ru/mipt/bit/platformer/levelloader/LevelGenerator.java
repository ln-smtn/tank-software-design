package ru.mipt.bit.platformer.levelloader;

import ru.mipt.bit.platformer.model.Level;

/**
 * Абстракция генератора уровня
 */
public interface LevelGenerator {
    void generate(Level level);
}
