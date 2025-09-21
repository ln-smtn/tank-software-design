package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

// Класс "Танк" (игрок)
// Хранит текстуру, координаты, направление и умеет двигаться по карте.
public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f; // скорость движения (меньше = медленнее)

    private final Texture texture;        // текстура танка
    private final TextureRegion graphics; // часть текстуры (для спрайта)
    private final Rectangle rectangle;    // прямоугольник для отрисовки

    private final GridPoint2 coordinates;          // текущие координаты (x,y)
    private final GridPoint2 destination;          // куда движется
    private float movementProgress = 1f;           // прогресс движения [0..1]
    private float rotation;                        // угол поворота спрайта

    public Tank(GridPoint2 startCoordinates) {
        this.texture = new Texture("images/tank_blue.png");
        this.graphics = new TextureRegion(texture);

        this.rectangle = createBoundingRectangle(graphics);

        this.coordinates = new GridPoint2(startCoordinates);
        this.destination = new GridPoint2(startCoordinates);

        this.rotation = 0f;
    }

    // Проверяем возможность движения в направлении (например, не врезаемся в дерево)
    public void requestMove(Direction direction, Tree obstacle) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 newDest = new GridPoint2(coordinates.x + direction.dx, coordinates.y + direction.dy);

            // Проверка на коллизию с деревом
            if (!obstacle.getCoordinates().equals(newDest)) {
                destination.set(newDest);
                movementProgress = 0f; // запускаем движение
                rotation = direction.rotation;
            }
        }
    }

    // Обновляем движение (каждый кадр)
    public void update(TileMovement movement, float deltaTime) {
        movement.moveRectangleBetweenTileCenters(rectangle, coordinates, destination, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destination);
        }
    }

    // Отрисовка танка
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    // Освобождение ресурсов
    public void dispose() {
        texture.dispose();
    }
}
