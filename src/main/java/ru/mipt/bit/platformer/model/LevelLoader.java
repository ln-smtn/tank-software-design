package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.util.*;


public class LevelLoader {

    private final int width;   // ширина игрового поля
    private final int height;  // высота игрового поля

    public LevelLoader(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /** Генерация случайного уровня */
    public LevelData generateRandom(int treeCount) {
        Random random = new Random();

        List<Obstacle> obstacles = new ArrayList<>();
        Set<GridPoint2> used = new HashSet<>(); // чтобы не было пересечения объектов

        for (int i = 0; i < treeCount; i++) {
            GridPoint2 pos;
            do {
                pos = new GridPoint2(random.nextInt(width), random.nextInt(height));
            } while (used.contains(pos)); // проверяем, что позиция свободна
            used.add(pos);
            obstacles.add(new TreeModel(pos));
        }

        GridPoint2 playerStart;
        do {
            playerStart = new GridPoint2(random.nextInt(width), random.nextInt(height));
        } while (used.contains(playerStart));
        return new LevelData(obstacles, playerStart);
    }

    // Загрузка уровня из текстового файла (например, src/main/resources/level.txt)
    public LevelData loadFromFile(String resourcePath) throws IOException {
        List<String> lines;

        // Считываем из ресурсов (если игра упакована в jar)
        try (var stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IOException("Не найден файл ресурса: " + resourcePath);
            }
            lines = new java.io.BufferedReader(new java.io.InputStreamReader(stream))
                    .lines().toList();
        }

        List<Obstacle> obstacles = new ArrayList<>();
        GridPoint2 playerStart = null;
        int yMax = lines.size() - 1;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                switch (c) {
                    case 'T' -> obstacles.add(new TreeModel(new GridPoint2(x, yMax - y)));
                    case 'X' -> playerStart = new GridPoint2(x, yMax - y);
                    default -> { /* '_' — пустая клетка */ }
                }
            }
        }

        if (playerStart == null) {
            throw new IllegalStateException("Не найдена начальная позиция игрока (символ X)");
        }

        return new LevelData(obstacles, playerStart);
    }
    // вспомогательный контейнер с результатом
    public static class LevelData {
        public final List<Obstacle> obstacles;   // список всех препятствий
        public final GridPoint2 playerStart;     // стартовая позиция игрока

        public LevelData(List<Obstacle> obstacles, GridPoint2 playerStart) {
            this.obstacles = obstacles;
            this.playerStart = playerStart;
        }
    }
}