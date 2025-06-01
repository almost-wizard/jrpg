package ru.rsreu.gorobchenko.project.gameplay.daytime;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.Config;

public class DayTimeManager {
    private static DayTimeManager instance;

    private final Pane overlay;
    private final Rectangle[] filters;
    private int activeFilterIndex;

    public static final Duration TRANSITION_DURATION = Duration.seconds(Config.DEBUG ? 1 : 5);

    private DayTimeType currentDayTime = DayTimeType.DAY;

    private DayTimeManager() {
        overlay = new Pane();

        Rectangle active = new Rectangle(Config.Map.WIDTH, Config.Map.HEIGHT, currentDayTime.getColorCorrection());
        Rectangle next = new Rectangle(Config.Map.WIDTH, Config.Map.HEIGHT, currentDayTime.getColorCorrection());
        next.setOpacity(0);

        filters = new Rectangle[]{active, next};
        activeFilterIndex = 0;

        overlay.getChildren().addAll(filters);
    }

    public static DayTimeManager getInstance() {
        if (instance == null) {
            instance = new DayTimeManager();
        }
        return instance;
    }

    public Pane getOverlayNode() {
        return overlay;
    }

    public void next() {
        DayTimeType[] values = DayTimeType.values();
        int nextIndex = currentDayTime.ordinal() + 1;
        if (nextIndex >= values.length) {
            nextIndex = 0;
        }
        _set(values[nextIndex], true);
    }

    public void forceSet(DayTimeType dayTimeType) {
        _set(dayTimeType, false);
    }

    public void set(DayTimeType dayTimeType) {
        _set(dayTimeType, true);
    }

    private void _set(DayTimeType newDayTime, boolean withAnimation) {
        Rectangle activeFilter = this.filters[activeFilterIndex];
        Rectangle newFilter = this.filters[1 - activeFilterIndex];

        newFilter.setFill(newDayTime.getColorCorrection());

        if (withAnimation) {
            FXGL.animationBuilder()
                    .duration(TRANSITION_DURATION)
                    .fadeOut(activeFilter)
                    .buildAndPlay();
            FXGL.animationBuilder()
                    .duration(TRANSITION_DURATION)
                    .fadeIn(newFilter)
                    .buildAndPlay();
        } else {
            activeFilter.setOpacity(0);
            newFilter.setOpacity(1);
        }

        activeFilterIndex += 1;
        if (activeFilterIndex >= filters.length) {
            activeFilterIndex = 0;
        }

        this.currentDayTime = newDayTime;
    }

    public DayTimeType getCurrentDayTime() {
        return currentDayTime;
    }


}
