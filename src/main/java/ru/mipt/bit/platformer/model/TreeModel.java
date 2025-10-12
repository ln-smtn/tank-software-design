package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

// теперь дерево — частный случай препятствия
public class TreeModel implements Obstacle {

    private final GridPoint2 coordinates;

    public TreeModel(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
}
