package ru.mipt.bit.platformer.view;
import com.badlogic.gdx.graphics.Texture;                 // Для загрузки изображения (файла .png)
import com.badlogic.gdx.graphics.g2d.Batch;               // Batch нужен для рисования (SpriteBatch реализует его)
import com.badlogic.gdx.graphics.g2d.TextureRegion;       // Обёртка над Texture, удобна для спрайтов
import com.badlogic.gdx.math.Rectangle;                   // Прямоугольник позиции и размеров
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;     // Чтобы позиционировать по тайлам
import ru.mipt.bit.platformer.model.TankModel;            // Модель танка — содержит позицию/прогресс/rotation
import ru.mipt.bit.platformer.util.TileMovement;          // Вспомогательный класс для интерполяции между центрами тайлов

import static ru.mipt.bit.platformer.util.GdxGameUtils.*; // Утилиты для создания прямоугольника и рисования



// Класс "Танк" (игрок)
// Хранит текстуру, координаты
public class TankView {

    // Текстура (на диске) — хранится тут, потому что View отвечает за графику.
    private final Texture texture;               // файл images/tank_blue.png
    private final TextureRegion graphics;        // region на всякий случай (можно анимировать)
    private final Rectangle rectangle;           // область, в которой будет рисоваться танк
    private final TankModel model;               // ссылка на модель — view читает состояние из модели
    private final Batch batch;
    private final TileMovement movement;

    // Конструктор: сохраняем модель и создаём графические ресурсы
    public TankView(TankModel model, Batch batch, TileMovement movement) {
        this.model = model;     // сохраняем ссылку на модель (чтобы читать координаты)
        this.batch = batch;
        this.movement = movement;

        this.texture = new Texture("images/tank_blue.png");// загружаем текстуру (файл должен быть в assets/images/)
        this.graphics = new TextureRegion(texture);       // создаём region поверх всей текстуры
        this.rectangle = createBoundingRectangle(graphics); // создаём Rectangle нужного размера (утилита на основе region)
        // Не позиционируем rectangle тут — позиционирование делаем в draw через TileMovement,
        // т.к. модель содержит прогресс движения и destination/coordinates.
    }

    // render без аргументов
    public void render() {
        // movement.moveRectangleBetweenTileCenters -> вычисляет промежуточную позицию rectangle
        // между центрами клетки model.getCoordinates() и model.getDestination()
        movement.moveRectangleBetweenTileCenters(
                rectangle,
                model.getCoordinates(),       // текущая целая клетка (GridPoint2)
                model.getDestination(),       // куда движемся (GridPoint2)
                model.getMovementProgress()   // прогресс [0..1]
        );

        // После того, как rectangle поставлен в нужное место, рисуем texture region.
        // drawTextureRegionUnscaled — утилита, которая рисует graphics в rectangle без дополнительного масштабирования.
        drawTextureRegionUnscaled(batch, graphics, rectangle, model.getRotation());
    }

    // Освобождение ресурсов при выходе (важно чтобы texture.dispose() вызывался)
    public void dispose() {
        texture.dispose();
    }
}
