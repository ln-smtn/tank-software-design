package ru.mipt.bit.platformer.model;
import com.badlogic.gdx.math.GridPoint2; // используется только как контейнер координат
import java.util.function.Predicate; // для передачи проверки коллизий
import java.util.Random;


// Класс — чистая модель танка: состояние, запрос на движение, прогресс.
public class TankModel {

    private static final float DEFAULT_MOVEMENT_SPEED = 5f; // скорость как в вашем коде (можно менять)

    private final GridPoint2 coordinates; // текущая целая координата на сетке
    private final GridPoint2 destination; // цель перемещения (целая клетка)
    private float movementProgress = 1f; // [0..1] прогресс между coordinates и destination
    private float rotation = 0f; // текущий угол для отрисовки
    private final float movementSpeed; // скорость (в секундах?) — оставляем как коэффициент
    private int health;

    // Изменим конструктор:
    public TankModel(GridPoint2 startCoordinates, float movementSpeed) {
        this.coordinates = new GridPoint2(startCoordinates);
        this.destination = new GridPoint2(startCoordinates);
        this.movementSpeed = movementSpeed;
        this.movementProgress = 1f;
        this.rotation = 0f;
        this.health = 80 + new Random().nextInt(21); // 80–100
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, health); }
    public boolean isAlive() { return health > 0; }


    // Попытка запросить движение. isBlocked — предикат, который возвращает true, если клетка занята.
    // Возвращает true, если движение стартовало, false — если движение невозможно (еще двигаемся или блок).
    public boolean requestMove(Direction direction, Predicate<GridPoint2> isBlocked) {
    // Если мы уже движемся — отказываем
        if (movementProgress < 1f) {
            return false; // ещё в движении
        }
    // Вычисляем новую целевую клетку
        GridPoint2 newDest = new GridPoint2(coordinates.x + direction.dx, coordinates.y + direction.dy);

    // Запрос предиката — если заблокировано, не двигаемся
        if (isBlocked != null && isBlocked.test(newDest)) {
            return false; // коллизия
        }
    // Инициализируем движение
        destination.set(newDest); // ставим цель
        movementProgress = 0f; // старт прогресса
        rotation = direction.rotation; // поворот для визуализации
        return true; // движение началось
    }


    // Обновляем прогресс движения. фиксируем координаты по завершении
    public void update(float deltaTime) {
// Накапливаем прогресс с учётом скорости
        movementProgress = Math.min(1f, movementProgress + deltaTime * movementSpeed);
// Если движение завершено — фиксируем координаты
        if (movementProgress >= 1f) {
            coordinates.set(destination);
            movementProgress = 1f; // обрезаем погрешности
        }
    }


    // Геттеры — необходимы для View и тестов, возврат копий
    public GridPoint2 getCoordinates() { return new GridPoint2(coordinates); } // возвращаем копию — защищаем внутреннее состояние
    public GridPoint2 getDestination() { return new GridPoint2(destination); } // копия
    public float getMovementProgress() { return movementProgress; }
    public float getRotation() { return rotation; }
}
