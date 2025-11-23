package ru.mipt.bit.platformer.graphics;

public class UIState {
    private boolean healthBarsEnabled = false; // по умолчанию выключены

    public boolean isHealthBarsEnabled() {
        return healthBarsEnabled;
    }

    public void toggleHealthBars() {
        healthBarsEnabled = !healthBarsEnabled;
    }
}
