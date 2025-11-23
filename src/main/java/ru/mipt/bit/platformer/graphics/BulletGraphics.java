package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Bullet;

public class BulletGraphics implements Graphics {

    private final Bullet bullet;
    private final Batch batch;
    private final Texture texture;
    private final int tileWidth;
    private final int tileHeight;

    public BulletGraphics(Bullet bullet, Batch batch, TiledMapTileLayer groundLayer) {
        this.bullet = bullet;
        this.batch = batch;

        this.tileWidth = groundLayer.getTileWidth();
        this.tileHeight = groundLayer.getTileHeight();

        Pixmap pix = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pix.setColor(0, 0, 0, 0);
        pix.fill();
        pix.setColor(Color.RED);
        pix.fillCircle(4, 4, 4);
        texture = new Texture(pix);
        pix.dispose();
    }

    @Override
    public void render() {
        GridPoint2 pos = bullet.getPosition();

        float sizePx = Math.min(tileWidth, tileHeight) * 0.4f;
        float centerX = pos.x * tileWidth + tileWidth / 2f;
        float centerY = pos.y * tileHeight + tileHeight / 2f;

        batch.draw(texture,
                centerX - sizePx / 2f,
                centerY - sizePx / 2f,
                sizePx,
                sizePx);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
