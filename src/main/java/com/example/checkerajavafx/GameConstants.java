package com.example.checkerajavafx;

public class GameConstants {
    public static final int TILES_PER_ROW = 8;
    public static final int TILES_PER_COLUMN = 8;
    public static final int ROWS_OF_PIECES = 3;

    public static final int TILE_SIZE = 75;
    public static final int BOARD_WIDTH = TILE_SIZE * TILES_PER_ROW;
    public static final int BOARD_HEIGHT = TILE_SIZE * TILES_PER_COLUMN;

    public static final int MENU_WIDTH = BOARD_WIDTH;
    public static final int MENU_HEIGHT = 150;

    public static final int APP_WIDTH = BOARD_WIDTH;
    public static final int APP_HEIGHT = BOARD_HEIGHT + MENU_HEIGHT;

    public static final String LIGHT_FIELD_COLOR_HEX = "#ffee9c";
    public static final String DARK_FIELD_COLOR_HEX = "#08752b";
    public static final String POSSIBLE_PIECES_TO_MOVES_FIELD_COLOR_HEX = "#2da3e3";
    public static final String POSSIBLE_MOVES_FIELD_COLOR_HEX = "#e60721";
    public static final String LIGHT_PIECE_COLOR_HEX = "#f7f3da";
    public static final String DARK_PIECE_COLOR_HEX = "#eb1e2c";

    public static final int TEXT_FONT_SIZE = 24;
    public static final int TEXT_OFFSET_Y = -25;

    public static final int BUTTONS_WIDTH = 150;
    public static final int BUTTONS_HEIGHT = 25;

    public static final int BUTTONS_OFFSET_X = 75;
    public static final int BUTTONS_OFFSET_Y = 25;
    public static final double ELLIPSE_SIZE_X = TILE_SIZE * 0.35;
    public static final double ELLIPSE_SIZE_Y = TILE_SIZE * 0.28;

    public static final double CROWN_SIZE = TILE_SIZE * 0.45;

    public static final double PIECE_STROKE_VALUE = TILE_SIZE * 0.025;
}
