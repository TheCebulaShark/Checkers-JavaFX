package com.example.checkerajavafx;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    public List<Move> findPossibleMoves(Board board, PieceColor currentPlayerColor) {
        List<Move> possibleMoves = new ArrayList<>();

        int maxCaptureCount = 0;
        List<Piece> piecesWithMaxCaptures = new ArrayList<>();

        for (Piece piece : board.getPieces(currentPlayerColor)) {
            int captureCount = getCaptureCount(board, piece, currentPlayerColor, 0);
            if (captureCount > maxCaptureCount) {
                maxCaptureCount = captureCount;
                piecesWithMaxCaptures.clear();
                piecesWithMaxCaptures.add(piece);
            } else if (captureCount == maxCaptureCount) {
                piecesWithMaxCaptures.add(piece);
            }
        }

        for (Piece piece : piecesWithMaxCaptures) {
            List<Move> pieceMoves = getPossibleMovesForPiece(board, piece, currentPlayerColor, true);
            possibleMoves.addAll(pieceMoves);
        }

        return possibleMoves;
    }

    private int getCaptureCount(Board board, Piece piece, PieceColor currentPlayerColor, int currentCaptureCount) {
        int maxCaptureCount = currentCaptureCount;

        List<Move> possibleMoves = getPossibleMovesForPiece(board, piece, currentPlayerColor, false);
        for (Move move : possibleMoves) {
            Board tempBoard = board.copy();
            Tile startTile = tempBoard.getTile(piece.getX(), piece.getY());
            Tile endTile = tempBoard.getTile(move.getEndX(), move.getEndY());
            tempBoard.movePiece(startTile, endTile);
            int captureCount = getCaptureCount(tempBoard, endTile.getPiece(), currentPlayerColor, currentCaptureCount + 1);
            if (captureCount > maxCaptureCount) {
                maxCaptureCount = captureCount;
            }
        }

        return maxCaptureCount;
    }

    private List<Move> getPossibleMovesForPiece(Board board, Piece piece, PieceColor currentPlayerColor, boolean includeCaptures) {
        List<Move> possibleMoves = new ArrayList<>();
        int startX = piece.getX();
        int startY = piece.getY();

        int[] directions = { -1, 1 };
        for (int dirX : directions) {
            int endX = startX + dirX;
            int endY = startY + piece.getColor().moveDir;

            if (board.isWithinBounds(endX, endY) && board.getPiece(endX, endY) == null) {
                possibleMoves.add(new Move(startX, startY, endX, endY, false, false));
            }
        }

        if (includeCaptures) {
            for (int dirX : directions) {
                int endX = startX + 2 * dirX;
                int endY = startY + 2 * piece.getColor().moveDir;

                if (board.isWithinBounds(endX, endY)) {
                    Piece capturedPiece = board.getPiece(startX + dirX, startY + piece.getColor().moveDir);
                    if (capturedPiece != null && capturedPiece.getColor() != currentPlayerColor) {
                        if (board.getPiece(endX, endY) == null) {
                            possibleMoves.add(new Move(startX, startY, endX, endY, true, false));
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }


}
