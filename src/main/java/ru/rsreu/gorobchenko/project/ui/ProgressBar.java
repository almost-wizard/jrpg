package ru.rsreu.gorobchenko.project.ui;

import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public enum ProgressBar {
    PLAYER_HEALTH(new Dimension2D(200, 30), Color.rgb(160, 2, 7), Color.rgb(14, 9, 8)),
    NPC_HEALTH(new Dimension2D(35, 6), Color.rgb(160, 2, 7), Color.rgb(14, 9, 8)) {
        @Override
        public Rectangle constructBg() {
            Rectangle bg =  super.constructBg();
            bg.setStrokeWidth(1);
            return bg;
        }
    },
    INTOXICATION(new Dimension2D(100, 10), Color.rgb(19, 143, 23), Color.rgb(14, 9, 8)),
    STAMINA(new Dimension2D(200, 8), Color.rgb(22, 103, 216), Color.rgb(14, 9, 8));

    private final Dimension2D size;
    private final Color barColor;
    private final Color bgColor;

    ProgressBar(Dimension2D size, Color barColor, Color bgColor) {
        this.size = size;
        this.barColor = barColor;
        this.bgColor = bgColor;
    }

    public Rectangle constructBg() {
        Rectangle bg = constructRectangle();
        bg.setFill(this.bgColor);
        bg.setStrokeWidth(3);
        return bg;
    }

    public Rectangle constructBar() {
        Rectangle bar = constructRectangle();
        bar.setFill(this.barColor);
        return bar;
    }

    public Dimension2D getSize() {
        return size;
    }

    private Rectangle constructRectangle() {
        Rectangle rectangle = new Rectangle(size.getWidth(), size.getHeight());
        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);
        rectangle.setStroke(bgColor);
        return rectangle;
    }
}
