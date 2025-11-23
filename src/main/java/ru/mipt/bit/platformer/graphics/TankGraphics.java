package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphics implements Graphics {

    private static final Texture TANK_TEXTURE =
            new Texture(Gdx.files.classpath("images/tank_blue.png"));
    private static final TextureRegion TANK_REGION = new TextureRegion(TANK_TEXTURE);

    private final Tank tank;
    private final Batch batch;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public TankGraphics(Tank tank, Batch batch, TiledMapTileLayer groundLayer) {
        this.tank = tank;
        this.batch = batch;

        this.rectangle = createBoundingRectangle(TANK_REGION);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    @Override
    public void render() {
        GridPoint2 from = tank.getPreviousPosition();
        GridPoint2 to   = tank.getPosition();
        float progress  = tank.getMoveProgress();

        tileMovement.moveRectangleBetweenTileCenters(rectangle, from, to, progress);

        Direction dir = tank.getDirection();
        drawTextureRegionUnscaled(batch, TANK_REGION, rectangle, dir.getRotation());
    }

    @Override
    public void dispose() {
    }
}
