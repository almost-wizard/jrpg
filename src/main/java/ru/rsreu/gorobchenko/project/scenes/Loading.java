package ru.rsreu.gorobchenko.project.scenes;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.shared.utils.NodeUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getb;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;

public class Loading extends LoadingScene {

    private final Pane dotsPane;

    public Loading() {
        getContentRoot().getChildren().add(new Rectangle(getAppWidth(), getAppHeight()));
        dotsPane = new Pane();

        for (int i = 0; i < 3; i++) {
            Rectangle dot = new Rectangle(20, 20, Color.color(1, 0.78, 0, 1));
            NodeUtils.bindCenter(dot, getAppWidth() + (i - 1) * 65, getAppHeight());

            FXGL.animationBuilder(this)
                    .delay(Duration.seconds(i * 0.5))
                    .interpolator(Interpolators.CUBIC.EASE_OUT())
                    .duration(Duration.seconds(1.36))
                    .repeatInfinitely()
                    .autoReverse(true)
                    .fadeIn(dot)
                    .buildAndPlay();

            dotsPane.getChildren().add(dot);
        }

        getContentRoot().getChildren().add(dotsPane);
    }

    @Override
    protected void onUpdate(double tpf) {
        try {
            if (getb("isGameInitialized")) {
                FXGL.animationBuilder(this)
                        .duration(Duration.millis(Config.FadeTransition.DURATION))
                        .fadeOut(dotsPane)
                        .buildAndPlay();
                set("isGameInitialized", false);
            }
        } catch (Exception e) {

        }
    }
}
