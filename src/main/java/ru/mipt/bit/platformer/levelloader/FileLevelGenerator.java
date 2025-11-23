package ru.mipt.bit.platformer.levelloader;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLevelGenerator implements LevelGenerator {

    private final String resourcePath;

    public FileLevelGenerator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void generate(Level level) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        FileLevelGenerator.class.getClassLoader()
                                .getResourceAsStream(resourcePath)))) {

            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    GridPoint2 pos = new GridPoint2(x, y);

                    if (level.isOutOfBounds(pos)) {
                        continue;
                    }
                    switch (c) {
                        case 'X': // игрок
                            level.addObject(new Tank(pos));
                            break;
                        case 'T': // дерево
                            level.addObject(new Tree(pos));
                            break;
                        default:
                            break;
                    }
                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read level file " + resourcePath, e);
        }
    }
}
