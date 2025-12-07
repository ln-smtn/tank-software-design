package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.graphics.LevelGraphics;
import ru.mipt.bit.platformer.graphics.ToggleHealthCommand;
import ru.mipt.bit.platformer.graphics.UIState;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class CommandFactory {

    private static final int PLAYER_DAMAGE = 20;

    public PlayerCommands createPlayerCommands(Tank playerTank,
                                               Level level,
                                               LevelGraphics levelGraphics,
                                               UIState uiState,
                                               Batch batch,
                                               TiledMapTileLayer groundLayer) {

        return new PlayerCommands(
                new MoveCommand(playerTank, Direction.UP, level),
                new MoveCommand(playerTank, Direction.DOWN, level),
                new MoveCommand(playerTank, Direction.LEFT, level),
                new MoveCommand(playerTank, Direction.RIGHT, level),
                new ShootCommand(playerTank, level, PLAYER_DAMAGE),
                new ToggleHealthCommand(uiState, levelGraphics, level, batch, groundLayer)
        );
    }
}
