package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Tank;

public class HealthBarDecorator implements Graphics {

    private final Graphics delegate;
    private final Tank tank;
    private final Batch batch;
    private final Texture whitePixel;
    private final int tileWidth;
    private final int tileHeight;

    public HealthBarDecorator(Graphics delegate,
                              Tank tank,
                              Batch batch,
                              TiledMapTileLayer groundLayer) {
        this.delegate = delegate;
        this.tank = tank;
        this.batch = batch;

        this.tileWidth = groundLayer.getTileWidth();
        this.tileHeight = groundLayer.getTileHeight();

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE);
        pix.fill();
        whitePixel = new Texture(pix);
        pix.dispose();
    }

    @Override
    public void render() {
        // сначала танк
        delegate.render();

        int hp = tank.getHealth();
        if (hp <= 0) return;

        Color old = batch.getColor().cpy();

        float ratio = Math.max(0f, Math.min(1f, hp / 100f));

        GridPoint2 p = tank.getPosition();
        float x = p.x * tileWidth;
        float y = p.y * tileHeight + tileHeight * 0.8f;

        batch.setColor(1 - ratio, ratio, 0f, 1f);
        batch.draw(whitePixel, x, y, tileWidth * ratio, tileHeight * 0.15f);

        batch.setColor(old);
    }

    @Override
    public void dispose() {
        whitePixel.dispose();
    }
}
