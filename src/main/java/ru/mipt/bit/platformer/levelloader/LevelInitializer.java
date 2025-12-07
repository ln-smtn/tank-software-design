package ru.mipt.bit.platformer.levelloader;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.keyboard.RandomTankController;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class LevelInitializer {

    private static final int ENEMY_COUNT = 3;

    public LevelInitResult initializeLevel(TiledMapTileLayer groundLayer) {
        Level level = new Level();

        int mapWidth = groundLayer.getWidth();
        int mapHeight = groundLayer.getHeight();
        level.setBounds(mapWidth, mapHeight);

        // 1) из файла
        LevelGenerator fileGen = new FileLevelGenerator("level.txt");
        fileGen.generate(level);

        // 2) случайные враги
        LevelGenerator randomGen = new RandomLevelGenerator(mapWidth, mapHeight, ENEMY_COUNT);
        randomGen.generate(level);

        // применяем
        level.applyPendingChanges();

        // ищем игрока (первый танк после fileGen)
        Tank playerTank = null;
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank) {
                playerTank = (Tank) obj;
                break;
            }
        }

        // AI для остальных
        List<RandomTankController> ai = new ArrayList<>();
        for (GameObject obj : level.getObjects()) {
            if (obj instanceof Tank && obj != playerTank) {
                ai.add(new RandomTankController((Tank) obj, level));
            }
        }

        return new LevelInitResult(level, playerTank, ai);
    }
}
