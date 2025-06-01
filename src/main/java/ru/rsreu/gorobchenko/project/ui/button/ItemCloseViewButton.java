package ru.rsreu.gorobchenko.project.ui.button;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class ItemCloseViewButton extends CustomButton {
    private static final int BORDER_RADIUS = 15;

    public ItemCloseViewButton(int width, int height) {
        super("", constructFont());

        this.setAlignment(Pos.TOP_LEFT);
        this.setTextFill(Color.WHITE);
        this.setPrefSize(width, height);
        this.setWrapText(true);

        applyStyle(this);
    }

    public static void applyStyle(Region shape) {
        shape.setPadding(new Insets(5, 5, 5, 5));
        shape.setFocusTraversable(false);
        shape.setBorder(constructBorder(Color.rgb(22, 47, 75, 1)));
        bindBackground(shape, "");
    }

    public static Font constructFont() {
        return new Font(TextType.INK_FREE.getFontName(), TextSize.S.getTextSize());
    }

    public static void bindBackground(Region button, String spritePath) {
        BackgroundImage bgImage = null;
        if (!spritePath.isEmpty()) {
            bgImage = new BackgroundImage(new Image(spritePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        }

        BackgroundFill defaultBgColor = new BackgroundFill(Color.rgb(67, 78, 143, 0.9), new CornerRadii(BORDER_RADIUS), Insets.EMPTY);
        Background defaultBg = new Background(new BackgroundFill[]{defaultBgColor}, new BackgroundImage[]{bgImage});

        button.setBackground(defaultBg);
    }

    private static Border constructBorder(Color color) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.DASHED, new CornerRadii(BORDER_RADIUS), new BorderWidths(3)));
    }
}
