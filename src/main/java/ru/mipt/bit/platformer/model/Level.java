package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final Observable observable = new Observable();

    private final List<GameObject> objects = new ArrayList<>();
    private final List<GameObject> pendingAdd = new ArrayList<>();
    private final List<GameObject> pendingRemove = new ArrayList<>();

    // размеры карты в тайлах (по X и Y)
    private int width = 0;
    private int height = 0;

    // вызывается из GameDesktopLauncher после загрузки TMX
    public void setBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // true, если клетка вне карты
    public boolean isOutOfBounds(GridPoint2 pos) {
        return pos.x < 0 || pos.y < 0 || pos.x >= width || pos.y >= height-2;
    }

    // ---------------- OBSERVER ----------------

    public <T extends GameObject> void addListener(Class<T> type, Observer<T> observer) {
        observable.addListener(observer, type);
    }

    // ---------------- ОБЪЕКТЫ УРОВНЯ ----------------

    public void addObject(GameObject obj) {
        pendingAdd.add(obj);
    }

    public void removeObject(GameObject obj) {
        pendingRemove.add(obj);
    }

    public List<GameObject> getObjects() {
        return new ArrayList<>(objects);
    }

    public void applyPendingChanges() {
        for (GameObject obj : pendingAdd) {
            objects.add(obj);
            observable.notifyCreated(obj);
        }
        pendingAdd.clear();

        for (GameObject obj : pendingRemove) {
            objects.remove(obj);
            observable.notifyRemoved(obj);
        }
        pendingRemove.clear();
    }

    // апдейт логики всех объектов
    public void update(float delta) {
        for (GameObject obj : new ArrayList<>(objects)) {
            obj.update(delta);
            if (obj.isRemovable()) {
                removeObject(obj);
            }
        }
    }

    // движение блокируют:
    // 1) выход за границы карты
    // 2) танки
    // 3) деревья
    public boolean isBlocked(GridPoint2 pos, GameObject self) {
        // сразу отсекаем выход за карту
        if (isOutOfBounds(pos)) {
            return true;
        }

        for (GameObject obj : objects) {
            if (obj == self) continue;

            if (obj.getType() == ObjectType.TANK || obj.getType() == ObjectType.TREE) {
                if (obj.getPosition().equals(pos)) {
                    return true;
                }
            }
        }
        return false;
    }
}
