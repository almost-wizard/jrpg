package ru.rsreu.gorobchenko.project.ui.button;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomButton extends javafx.scene.control.Button {
    public CustomButton(String text, Font font) {
        super(text);

        setFont(font);

        // This needs for correct work of shared.NodeUtils
        Text t = new Text(text);
        t.setFont(font);
        getChildren().add(t);
    }
}
