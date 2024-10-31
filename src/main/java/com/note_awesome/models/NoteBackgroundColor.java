package com.note_awesome.models;

import javafx.scene.paint.Color;

public enum NoteBackgroundColor {
    NONE("#ffffff"),
    CORAL_RED("#faafa8"),
    PEACH_ORANGE(" #f39f76"),
    SAND_BROWN("#fff8b8"),
    MINT_GREEN(" #e2f6d3"),
    GRAY_GREEN("#b4ddd3"),
    SMOKE_GRAY("#d4e4ed"),
    DARK_BLUE("#aeccdc"),
    PURPLE_SUNSET("#d3bfdb"),
    PEACH("#f6e2dd"),
    CLAY_BROWN("#e9e3d4"),
    CHEL_GRAY("#efeff1");

    private final String hexCode;

    NoteBackgroundColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public Color getColor() {
        return Color.web(hexCode);
    }
}
