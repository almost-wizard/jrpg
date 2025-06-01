package ru.rsreu.gorobchenko.project.scenes;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlayer;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlaylist;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Intro extends IntroScene {
    private static final int BASE_CIRCLE_SIZE = 150;

    private final List<Animation<?>> animations = new ArrayList<>();

    public Intro() {
        var imageCircle = new Circle(BASE_CIRCLE_SIZE, BASE_CIRCLE_SIZE, BASE_CIRCLE_SIZE);
//        imageCircle.setFill(new ImagePattern(new Image("assets/textures/avatars/guitarist.png")));
        imageCircle.setFill(new ImagePattern(new Image("assets/textures/icon-upscaled.png")));
        imageCircle.setTranslateX(getAppWidth() / 2.0 - BASE_CIRCLE_SIZE);
        imageCircle.setTranslateY(getAppHeight() / 2.0 - BASE_CIRCLE_SIZE);

        Text authorTitle = new Text("by Alexander");
        authorTitle.setFont(new Font("Old English Text MT", 80));
        authorTitle.setFill(TextColor.PRIMARY_YELLOW.getColor());
        authorTitle.setTranslateX((getAppWidth() - authorTitle.getBoundsInLocal().getWidth()) / 2.0);
        authorTitle.setTranslateY(getAppHeight() / 2.0 + BASE_CIRCLE_SIZE + 60);
        authorTitle.setOpacity(0);
        authorTitle.setTextAlignment(TextAlignment.CENTER);

        animations.add(FXGL.animationBuilder()
                .duration(Duration.seconds(0.1))
                .delay(Duration.seconds(0.1))
                .fadeIn(imageCircle)
                .build());

        var titleFadeInAnimation = FXGL.animationBuilder()
                .duration(Duration.seconds(10))
                .delay(Duration.seconds(1))
                .interpolator(Interpolators.CUBIC.EASE_IN())
                .fadeIn(authorTitle)
                .build();

        Pane pane = new Pane(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()), imageCircle, authorTitle);

        var fadeOutAnimation = FXGL.animationBuilder()
                .duration(Duration.seconds(0.1))
                .delay(Duration.seconds(11.5))
                .fadeOut(pane)
                .build();


        var delayAnimation = FXGL.animationBuilder()
                .duration(Duration.seconds(2.5))
                .delay(Duration.seconds(13))
                .fade(authorTitle).from(0).to(0)
                .build();


        Rectangle blackBackground = new Rectangle(getAppWidth(), getAppHeight());
        blackBackground.setFill(Color.BLACK);

        animations.add(titleFadeInAnimation);
        animations.add(fadeOutAnimation);
        animations.add(delayAnimation);

        animations.getLast().setOnFinished(this::finishIntro);

        getContentRoot().getChildren().addAll(blackBackground, pane);
    }

    @Override
    protected void onUpdate(double tpf) {
        animations.forEach(a -> a.onUpdate(tpf));
    }

    @Override
    public void startIntro() {
        MusicPlayer.getInstance().setPlaylist(MusicPlaylist.GAME);
        animations.forEach(Animation::start);
    }
}
