package ru.rsreu.gorobchenko.project.ui.text.type;

public enum TextSize {
    XL(80),
    L(50),
    M(30),
    S(20),
    XS(10),
    XXS(7),
    ;

    private final int textSize;

    TextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }
}
