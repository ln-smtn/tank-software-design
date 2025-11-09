package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankViewWithHealth extends TankView {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final TankModel tank;

    public TankViewWithHealth(TankModel model, Batch batch, TileMovement movement) {
        super(model, batch, movement);
        this.tank = model;
    }

    @Override
    public void render() {
        super.render();
        int health = tank.getHealth();
        if (health > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            float x = tank.getCoordinates().x * 32f;
            float y = tank.getCoordinates().y * 32f + 35f;
            shapeRenderer.setColor(1 - health / 100f, health / 100f, 0, 1);
            shapeRenderer.rect(x, y, 32f * health / 100f, 4);
            shapeRenderer.end();
        }
    }
}
