package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.TankModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TankView implements IView {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    protected final TankModel model;
    private final Batch batch;

    public TankView(TankModel model, Batch batch) {
        this.model = model;
        this.batch = batch;

        this.texture = new Texture("images/tank_blue.png");
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    @Override
    public void render() {
        // Позиционируем rectangle по координатам танка
        rectangle.setPosition(model.getCoordinates().x, model.getCoordinates().y);
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f); // без rotation
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
