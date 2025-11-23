package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Observer;

public class OnShootingObserver implements Observer<Bullet> {

    private final LevelGraphics levelGraphics;
    private final Batch batch;
    private final TiledMapTileLayer groundLayer;

    public OnShootingObserver(LevelGraphics levelGraphics,
                              Batch batch,
                              TiledMapTileLayer groundLayer) {
        this.levelGraphics = levelGraphics;
        this.batch = batch;
        this.groundLayer = groundLayer;
    }

    @Override
    public void onCreated(Bullet bullet) {
        Graphics g = new BulletGraphics(bullet, batch, groundLayer);
        levelGraphics.add(bullet.getId(), g);
    }

    @Override
    public void onRemoved(Bullet bullet) {
        levelGraphics.remove(bullet.getId());
    }
}
