package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.TileMovement;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.*;

public class GameField {

    private final TiledMap level;
    private final MapRenderer renderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement movement;

    private final Batch mapBatch = new SpriteBatch();  // <-- ВАЖНО!!!

    public GameField(Batch ignored) {
        level = new TmxMapLoader().load("level.tmx");

        // создаём renderer с ОТДЕЛЬНЫМ batch
        renderer = createSingleLayerMapRenderer(level, mapBatch);

        groundLayer = getSingleLayer(level);
        movement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public TileMovement getMovement() {
        return movement;
    }

    public void render() {
        renderer.render();  // рисует только tilemap
    }

    public void dispose() {
        level.dispose();
        mapBatch.dispose();  // <-- важно
    }
}
