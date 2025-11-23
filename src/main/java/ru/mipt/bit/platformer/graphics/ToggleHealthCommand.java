package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.model.Level;

public class ToggleHealthCommand implements Command {

    private final UIState uiState;
    private final LevelGraphics levelGraphics;
    private final Level level;
    private final Batch batch;
    private final TiledMapTileLayer groundLayer;

    public ToggleHealthCommand(UIState uiState,
                               LevelGraphics levelGraphics,
                               Level level,
                               Batch batch,
                               TiledMapTileLayer groundLayer) {
        this.uiState = uiState;
        this.levelGraphics = levelGraphics;
        this.level = level;
        this.batch = batch;
        this.groundLayer = groundLayer;
    }

    @Override
    public void execute() {
        uiState.toggleHealthBars();
        levelGraphics.rebuildTankGraphics(level, batch, groundLayer, uiState);
    }
}
