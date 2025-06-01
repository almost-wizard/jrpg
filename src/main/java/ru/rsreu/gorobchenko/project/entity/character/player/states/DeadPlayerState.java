package ru.rsreu.gorobchenko.project.entity.character.player.states;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlayer;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlaylist;
import ru.rsreu.gorobchenko.project.shared.utils.NodeUtils;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class DeadPlayerState extends EntityState {
    private final Pane pane = constructEngGamePane();

    private final Entity entity;
    private final CharacterParametersComponent parameters;
    private final CharacterAnimationComponent animation;
    private final CharacterAnimation playerAnimation;

    public DeadPlayerState(Entity entity, CharacterAnimation playerAnimation) {
        this.entity = entity;
        this.parameters = entity.getComponent(PlayerParametersComponent.class);
        this.animation = entity.getComponent(CharacterAnimationComponent.class);
        this.playerAnimation = playerAnimation;
    }

    private Pane constructEngGamePane() {
        Pane pane = new Pane();
        Rectangle outRect = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.BLACK);
        outRect.setOpacity(0);

        Rectangle bg = new Rectangle(0, 0, FXGL.getAppWidth(), FXGL.getAppHeight());
        bg.setOpacity(0.7);

        Text text = TextFactory.create("YOU DEAD", TextType.GOTHIC, TextColor.DEAD_RED, TextSize.XL);
        NodeUtils.bindCenter(text, bg.getWidth(), bg.getHeight());

        Button newGameButton = ButtonFactory.createMenuButton("New Game");
        newGameButton.setOnMouseClicked((MouseEvent e) -> {
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
        newGameButton.setTranslateY(FXGL.getAppHeight() / 2.0 + 100);

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
        exitButton.setTranslateY(FXGL.getAppHeight() / 2.0 + 150);

        pane.getChildren().addAll(bg, text, newGameButton, exitButton);

        return pane;
    }

    @Override
    public void onEntering() {
        entity.getComponent(PhysicsComponent.class).setLinearVelocity(Point2D.ZERO);
        parameters.kill(() -> animation.playAnimation(playerAnimation.getDie(), () -> {
            MusicPlayer.getInstance().playOnce(MusicPlaylist.DEAD.getMusicList().getFirst());
            pane.setOpacity(0);
            FXGL.getGameScene().getContentRoot().getChildren().add(pane);

            FXGL.animationBuilder()
                    .duration(Duration.millis(Config.FadeTransition.DURATION))
                    .fadeIn(pane)
                    .buildAndPlay();
        }));
    }
}
