package ru.rsreu.gorobchenko.project.gameplay.daytime;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.rsreu.gorobchenko.project.Config;

public enum DayTimeType {
    MORNING(Color.rgb(255, 144, 99, 0.2), Config.DEBUG ? 3 : 30),
    DAY(Color.rgb(255, 255, 255, 0), Config.DEBUG ? 3 : 120),
    EVENING(Color.rgb(99, 33, 255, 0.2), Config.DEBUG ? 3 : 30),
    NIGHT(Color.rgb(22, 22, 106, 0.5), Config.DEBUG ? 3 : 180);

    private final Paint colorCorrection;
    private final int duration;

    DayTimeType(Paint colorCorrection, int duration) {
        this.colorCorrection = colorCorrection;
        this.duration = duration;
    }

    public Paint getColorCorrection() {
        return this.colorCorrection;
    }

    public int getDuration() {
        return this.duration;
    }
}