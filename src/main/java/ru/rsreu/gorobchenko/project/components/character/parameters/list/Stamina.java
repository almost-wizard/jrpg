package ru.rsreu.gorobchenko.project.components.character.parameters.list;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.DynamicValue;

public class Stamina extends DynamicValue {
    private TimerAction restoreTimer;

    private final DynamicValue restoreCoefficient = new DynamicValue(1, 10);

    public Stamina(int maxValue) {
        super(maxValue);
    }

    public boolean hasEnoughToRoll() {
        return getCurrentValue() >= 40;
    }

    public void startRestoreTimer() {
        TimerAction timer = getRestoreTimer();

        if (timer.isPaused()) {
            timer.resume();
        }
    }

    public DynamicValue getRestoreCoefficient() {
        return restoreCoefficient;
    }

    private TimerAction getRestoreTimer() {
        if (restoreTimer == null) {
            restoreTimer = constructRestoreTimer();
        }
        return restoreTimer;
    }

    private TimerAction constructRestoreTimer() {
        return FXGL.getGameTimer().runAtInterval(() -> {
            this.increment(getRestoreCoefficient().getCurrentValue());
            if (this.isMaximum()) {
                this.restoreTimer.pause();
            }
        }, Duration.seconds(0.06));
    }
}
