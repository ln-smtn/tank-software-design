package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.BulletModel;

public class BulletView implements IView {

    private final BulletModel model;
    private final Batch batch;

    public BulletView(BulletModel model, Batch batch) {
        this.model = model;
        this.batch = batch;
    }

    @Override
    public void render() {
        // ничего не рисуем — пули визуально игнорируем
        GridPoint2 pos = model.getCoordinates();
    }

    @Override
    public void dispose() {}
}
