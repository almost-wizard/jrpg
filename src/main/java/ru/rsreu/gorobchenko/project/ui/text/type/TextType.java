package ru.rsreu.gorobchenko.project.ui.text.type;

public enum TextType {
    GOTHIC("Old English Text MT"),
    INK_FREE("Ink Free"),
    ST_SONG("STSong");

    private final String fontName;

    TextType(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }
}
