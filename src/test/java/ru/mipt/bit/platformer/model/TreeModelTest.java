package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeModelTest {
    @Test
    void coordinatesAreStored() {
        TreeModel t = new TreeModel(new GridPoint2(5,6));
        assertEquals(5, t.getCoordinates().x);
        assertEquals(6, t.getCoordinates().y);
    }
}
