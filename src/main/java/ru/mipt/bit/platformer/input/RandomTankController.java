package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomTankController {
    private final TankModel tank;
    private final GameWorld world;
    private final Random random = new Random();
    private float timer = 0f;

    public RandomTankController(TankModel tank, GameWorld world) {
        this.tank = tank;
        this.world = world;
    }

    public void update(float deltaTime) {
        timer += deltaTime;

        // Двигаемся только если закончили предыдущее движение
        if (tank.getMovementProgress() < 1f) return;

        // Пробуем выбрать новое направление каждые 0.2 секунды
        if (timer >= 0.2f) {
            timer = 0f;

            // Перемешиваем направления
            List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
            Collections.shuffle(directions, random);

            for (Direction dir : directions) {
                // передаем свой танк, чтобы метод isBlocked учитывал его как "себя"
                boolean started = tank.requestMove(dir, pos -> world.isBlocked(pos, tank));
                if (started) break;
            }
        }
    }
}

