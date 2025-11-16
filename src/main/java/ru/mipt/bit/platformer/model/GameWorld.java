package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.*;

public class GameWorld {

    private final List<WorldObject> objects = new ArrayList<>();
    private final List<WorldObject> pendingAdd = new ArrayList<>();
    private final List<WorldObject> pendingRemove = new ArrayList<>();
    private final List<LevelObserver> observers = new ArrayList<>();

    public void addObserver(LevelObserver observer) { observers.add(observer); }

    public void enqueueAdd(WorldObject obj) { pendingAdd.add(obj); }
    public void enqueueRemove(WorldObject obj) { pendingRemove.add(obj); }

    public void applyPendingChanges() {
        for (WorldObject obj : pendingAdd) {
            objects.add(obj);
            for (LevelObserver o : observers) o.onObjectAdded(obj);
        }
        pendingAdd.clear();

        for (WorldObject obj : pendingRemove) {
            objects.remove(obj);
            for (LevelObserver o : observers) o.onObjectRemoved(obj);
        }
        pendingRemove.clear();
    }

    public void updateAll(float delta) {
        for (WorldObject obj : objects) obj.live(delta);
    }

    public boolean isBlocked(GridPoint2 pos, WorldObject self) {
        for (WorldObject obj : objects) {
            if (obj == self) continue;
            if (obj instanceof TankModel || obj instanceof BulletModel) {
                GridPoint2 objPos = null;
                if (obj instanceof TankModel) objPos = ((TankModel)obj).getCoordinates();
                else if (obj instanceof BulletModel) objPos = ((BulletModel)obj).getCoordinates();
                if (objPos.equals(pos)) return true;
            }
        }
        return false;
    }

    public List<WorldObject> getObjects() { return new ArrayList<>(objects); }
}
