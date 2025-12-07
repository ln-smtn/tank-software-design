package ru.mipt.bit.platformer.graphics;

public class GraphicsInitResult {

    private final LevelGraphics levelGraphics;
    private final UIState uiState;

    public GraphicsInitResult(LevelGraphics levelGraphics, UIState uiState) {
        this.levelGraphics = levelGraphics;
        this.uiState = uiState;
    }

    public LevelGraphics getLevelGraphics() {
        return levelGraphics;
    }

    public UIState getUiState() {
        return uiState;
    }
}
