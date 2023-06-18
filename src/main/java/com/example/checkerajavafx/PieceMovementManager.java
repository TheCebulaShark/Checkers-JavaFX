package com.example.checkerajavafx;

import javafx.scene.paint.Color;

import java.util.List;

public class PieceMovementManager {
    private Tile[][] board;
    private List<PieceMovement> possibleMoves;

    public PieceMovementManager(Tile[][] board) {
        this.board = board;
    }

    public void movePiece(PieceColor color) {
        clearHighlightedTiles();

        possibleMoves = PieceMovement.getPossibleMoves(board, color);
        highlightPossibleMoves();
    }

    public void highlightPossibleMoves() {
        for (PieceMovement movement : possibleMoves) {
            int startX = movement.getStartX();
            int startY = movement.getStartY();
            int endX = movement.getEndX();
            int endY = movement.getEndY();

            board[endX][endY].setColor(Color.valueOf(GameConstants.POSSIBLE_PIECES_TO_MOVES_FIELD_COLOR_HEX));
        }
    }

    public void clearHighlightedTiles() {
        for (int column = 0; column < GameConstants.TILES_PER_COLUMN; column++) {
            for (int row = 0; row < GameConstants.TILES_PER_ROW; row++) {
                board[column][row].setDefaultColor();
            }
        }
    }
}
