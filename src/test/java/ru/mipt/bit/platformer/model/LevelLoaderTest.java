package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LevelLoaderTest {

    @Test
    void generateRandomLevelHasUniquePositions() {
        LevelLoader loader = new LevelLoader(10, 8);
        LevelLoader.LevelData data = loader.generateRandom(10);

        Set<GridPoint2> allPositions = new HashSet<>();
        for (Obstacle o : data.obstacles) {
            assertTrue(allPositions.add(o.getCoordinates()), "Объекты не должны пересекаться");
        }
        assertTrue(allPositions.add(data.playerStart), "Игрок не должен стоять на дереве");
    }
}
