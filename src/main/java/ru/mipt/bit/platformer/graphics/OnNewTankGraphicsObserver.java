package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Observer;
import ru.mipt.bit.platformer.model.Tank;

public class OnNewTankGraphicsObserver implements Observer<Tank> {

    private final LevelGraphics levelGraphics;
    private final Batch batch;
    private final TiledMapTileLayer groundLayer;

    public OnNewTankGraphicsObserver(LevelGraphics levelGraphics,
                                     Batch batch,
                                     TiledMapTileLayer groundLayer) {
        this.levelGraphics = levelGraphics;
        this.batch = batch;
        this.groundLayer = groundLayer;
    }

    @Override
    public void onCreated(Tank tank) {
        // Важно! Только TankGraphics, без HealthBar!!!
        Graphics g = new TankGraphics(tank, batch, groundLayer);
        levelGraphics.add(tank.getId(), g);
    }

    @Override
    public void onRemoved(Tank tank) {
        levelGraphics.remove(tank.getId());
    }
}
