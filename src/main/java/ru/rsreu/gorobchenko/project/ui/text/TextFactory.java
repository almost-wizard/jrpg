package ru.rsreu.gorobchenko.project.ui.text;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class TextFactory {
    public static Text create(String string, TextType textType, TextColor textColor, TextSize textSize) {
        return _create(string, textType, textColor, textSize);
    }

    public static Text create(String string, TextSize textSize) {
        return _create(string, TextType.GOTHIC, TextColor.PRIMARY_YELLOW, textSize);
    }

    private static Text _create(String string, TextType textType, TextColor textColor, TextSize textSize) {
        Text text = new Text(string);
        text.setFont(new Font(textType.getFontName(), textSize.getTextSize()));
        text.setFill(textColor.getColor());
        return text;
    }
}
