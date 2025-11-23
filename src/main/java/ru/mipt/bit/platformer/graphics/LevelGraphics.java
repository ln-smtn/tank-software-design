package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.HashMap;
import java.util.Map;

public class LevelGraphics {

    private final Map<String, Graphics> graphicsById = new HashMap<>();

    public void add(String id, Graphics graphics) {
        graphicsById.put(id, graphics);
    }

    public void remove(String id) {
        graphicsById.remove(id);
    }

    public void renderAll() {
        for (Graphics g : graphicsById.values()) {
            g.render();
        }
    }

    public void disposeAll() {
        for (Graphics g : graphicsById.values()) {
            g.dispose();
        }
        graphicsById.clear();
    }

    // пересборка графики ТОЛЬКО танков
    public void rebuildTankGraphics(Level level,
                                    Batch batch,
                                    TiledMapTileLayer groundLayer,
                                    UIState ui) {

        // 1. убираем только танки
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                remove(obj.getId());
            }
        }

        // 2. создаём заново
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                Tank tank = (Tank) obj;

                Graphics g = new TankGraphics(tank, batch, groundLayer);

                if (ui.isHealthBarsEnabled()) {
                    g = new HealthBarDecorator(g, tank, batch, groundLayer);
                }

                add(tank.getId(), g);
            }
        }
    }
}
