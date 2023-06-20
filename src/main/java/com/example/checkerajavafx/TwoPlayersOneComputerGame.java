package com.example.checkerajavafx;

import javafx.scene.paint.Color;
import java.util.List;

public class TwoPlayersOneComputerGame {
    private Board board;
    private Menu menu;
    private PieceColor currentPlayerColor;

    private List<PieceMovement> possibleMoves;

    private Piece selectedPiece;

    private Tile selectedTile;
    private boolean isGameOver;
    private boolean wasCaptured;
    private boolean isPossibleMultiCapture;

    public TwoPlayersOneComputerGame(Board board, Menu menu) {
        this.board = board;
        this.menu = menu;
        menu.updateText("Tura Gracza 1");
        currentPlayerColor = PieceColor.LIGHT;
        this.isGameOver = false;
        this.isPossibleMultiCapture = false;
    }

    public void start() {
        startTurn();
    }

    private void switchPlayer() {
        if (currentPlayerColor == PieceColor.LIGHT) {
            currentPlayerColor = PieceColor.DARK;
            menu.updateText("Tura Gracza 2");
        }
        else {
            currentPlayerColor = PieceColor.LIGHT;
            menu.updateText("Tura Gracza 1");
        }

        startTurn();
    }

    private void checkGameOver() {
    }

    private void startTurn() {
        printBoard();
        possibleMoves = PieceMovement.getPossibleMoves(board.getTiles(), currentPlayerColor);
        displayPossiblePiecesToMove();
    }

    public void printBoard() {
        System.out.println("Current board state:");
        Tile[][] boardTiles = board.getTiles();

        for (int y = 0; y < GameConstants.TILES_PER_ROW; y++) {
            for (int x = 0; x < GameConstants.TILES_PER_COLUMN; x++) {
                Tile tile = boardTiles[y][x];
                if (tile.hasPiece()) {
                    PieceColor pieceColor = tile.getPiece().getColor();
                    System.out.print(pieceColor == PieceColor.LIGHT ? "L " : "D ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayPossiblePiecesToMove() {
        board.resetColors();

        for (PieceMovement move : possibleMoves) {
            int startX = move.getStartX();
            int startY = move.getStartY();

            Tile startTile = board.getTile(startX, startY);
            startTile.setColor(Color.valueOf(GameConstants.POSSIBLE_PIECES_TO_MOVES_FIELD_COLOR_HEX));
        }
    }

    public void displayPossibleMoves() {
        int selectedX = selectedPiece.getX();
        int selectedY = selectedPiece.getY();

        board.resetColors();
        board.getTile(selectedX, selectedY).setColor(Color.valueOf(GameConstants.POSSIBLE_PIECES_TO_MOVES_FIELD_COLOR_HEX));
        for (PieceMovement move : possibleMoves) {
            int startX = move.getStartX();
            int startY = move.getStartY();

            if (selectedX == startX && selectedY == startY)
            {
                int endX = move.getEndX();
                int endY = move.getEndY();

                board.getTile(endX, endY).setColor(Color.valueOf(GameConstants.POSSIBLE_MOVES_FIELD_COLOR_HEX));
            }
        }

    }

    public void handleTileClick(Tile tile) {
        if (selectedPiece != null) {
            int clickedX = tile.getCordX();
            int clickedY = tile.getCordY();
            int pieceX = selectedPiece.getX();
            int pieceY = selectedPiece.getY();

            for (PieceMovement move : possibleMoves) {
                if (move.getStartX() == pieceX && move.getStartY() == pieceY) {
                    int endX = move.getEndX();
                    int endY = move.getEndY();

                    if (clickedX == endX && clickedY == endY) {
                        selectedTile = tile;
                        wasCaptured = board.capturePiece(selectedPiece, selectedTile);
                        board.movePiece(selectedPiece, selectedTile);
                        if (wasCaptured) {
                            printBoard();
                            List<PieceMovement> nextCaptureMoves = PieceMovement.getPossibleCapturesForPiece(board.getTiles(), currentPlayerColor, selectedPiece);
                            if (nextCaptureMoves.isEmpty())
                            {
                                selectedPiece = null;
                                selectedTile = null;
                                switchPlayer();
                            }
                            else {
                                possibleMoves = nextCaptureMoves;
                                displayPossibleMoves();
                            }
                            return;
                        }
                        else {
                            selectedPiece = null;
                            selectedTile = null;
                            switchPlayer();
                        }
                        return;
                    }
                }
            }
        }
    }

    public void handlePieceClick(Piece piece) {
        int clickedX = piece.getX();
        int clickedY = piece.getY();

        if (selectedPiece == null) {
            for (PieceMovement move : possibleMoves) {
                int startX = move.getStartX();
                int startY = move.getStartY();

                if (startX == clickedX && startY == clickedY) {
                    selectedPiece = piece;
                    displayPossibleMoves();
                }
            }
        }
        else {
            if (clickedX == selectedPiece.getX() && clickedY == selectedPiece.getY()) {
                selectedPiece = null;
                displayPossiblePiecesToMove();
            }
        }
    }
}