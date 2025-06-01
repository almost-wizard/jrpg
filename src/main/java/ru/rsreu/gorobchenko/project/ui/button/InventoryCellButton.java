package ru.rsreu.gorobchenko.project.ui.button;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class InventoryCellButton extends CustomButton {
    private static final int BORDER_RADIUS = 15;

    public InventoryCellButton(Item item, int tileSize) {
        super(constructText(item), constructFont());

        this.setAlignment(Pos.TOP_RIGHT);
        this.setTextFill(Color.WHITE);
        this.setPrefSize(tileSize, tileSize);

        applyStyle(this, item.getSmallSpritePath());
    }

    public static void applyStyle(Region shape, String spritePath) {
        shape.setPadding(new Insets(-5, 0, 0, 0));
        shape.setBorder(constructBorder(Color.rgb(22, 47, 75, 1)));
        bindBackground(shape, spritePath);
    }

    public static String constructText(Item item) {
        return item.isCollectable() && item.getCount() > 1 ? String.valueOf(item.getCount()) : "";
    }

    public static Font constructFont() {
        return new Font(TextType.GOTHIC.getFontName(), TextSize.S.getTextSize());
    }

    public static void bindBackground(Region button, String spritePath) {
        BackgroundImage bgImage = null;
        if (!spritePath.isEmpty()) {
            bgImage = new BackgroundImage(new Image(spritePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        }

        BackgroundFill defaultBgColor = new BackgroundFill(Color.rgb(67, 78, 143, 0.7), new CornerRadii(BORDER_RADIUS), Insets.EMPTY);
        BackgroundFill hoverBgColor = new BackgroundFill(Color.rgb(79, 120, 193, 0.8), new CornerRadii(BORDER_RADIUS), Insets.EMPTY);
        BackgroundFill pressedBgColor = new BackgroundFill(Color.rgb(67, 78, 143, 0.5), new CornerRadii(BORDER_RADIUS), Insets.EMPTY);

        Background defaultBg = new Background(new BackgroundFill[]{defaultBgColor}, new BackgroundImage[]{bgImage});
        Background hoverBg = new Background(new BackgroundFill[]{hoverBgColor}, new BackgroundImage[]{bgImage});
        Background pressedBg = new Background(new BackgroundFill[]{pressedBgColor}, new BackgroundImage[]{bgImage});


        button.backgroundProperty().bind(
                Bindings.when(button.pressedProperty())
                        .then(pressedBg)
                        .otherwise(
                                Bindings.when(button.hoverProperty().or(button.focusedProperty()))
                                        .then(hoverBg)
                                        .otherwise(defaultBg)
                        )
        );
    }

    private static Border constructBorder(Color color) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.DASHED, new CornerRadii(BORDER_RADIUS), new BorderWidths(3)));
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            this.setBorder(constructBorder(Color.rgb(68, 160, 24, 1)));
        } else {
            this.setBorder(constructBorder(Color.rgb(22, 47, 75, 1)));
        }
    }
}
