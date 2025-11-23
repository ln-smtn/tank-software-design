package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.UUID;

public class Tree implements GameObject {

    private final String id = "tree-" + UUID.randomUUID();
    private final GridPoint2 position;

    public Tree(GridPoint2 pos) {
        this.position = new GridPoint2(pos);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.TREE;
    }

    @Override
    public GridPoint2 getPosition() {
        return new GridPoint2(position);
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void update(float deltaTime) {
        // дерево статично
    }
}
