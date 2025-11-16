package ru.mipt.bit.platformer.view;

/** Общий интерфейс для всех объектов, которые умеют рисовать себя и освобождать ресурсы */
public interface IView {
    void render();
    void dispose();
}
