package ru.mipt.bit.platformer.view;

import ru.mipt.bit.platformer.model.*;
import java.util.*;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LevelViewObserver implements LevelObserver {

    private final Map<String, IView> map = new HashMap<>();
    private final Batch batch;

    public LevelViewObserver(Batch batch) {
        this.batch = batch;
    }

    @Override
    public void onObjectAdded(WorldObject obj) {
        IView view = null;
        if (obj instanceof TankModel) {
            view = new TankView((TankModel)obj, batch);
        } else if (obj instanceof BulletModel) {
            view = new BulletView((BulletModel)obj, batch);
        }
        if (view != null) {
            map.put(obj.getId(), view);
        }
    }

    @Override
    public void onObjectRemoved(WorldObject obj) {
        IView v = map.remove(obj.getId());
        if (v != null) v.dispose();
    }

    public void renderAll() {
        for (IView v : map.values()) v.render();
    }
}
