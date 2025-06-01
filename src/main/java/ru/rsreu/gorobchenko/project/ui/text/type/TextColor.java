package ru.rsreu.gorobchenko.project.ui.text.type;

import javafx.scene.paint.Color;

public enum TextColor {
    DEAD_RED(Color.rgb(143, 34, 34)),
    PRIMARY_YELLOW(Color.color(1, 0.78, 0, 1)),
    WHITE(Color.color(1, 1, 1, 1));

    private final Color color;

    TextColor(Color color) {
        this.color = color;
    }


    public Color getColor() {
        return this.color;
    }
}
