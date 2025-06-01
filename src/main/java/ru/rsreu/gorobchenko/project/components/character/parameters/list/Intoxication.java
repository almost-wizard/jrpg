package ru.rsreu.gorobchenko.project.components.character.parameters.list;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.DynamicValue;

public class Intoxication extends DynamicValue {
    private static TimerAction restoreTimer;

    public Intoxication(int maxValue) {
        super(0, maxValue);
    }

    public boolean hasEnoughToDrinkPotion(int elixirIntoxication) {
        return getCurrentValue() <= 100 - elixirIntoxication;
    }

    public void takePotion(int elixirIntoxication) {
        if (!hasEnoughToDrinkPotion(elixirIntoxication)) {
            return;
        }

        increment(elixirIntoxication);

        if (restoreTimer == null) {
            restoreTimer = FXGL.getGameTimer().runAtInterval(() -> {
                decrement(1);
                if (isMinimum()) {
                    restoreTimer.pause();
                }
            }, Duration.seconds(0.5));
        }

        if (restoreTimer.isPaused()) {
            restoreTimer.resume();
        }
    }
}
