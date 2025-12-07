package ru.mipt.bit.platformer.levelloader;

import ru.mipt.bit.platformer.keyboard.RandomTankController;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class LevelInitResult {

    private final Level level;
    private final Tank playerTank;
    private final List<RandomTankController> aiControllers;

    public LevelInitResult(Level level, Tank playerTank, List<RandomTankController> aiControllers) {
        this.level = level;
        this.playerTank = playerTank;
        this.aiControllers = aiControllers;
    }

    public Level getLevel() {
        return level;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<RandomTankController> getAiControllers() {
        return aiControllers;
    }
}
