package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.Tree;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics implements Graphics {

    private static final Texture TREE_TEXTURE =
            new Texture(Gdx.files.classpath("images/greenTree.png"));
    private static final TextureRegion TREE_REGION = new TextureRegion(TREE_TEXTURE);

    private final Tree tree;
    private final Batch batch;
    private final Rectangle rectangle;

    public TreeGraphics(Tree tree, Batch batch, TiledMapTileLayer groundLayer) {
        this.tree = tree;
        this.batch = batch;

        rectangle = createBoundingRectangle(TREE_REGION);
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getPosition());
    }

    @Override
    public void render() {
        drawTextureRegionUnscaled(batch, TREE_REGION, rectangle, 0f);
    }

    @Override
    public void dispose() {
        // НЕ трогаем TREE_TEXTURE — общий, живёт до конца игры.
    }
}
