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

    public static List<PieceMovement> getPossibleCapturesForPiece(Tile[][] board, PieceColor color, Piece piece) {
        List<PieceMovement> possibleMoves = new ArrayList<>();
        int startX = piece.getX();
        int startY = piece.getY();

        if (piece.isKing()) {
            for (int dirX = -1; dirX <= 1; dirX += 2) {
                for (int dirY = -1; dirY <= 1; dirY += 2) {
                    checkKingCaptureMoves(board, possibleMoves, color, startX, startY, dirX, dirY);
                }
            }
        }
        else {
            for (int dirX = -1; dirX <= 1; dirX += 2) {
                for (int dirY = -1; dirY <= 1; dirY += 2) {
                    checkPawnCaptureMoves(board, possibleMoves, color, startX, startY, dirX, dirY);
                }
            }
        }

        return possibleMoves;
    }
    public static List<PieceMovement> getPossibleMoves(Tile[][] board, PieceColor color) {
        List<PieceMovement> possibleMoves = new ArrayList<>();
        boolean wasCapture = false;

        for (int row = 0; row < GameConstants.TILES_PER_ROW; row++) {
            for (int column = 0; column < GameConstants.TILES_PER_COLUMN; column++) {
                Tile currentTile = board[row][column];
                Piece currentPiece = currentTile.getPiece();

                if (currentPiece != null && currentPiece.getColor() == color) {
                    if (currentPiece.isKing()) {
                        for (int dirX = -1; dirX <= 1; dirX += 2) {
                            for (int dirY = -1; dirY <= 1; dirY += 2) {
                                boolean check = checkKingCaptureMoves(board, possibleMoves, color, row, column, dirX, dirY);
                                if (check)
                                    wasCapture = true;
                            }
                        }
                    }
                    else {
                        for (int dirX = -1; dirX <= 1; dirX += 2) {
                            for (int dirY = -1; dirY <= 1; dirY += 2) {
                                boolean check = checkPawnCaptureMoves(board, possibleMoves, color, row, column, dirX, dirY);
                                if (check)
                                    wasCapture = true;
                            }
                        }
                    }
                }
            }
        }

        if (!wasCapture) {
            for (int row = 0; row < GameConstants.TILES_PER_ROW; row++) {
                for (int column = 0; column < GameConstants.TILES_PER_COLUMN; column++) {
                    Tile currentTile = board[row][column];
                    Piece currentPiece = currentTile.getPiece();

                    if (currentPiece != null && currentPiece.getColor() == color) {
                        if (currentPiece.isKing()) {
                            for (int dirX = -1; dirX <= 1; dirX += 2) {
                                for (int dirY = -1; dirY <= 1; dirY += 2) {
                                    int targetX = column + dirX;
                                    int targetY = row + dirY;

                                    while (isValidPosition(targetX, targetY)) {
                                        Tile targetTile = board[targetY][targetX];

                                        if (targetTile.getPiece() == null) {
                                            possibleMoves.add(new PieceMovement(row, column, targetY, targetX));
                                        } else {
                                            break;
                                        }

                                        targetX += dirX;
                                        targetY += dirY;
                                    }
                                }
                            }
                        } else {
                            int moveDir = color.moveDir;
                            int targetX = column + moveDir;

                            if (isValidPosition(targetX, row + 1)) {
                                Tile targetTile = board[row + 1][targetX];
                                if (targetTile.getPiece() == null) {
                                    possibleMoves.add(new PieceMovement(row, column, row + 1, targetX));
                                }
                            }
                            if (isValidPosition(targetX, row - 1)) {
                                Tile targetTile = board[row - 1][targetX];
                                if (targetTile.getPiece() == null) {
                                    possibleMoves.add(new PieceMovement(row, column, row - 1, targetX));
                                }
                            }
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }

    private static boolean checkKingCaptureMoves(Tile[][] board, List<PieceMovement> possibleMoves, PieceColor color, int row, int column, int dirX, int dirY) {
        int targetX = column + dirX;
        int targetY = row + dirY;
        boolean hasCapture = false;

        while (isValidPosition(targetX, targetY)) {
            Tile targetTile = board[targetY][targetX];

            if (targetTile.getPiece() != null) {
                if (targetTile.getPiece().getColor() != color) {
                    int nextTargetX = targetX + dirX;
                    int nextTargetY = targetY + dirY;

                    while (isValidPosition(nextTargetX, nextTargetY)) {
                        Tile nextTargetTile = board[nextTargetY][nextTargetX];

                        if (nextTargetTile.getPiece() == null) {
                            possibleMoves.add(new PieceMovement(row, column, nextTargetY, nextTargetX));
                            hasCapture = true;
                        } else {
                            break;
                        }

                        nextTargetX += dirX;
                        nextTargetY += dirY;
                    }
                }
                break;
            }
            else {
                targetX += dirX;
                targetY += dirY;
            }
        }

        return hasCapture;
    }

    private static boolean checkPawnCaptureMoves(Tile[][] board, List<PieceMovement> possibleMoves, PieceColor color, int row, int column, int moveDir, int dirY) {
        boolean wasCapture = false;
        if (isValidPosition(row + (2 * moveDir), column + (2 * dirY))) {
            Tile middleTile = board[row + moveDir][column + dirY];
            Tile targetTile = board[row + (2 * moveDir)][column + (2 * dirY)];
            if (middleTile.getPiece() != null && middleTile.getPiece().getColor() != color && targetTile.getPiece() == null) {
                possibleMoves.add(new PieceMovement(row, column, row + (2 * moveDir), column + (2 * dirY)));
                wasCapture = true;
            }
        }
        return wasCapture;
    }

    private static boolean isValidPosition(int x, int y) {
        return x >= 0 && x < GameConstants.TILES_PER_COLUMN && y >= 0 && y < GameConstants.TILES_PER_ROW;
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