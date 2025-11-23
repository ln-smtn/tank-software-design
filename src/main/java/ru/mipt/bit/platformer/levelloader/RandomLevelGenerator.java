package ru.mipt.bit.platformer.levelloader;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Random;

public class RandomLevelGenerator implements LevelGenerator {

    private final int width;
    private final int height;
    private final int enemyCount;
    private final Random random = new Random();

    public RandomLevelGenerator(int width, int height, int enemyCount) {
        this.width = width;
        this.height = height;
        this.enemyCount = enemyCount;
    }

    @Override
    public void generate(Level level) {
        for (int i = 0; i < enemyCount; i++) {
            GridPoint2 pos;
            do {
                pos = new GridPoint2(random.nextInt(width), random.nextInt(height));
            } while (level.isBlocked(pos, null));   // не ставим на дерево/танк

            level.addObject(new Tank(pos));
        }
    }
}
