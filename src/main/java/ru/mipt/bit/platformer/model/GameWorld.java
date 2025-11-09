package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.*;

public class GameWorld {
    private final List<TankModel> tanks = new ArrayList<>();
    private final List<Obstacle> obstacles;
    private final int width;
    private final int height;

    public GameWorld(List<Obstacle> obstacles, int width, int height) {
        this.obstacles = obstacles;
        this.width = width;
        this.height = height;
    }

    public void addTank(TankModel tank) {
        tanks.add(tank);
    }

    public List<TankModel> getTanks() {
        return tanks;
    }

    // Проверяем, можно ли занять клетку
    public boolean isBlocked(GridPoint2 pos, TankModel self) {
        //  Границы карты
        // Проверка выхода за пределы карты
        if (pos.x < 0 || pos.y < 0 || pos.x >= width || pos.y >= height - 1) return true;

        // Препятствия
        for (Obstacle o : obstacles) {
            if (o.getCoordinates().equals(pos)) return true;
        }

        // Другие танки
        for (TankModel tank : tanks) {
            if (tank == self) continue;
            GridPoint2 cur = tank.getCoordinates();
            GridPoint2 dest = tank.getDestination();
            if (cur.equals(pos) || dest.equals(pos)) return true;
        }

        return false;
    }

    // Упрощённый вариант для CollisionChecker
    public boolean isBlocked(GridPoint2 pos) {
        return isBlocked(pos, null);
    }
}
