package com.example.checkerajavafx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private int x;
    private int y;
    private Piece piece;
    public Tile(boolean light_tile, int x, int y) {
        this.x = x;
        this.y = y;

        setFill(light_tile ? Color.valueOf(GameConstants.LIGHT_FIELD_COLOR_HEX) : Color.valueOf(GameConstants.DARK_FIELD_COLOR_HEX));
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setColor(Color color) {
        setFill(color);
    }

    public void setDefaultColor() {
        if ((x + y) % 2 == 0)
            setFill(Color.valueOf(GameConstants.LIGHT_FIELD_COLOR_HEX));
        else
            setFill(Color.valueOf(GameConstants.DARK_FIELD_COLOR_HEX));
    }

    public int getCordX() {
        return x;
    }

    public void setCordX(int x) {
        this.x = x;
    }

    public int getCordY() {
        return y;
    }

    public void setCordY(int y) {
        this.y = y;
    }
}
