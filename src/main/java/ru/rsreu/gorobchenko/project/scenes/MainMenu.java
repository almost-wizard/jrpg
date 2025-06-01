package ru.rsreu.gorobchenko.project.scenes;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.shared.utils.NodeUtils;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;

import static com.almasb.fxgl.dsl.FXGLForKtKt.set;

public class MainMenu extends FXGLMenu {
    private static final int BUTTON_PADDING = 40;
    private final double START_BUTTONS_Y = getAppHeight() * 0.7;

    private final Animation<?> inAnimation;
    private final Animation<?> outAnimation;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        Pane elementsPane = createElementsPane();

        Rectangle blackBackground = new Rectangle(getAppWidth(), getAppHeight());
        blackBackground.setFill(Color.BLACK);

        getContentRoot().getChildren().addAll(blackBackground, elementsPane);

        inAnimation = FXGL.animationBuilder()
                .duration(Duration.millis(3500))
                .fadeIn(elementsPane)
                .build();

        outAnimation = FXGL.animationBuilder()
                .duration(Duration.millis(Config.FadeTransition.DURATION))
                .fadeOut(elementsPane)
                .build();

        set("isGameInitialized", false);
    }

    private Pane createElementsPane() {
        var background = FXGL.texture("screens/main-menu.png", getAppWidth(), getAppHeight());

        Color titleColor = new Color(1, 0.78, 0, 1);

        Text title = new Text(FXGL.getSettings().getTitle());
        title.setFont(new Font("Old English Text MT", 150));
        title.setFill(titleColor);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);
        title.setTranslateY(170);
        NodeUtils.bindXCenter(title, getAppWidth());

        Button newGameButton = ButtonFactory.createMenuButton("New Game");
        newGameButton.setOnMouseClicked(e -> {
            outAnimation.setOnFinished(this::fireNewGame);
            outAnimation.start();
        });
        NodeUtils.bindXCenter(newGameButton, getAppWidth());
        newGameButton.setTranslateY(START_BUTTONS_Y);

        Button exitButton = ButtonFactory.createMenuButton("Exit");
        exitButton.setOnMouseClicked(e -> fireExit());
        NodeUtils.bindXCenter(exitButton, getAppWidth());
        exitButton.setTranslateY(START_BUTTONS_Y + BUTTON_PADDING);

        return new Pane(background, title, newGameButton, exitButton);
    }

    @Override
    public void onCreate() {
        inAnimation.start();
    }

    @Override
    protected void onUpdate(double tpf) {
        inAnimation.onUpdate(tpf);
        outAnimation.onUpdate(tpf);
    }
}