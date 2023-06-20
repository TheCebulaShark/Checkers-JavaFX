package com.example.checkerajavafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.example.checkerajavafx.GameConstants.*;

public class Piece extends StackPane {

    private int x;
    private int y;
    private PieceColor color;
    private boolean isKing;

    Ellipse pieceBg;
    Ellipse pieceVisual;
    ImageView crownImageView;

    public Piece(PieceColor color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isKing = false;

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        pieceBg = new Ellipse(ELLIPSE_SIZE_X, ELLIPSE_SIZE_Y);
        pieceBg.setFill(Color.BLACK);
        pieceBg.setStroke(Color.BLACK);
        pieceBg.setStrokeWidth(PIECE_STROKE_VALUE);

        pieceBg.setTranslateX((TILE_SIZE - ELLIPSE_SIZE_X * 2) / 2);
        pieceBg.setTranslateY((TILE_SIZE - ELLIPSE_SIZE_Y * 2) / 2 + TILE_SIZE * 0.07);

        pieceVisual = new Ellipse(ELLIPSE_SIZE_X, ELLIPSE_SIZE_Y);
        pieceVisual.setFill(color == PieceColor.DARK ? Color.valueOf(DARK_PIECE_COLOR_HEX) : Color.valueOf(LIGHT_PIECE_COLOR_HEX));
        pieceVisual.setStroke(Color.BLACK);
        pieceVisual.setStrokeWidth(PIECE_STROKE_VALUE);

        pieceVisual.setTranslateX((TILE_SIZE - ELLIPSE_SIZE_X * 2) / 2);
        pieceVisual.setTranslateY((TILE_SIZE - ELLIPSE_SIZE_Y * 2) / 2);

        crownImageView = new ImageView(new Image(getClass().getResourceAsStream("/crown.png")));
        crownImageView.setFitWidth(CROWN_SIZE);
        crownImageView.setFitHeight(CROWN_SIZE);
        crownImageView.setTranslateX((TILE_SIZE - ELLIPSE_SIZE_X * 2) / 2);
        crownImageView.setTranslateY((TILE_SIZE - ELLIPSE_SIZE_Y * 2) / 2);
        crownImageView.setVisible(false);

        getChildren().add(pieceBg);
        getChildren().add(pieceVisual);
        getChildren().add(crownImageView);

        setOnMouseClicked(this::handlePieceClicked);
    }

    public void handlePieceClicked(MouseEvent event) {
        CheckersApp.handlePieceClick(this);
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
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

    public boolean isKing() {
        return isKing;
    }

    public void setKing() {
        isKing = true;
        crownImageView.setVisible(true);
    }

    public void setInvincible() {
        pieceBg.setVisible(false);
        pieceVisual.setVisible(false);
        crownImageView.setVisible(false);
    }
}
