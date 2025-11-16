package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.mipt.bit.platformer.model.TankModel;

public class TankViewWithHealth extends TankView {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public TankViewWithHealth(TankModel model, Batch batch) {
        super(model, batch);
    }

    @Override
    public void render() {
        // Сначала рисуем сам танк
        super.render();

        // Рисуем полоску здоровья над танком
        int health = model.getHealth();
        if (health > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            float x = model.getCoordinates().x;
            float y = model.getCoordinates().y + 1.0f; // над танком
            shapeRenderer.setColor(1 - health / 100f, health / 100f, 0f, 1f);
            shapeRenderer.rect(x, y, health / 2f, 0.2f); // ширина и высота полоски
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}
