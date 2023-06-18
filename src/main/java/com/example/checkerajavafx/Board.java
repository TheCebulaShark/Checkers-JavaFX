package com.example.checkerajavafx;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.example.checkerajavafx.GameConstants.*;

public class Board {

    private GridPane chessboard;
    private Group piecesGroup = new Group();

    private Tile[][] boardTable = new Tile[TILES_PER_ROW][TILES_PER_COLUMN];
    private Tile selectedTile;

    public Board() {
        chessboard = new GridPane();
        for (int column_iterator = 0; column_iterator < TILES_PER_COLUMN; column_iterator++) {
            for (int row_iterator = 0; row_iterator < TILES_PER_ROW; row_iterator++) {
                Tile tile = new Tile(((column_iterator + row_iterator) % 2 == 0), row_iterator, column_iterator);
                boardTable[row_iterator][column_iterator] = tile;

                tile.setWidth(TILE_SIZE);
                tile.setHeight(TILE_SIZE);

                tile.relocate(column_iterator * TILE_SIZE, row_iterator * TILE_SIZE);

                chessboard.add(tile, column_iterator, row_iterator, 1, 1);

                Piece piece = null;

                if ((column_iterator >= TILES_PER_COLUMN - ROWS_OF_PIECES) && ((column_iterator + row_iterator) % 2 != 0))
                    piece = new Piece(PieceColor.LIGHT, row_iterator, column_iterator);
                if ((column_iterator < ROWS_OF_PIECES) && ((column_iterator + row_iterator) % 2 != 0))
                    piece = new Piece(PieceColor.DARK, row_iterator, column_iterator);

                if (piece != null) {
                    tile.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }

                tile.setOnMouseClicked(event -> handleTileClick(tile));
            }
        }
        chessboard.setTranslateY(MENU_HEIGHT);
        piecesGroup.setTranslateY(MENU_HEIGHT);
    }

    public Board copy() {
        Board copiedBoard = new Board();

        for (int column = 0; column < TILES_PER_COLUMN; column++) {
            for (int row = 0; row < TILES_PER_ROW; row++) {
                Tile originalTile = boardTable[row][column];
                Tile copiedTile = copiedBoard.getTile(row, column);

                if (originalTile.hasPiece()) {
                    Piece originalPiece = originalTile.getPiece();
                    Piece copiedPiece = new Piece(originalPiece.getColor(), originalPiece.getX(), originalPiece.getY());
                    copiedTile.setPiece(copiedPiece);
                    copiedBoard.getPiecesGroup().getChildren().add(copiedPiece);
                }
            }
        }

        return copiedBoard;
    }

    public Tile getTile(int x, int y) {
        if (isValidTile(x, y)) {
            return boardTable[x][y];
        } else {
            return null;
        }
    }

    private boolean isValidTile(int x, int y) {
        return x >= 0 && x < TILES_PER_ROW && y >= 0 && y < TILES_PER_COLUMN;
    }

    private void resetColors() {
        for (int column = 0; column < TILES_PER_COLUMN; column++) {
            for (int row = 0; row < TILES_PER_ROW; row++) {
                Tile tile = boardTable[column][row];
                tile.setDefaultColor();
            }
        }
    }

    private void handleTurn(PieceColor currentPlayerColor) {

    }

    private void handleTileClick(Tile clickedTile) {
        if (selectedTile != null) {
            resetTileColors();
            clickedTile.setColor(Color.valueOf(GameConstants.POSSIBLE_PIECES_TO_MOVES_FIELD_COLOR_HEX));
            movePiece(selectedTile, clickedTile);
            selectedTile = null;
        } else {
            if (clickedTile.hasPiece()) {
                selectedTile = clickedTile;
                selectedTile.setColor(Color.valueOf(GameConstants.POSSIBLE_MOVES_FIELD_COLOR_HEX));
                highlightPossibleMoves(selectedTile);
            }
        }
    }

    public void movePiece(Tile sourceTile, Tile targetTile) {

    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < TILES_PER_ROW && y >= 0 && y < TILES_PER_COLUMN;
    }

    private void resetTileColors() {
        for (int row = 0; row < TILES_PER_ROW; row++) {
            for (int column = 0; column < TILES_PER_COLUMN; column++) {
                boardTable[row][column].setDefaultColor();
            }
        }
    }

    private void highlightPossibleMoves(Tile sourceTile) {
        PieceColor pieceColor = sourceTile.getPiece().getColor();
        List<PieceMovement> possibleMoves = PieceMovement.getPossibleMoves(boardTable, pieceColor);

        for (PieceMovement move : possibleMoves) {
            int targetX = move.getEndX();
            int targetY = move.getEndY();
            boardTable[targetX][targetY].setColor(Color.RED);
        }
    }


    public GridPane getChessboard() {
        return chessboard;
    }

    public Group getPiecesGroup() {
        return piecesGroup;
    }

    public Piece getPiece(int x, int y) {
        return boardTable[y][x].getPiece();
    }


    public List<Piece> getPieces(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();

        for (int column = 0; column < TILES_PER_COLUMN; column++) {
            for (int row = 0; row < TILES_PER_ROW; row++) {
                Tile tile = boardTable[row][column];

                if (tile.hasPiece() && tile.getPiece().getColor() == color) {
                    pieces.add(tile.getPiece());
                }
            }
        }

        return pieces;
    }
}
