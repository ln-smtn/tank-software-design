package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.GameField;
import ru.mipt.bit.platformer.graphics.LevelGraphics;
import ru.mipt.bit.platformer.graphics.UIState;
import ru.mipt.bit.platformer.keyboard.GdxKeyboardListener;
import ru.mipt.bit.platformer.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.levelloader.FileLevelGenerator;
import ru.mipt.bit.platformer.levelloader.LevelGenerator;
import ru.mipt.bit.platformer.levelloader.RandomLevelGenerator;
import ru.mipt.bit.platformer.model.CollisionManager;
import ru.mipt.bit.platformer.model.Level;

@Configuration
public class GameConfig {

    @Bean
    public Batch spriteBatch() {
        return new SpriteBatch();
    }

    @Bean
    public GameField gameField() {
        return new GameField();
    }

    @Bean
    public Level level(GameField gameField) {
        Level level = new Level();

        TiledMapTileLayer groundLayer = gameField.getGroundLayer();
        int mapWidth = groundLayer.getWidth();
        int mapHeight = groundLayer.getHeight();
        level.setBounds(mapWidth, mapHeight);

        return level;
    }

    @Bean
    public LevelGraphics levelGraphics() {
        return new LevelGraphics();
    }

    @Bean
    public UIState uiState() {
        return new UIState();
    }

    @Bean
    public KeyboardListener keyboardListener() {
        return new GdxKeyboardListener();
    }

    @Bean
    public CollisionManager collisionManager(Level level) {
        return new CollisionManager(level);
    }

    @Bean(name = "fileLevelGenerator")
    public LevelGenerator fileLevelGenerator() {
        return new FileLevelGenerator("level.txt");
    }

    @Bean(name = "randomLevelGenerator")
    public LevelGenerator randomLevelGenerator(GameField gameField) {
        TiledMapTileLayer groundLayer = gameField.getGroundLayer();
        int width = groundLayer.getWidth();
        int height = groundLayer.getHeight();
        int enemyCount = 3;

        return new RandomLevelGenerator(width, height, enemyCount);
    }
}
