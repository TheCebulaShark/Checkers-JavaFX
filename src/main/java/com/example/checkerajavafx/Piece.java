package com.example.checkerajavafx;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.example.checkerajavafx.GameConstants.*;

public class Piece extends StackPane {

    private int x;
    private int y;
    private PieceColor color;
    private boolean isQueen;

    public Piece(PieceColor color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isQueen = false;

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        Ellipse pieceBg = new Ellipse(ELLIPSE_SIZE_X, ELLIPSE_SIZE_Y);
        pieceBg.setFill(Color.BLACK);
        pieceBg.setStroke(Color.BLACK);
        pieceBg.setStrokeWidth(PIECE_STROKE_VALUE);

        pieceBg.setTranslateX((TILE_SIZE - ELLIPSE_SIZE_X * 2) / 2);
        pieceBg.setTranslateY((TILE_SIZE - ELLIPSE_SIZE_Y * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse pieceVisual = new Ellipse(ELLIPSE_SIZE_X, ELLIPSE_SIZE_Y);
        pieceVisual.setFill(color == PieceColor.DARK ? Color.valueOf(DARK_PIECE_COLOR_HEX) : Color.valueOf(LIGHT_PIECE_COLOR_HEX));
        pieceVisual.setStroke(Color.BLACK);
        pieceVisual.setStrokeWidth(PIECE_STROKE_VALUE);

        pieceVisual.setTranslateX((TILE_SIZE - ELLIPSE_SIZE_X * 2) / 2);
        pieceVisual.setTranslateY((TILE_SIZE - ELLIPSE_SIZE_Y * 2) / 2);

        getChildren().add(pieceBg);
        getChildren().add(pieceVisual);
    }

    public PieceColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isQueen() {
        return isQueen;
    }

    public void setQueen(boolean queen) {
        isQueen = queen;
    }
}
