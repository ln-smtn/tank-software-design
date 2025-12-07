package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.commands.CommandFactory;
import ru.mipt.bit.platformer.commands.PlayerCommands;
import ru.mipt.bit.platformer.graphics.GraphicsInitResult;
import ru.mipt.bit.platformer.graphics.GraphicsInitializer;
import ru.mipt.bit.platformer.graphics.LevelGraphics;
import ru.mipt.bit.platformer.graphics.UIState;
import ru.mipt.bit.platformer.keyboard.KeyboardInitializer;
import ru.mipt.bit.platformer.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.levelloader.LevelInitResult;
import ru.mipt.bit.platformer.levelloader.LevelInitializer;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

public class Config {

    private final LevelInitializer levelInitializer = new LevelInitializer();
    private final GraphicsInitializer graphicsInitializer = new GraphicsInitializer();
    private final CommandFactory commandFactory = new CommandFactory();
    private final KeyboardInitializer keyboardInitializer = new KeyboardInitializer();

    // --------- как на диаграмме ---------

    public LevelInitResult initializeLevel(TiledMapTileLayer groundLayer) {
        return levelInitializer.initializeLevel(groundLayer);
    }

    public GraphicsInitResult initializeGraphics(Level level,
                                                 Batch batch,
                                                 TiledMapTileLayer groundLayer) {
        return graphicsInitializer.initializeGraphics(level, batch, groundLayer);
    }

    public PlayerCommands createCommands(Tank playerTank,
                                         Level level,
                                         LevelGraphics levelGraphics,
                                         UIState uiState,
                                         Batch batch,
                                         TiledMapTileLayer groundLayer) {
        return commandFactory.createPlayerCommands(
                playerTank, level, levelGraphics, uiState, batch, groundLayer
        );
    }

    public KeyboardListener initializeKeyboard(PlayerCommands commands) {
        return keyboardInitializer.initializeKeyboard(commands);
    }
}
