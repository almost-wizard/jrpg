package ru.rsreu.gorobchenko.project.ui.button;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class ButtonFactory {
    public static CustomButton createMenuButton(String text) {
        Color defaultColor = Color.color(1, 0.78, 0, 1);
        Color hoverColor = Color.color(0.8, 0.6, 0, 1);
        Color pressedColor = Color.color(1, 0.65, 0, 1);

        Font font = new Font(TextType.GOTHIC.getFontName(), TextSize.M.getTextSize());
        CustomButton button = new CustomButton(text, font);
        button.setBackground(Background.fill(Color.TRANSPARENT));

        button.textFillProperty().bind(
                Bindings.when(button.pressedProperty())
                        .then(pressedColor)
                        .otherwise(
                                Bindings.when(button.hoverProperty())
                                        .then(hoverColor)
                                        .otherwise(defaultColor)
                        )
        );

        return button;
    }

    public static InventoryCellButton createInventoryCellButton(Item item, int tileSize) {
        return new InventoryCellButton(item, tileSize);
    }

    public static CustomButton createKeyHintButton(KeyCode keyCode) {
        CornerRadii borderRadius = new CornerRadii(3);

        Background defaultBg = new Background(new BackgroundFill(Color.color(1, 0.78, 0, 1), borderRadius, Insets.EMPTY));

        Font font = new Font(TextType.GOTHIC.getFontName(), TextSize.XXS.getTextSize());
        CustomButton button = new CustomButton(keyCode.toString(), font);

        button.setBorder(new Border(new BorderStroke(Color.rgb(174, 88, 33), BorderStrokeStyle.SOLID, borderRadius, new BorderWidths(0.5))));
        button.setPadding(Insets.EMPTY);
        button.setPrefSize(12, 12);

        button.setTextFill(Color.rgb(174, 88, 33));
        button.setBackground(defaultBg);

        button.setFocusTraversable(false);

        return button;
    }
}
