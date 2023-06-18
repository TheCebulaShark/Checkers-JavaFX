package com.example.checkerajavafx;

import java.util.ArrayList;
import java.util.List;

public class PieceMovement {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public PieceMovement(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    public static List<PieceMovement> getPossibleMoves(Tile[][] board, PieceColor color) {
        List<PieceMovement> possibleMoves = new ArrayList<>();

        int moveDir = color.moveDir;
        boolean wasCapture = false;

        for (int column = 0; column < GameConstants.TILES_PER_COLUMN; column++) {
            for (int row = 0; row < GameConstants.TILES_PER_ROW; row++) {
                Tile currentTile = board[column][row];
                Piece currentPiece = currentTile.getPiece();

                if (currentPiece != null && currentPiece.getColor() == color) {
                    if (row + 2 < GameConstants.TILES_PER_ROW && column + (2 * moveDir) < GameConstants.TILES_PER_COLUMN) {
                        Tile middleTile = board[column + moveDir][row + 1];
                        Tile targetTile = board[column + (2 * moveDir)][row + 2];
                        if (middleTile.getPiece() != null && middleTile.getPiece().getColor() != color && targetTile.getPiece() == null) {
                            possibleMoves.add(new PieceMovement(row, column, row + 2, column + (2 * moveDir)));
                            wasCapture = true;
                        }
                    }

                    if (row - 2 >= 0 && column + (2 * moveDir) < GameConstants.TILES_PER_COLUMN) {
                        Tile middleTile = board[column + moveDir][row - 1];
                        Tile targetTile = board[column + (2 * moveDir)][row - 2];
                        if (middleTile.getPiece() != null && middleTile.getPiece().getColor() != color && targetTile.getPiece() == null) {
                            possibleMoves.add(new PieceMovement(row, column, row - 2, column + (2 * moveDir)));
                            wasCapture = true;
                        }
                    }

                    if (currentPiece.isQueen()) {
                        //Tu dopisz pozostałą część kodu
                    }
                }
            }
        }
        if (!wasCapture) {
            for (int column = 0; column < GameConstants.TILES_PER_COLUMN; column++) {
                for (int row = 0; row < GameConstants.TILES_PER_ROW; row++) {
                    Tile currentTile = board[column][row];
                    Piece currentPiece = currentTile.getPiece();

                    if (currentPiece != null && currentPiece.getColor() == color) {
                        if (row + 1 < GameConstants.TILES_PER_ROW && column + moveDir < GameConstants.TILES_PER_COLUMN) {
                            Tile targetTile = board[column + moveDir][row + 1];
                            if (targetTile.getPiece() == null) {
                                possibleMoves.add(new PieceMovement(row, column, row + 1, column + moveDir));
                            }
                        }

                        if (row - 1 >= 0 && column + moveDir < GameConstants.TILES_PER_COLUMN) {
                            Tile targetTile = board[column + moveDir][row - 1];
                            if (targetTile.getPiece() == null) {
                                possibleMoves.add(new PieceMovement(row, column, row - 1, column + moveDir));
                            }
                        }

                        if (currentPiece.isQueen()) {

                        }
                    }
                }
            }
        }

        return possibleMoves;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
