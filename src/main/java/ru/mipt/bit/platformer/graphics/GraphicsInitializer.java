package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.model.*;

public class GraphicsInitializer {

    public GraphicsInitResult initializeGraphics(Level level,
                                                 Batch batch,
                                                 TiledMapTileLayer groundLayer) {

        LevelGraphics levelGraphics = new LevelGraphics();
        UIState uiState = new UIState();

        // подписки на события
        level.addListener(Tank.class,
                new OnNewTankGraphicsObserver(levelGraphics, batch, groundLayer));

        level.addListener(Bullet.class,
                new OnShootingObserver(levelGraphics, batch, groundLayer));

        level.addListener(Tree.class, new Observer<Tree>() {
            @Override
            public void onCreated(Tree tree) {
                levelGraphics.add(tree.getId(),
                        new TreeGraphics(tree, batch, groundLayer));
            }

            @Override
            public void onRemoved(Tree tree) {
                levelGraphics.remove(tree.getId());
            }
        });

        // ВАЖНО:
        // так как Level уже применил pending changes до подписки,
        // синхронизируем стартовые объекты вручную:
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                levelGraphics.add(obj.getId(), new TankGraphics((Tank) obj, batch, groundLayer));
            } else if (obj instanceof Tree) {
                levelGraphics.add(obj.getId(), new TreeGraphics((Tree) obj, batch, groundLayer));
            } else if (obj instanceof Bullet) {
                levelGraphics.add(obj.getId(), new BulletGraphics((Bullet) obj, batch, groundLayer));
            }
        }

        return new GraphicsInitResult(levelGraphics, uiState);
    }
}
