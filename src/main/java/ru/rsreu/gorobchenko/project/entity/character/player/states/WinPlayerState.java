package ru.rsreu.gorobchenko.project.entity.character.player.states;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlayer;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlaylist;
import ru.rsreu.gorobchenko.project.shared.utils.NodeUtils;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class WinPlayerState extends EntityState {
    private final Pane pane = constructEngGamePane();

    private final Entity entity;

    public WinPlayerState(Entity entity) {
        this.entity = entity;
    }

    private Pane constructEngGamePane() {
        Pane pane = new Pane();
        Rectangle outRect = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.BLACK);
        outRect.setOpacity(0);

        Rectangle bg = new Rectangle(0, 0, FXGL.getAppWidth(), FXGL.getAppHeight());
        bg.setOpacity(0.7);

        Text text = TextFactory.create("YOU WIN", TextType.GOTHIC, TextColor.PRIMARY_YELLOW, TextSize.XL);
        NodeUtils.bindCenter(text, bg.getWidth(), bg.getHeight());

        Button continueButton = ButtonFactory.createMenuButton("Continue exploring the world");
        continueButton.setOnMouseClicked(e -> FXGL.animationBuilder()
                .duration(Duration.millis(Config.FadeTransition.DURATION))
                .fadeOut(pane)
                .buildAndPlay());
        NodeUtils.bindXCenter(continueButton, FXGL.getAppWidth());
        continueButton.setTranslateY(FXGL.getAppHeight() / 2.0 + 100);

        Button newGameButton = ButtonFactory.createMenuButton("New Game");
        newGameButton.setOnMouseClicked(e -> {
            pane.getChildren().add(outRect);
            FXGL.animationBuilder()
                    .onFinished(() -> {
                        FXGL.getGameController().startNewGame();
                        FXGL.getGameScene().getContentRoot().getChildren().remove(pane);
                    })
                    .duration(Duration.millis(Config.FadeTransition.DURATION))
                    .fadeIn(outRect)
                    .buildAndPlay();
        });
        NodeUtils.bindXCenter(newGameButton, FXGL.getAppWidth());
        newGameButton.setTranslateY(FXGL.getAppHeight() / 2.0 + 150);

        Button exitButton = ButtonFactory.createMenuButton("Exit");
        exitButton.setOnMouseClicked(e -> {
            pane.getChildren().add(outRect);
            FXGL.animationBuilder()
                    .onFinished(() -> {
                        FXGL.getGameController().gotoMainMenu();
                        FXGL.getGameScene().getContentRoot().getChildren().remove(pane);
                    })
                    .duration(Duration.millis(Config.FadeTransition.DURATION))
                    .fadeIn(outRect)
                    .buildAndPlay();
        });
        NodeUtils.bindXCenter(exitButton, FXGL.getAppWidth());
        exitButton.setTranslateY(FXGL.getAppHeight() / 2.0 + 200);

        pane.getChildren().addAll(bg, text, continueButton, newGameButton, exitButton);

        return pane;
    }

    @Override
    public void onEntering() {
        MusicPlayer.getInstance().playOnce(MusicPlaylist.WIN.getMusicList().getFirst(), MusicPlaylist.GAME);

        entity.getComponent(PhysicsComponent.class).setLinearVelocity(Point2D.ZERO);
        pane.setOpacity(0);
        FXGL.getGameScene().getContentRoot().getChildren().add(pane);

        FXGL.animationBuilder()
                .delay(Duration.millis(500))
                .duration(Duration.millis(Config.FadeTransition.DURATION))
                .fadeIn(pane)
                .buildAndPlay();
    }
}
