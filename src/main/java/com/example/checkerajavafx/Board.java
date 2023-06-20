package com.example.checkerajavafx;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;

import static com.example.checkerajavafx.GameConstants.*;

public class Board {

    private GridPane chessboard;
    private Group piecesGroup = new Group();

    private Tile[][] boardTable = new Tile[TILES_PER_ROW][TILES_PER_COLUMN];

    public Board() {
        chessboard = new GridPane();
        for (int row_iterator = 0; row_iterator < TILES_PER_ROW; row_iterator++) {
            for (int column_iterator = 0; column_iterator < TILES_PER_COLUMN; column_iterator++) {
                Tile tile = new Tile(((column_iterator + row_iterator) % 2 == 0), row_iterator, column_iterator);
                boardTable[row_iterator][column_iterator] = tile;

                tile.setWidth(TILE_SIZE);
                tile.setHeight(TILE_SIZE);

                tile.relocate(row_iterator * TILE_SIZE, column_iterator * TILE_SIZE);

                chessboard.add(tile, row_iterator, column_iterator, 1, 1);

                Piece piece = null;

                if ((column_iterator >= TILES_PER_COLUMN - ROWS_OF_PIECES) && ((column_iterator + row_iterator) % 2 != 0))
                    piece = new Piece(PieceColor.LIGHT, row_iterator, column_iterator);
                if ((column_iterator < ROWS_OF_PIECES) && ((column_iterator + row_iterator) % 2 != 0))
                    piece = new Piece(PieceColor.DARK, row_iterator, column_iterator);

                if (piece != null) {
                    tile.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
            }
        }
        chessboard.setTranslateY(MENU_HEIGHT);
        piecesGroup.setTranslateY(MENU_HEIGHT);
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

    public void resetColors() {
        for (int column = 0; column < TILES_PER_COLUMN; column++) {
            for (int row = 0; row < TILES_PER_ROW; row++) {
                Tile tile = boardTable[column][row];
                tile.setDefaultColor();
            }
        }
    }

    public void movePiece(Piece piece, Tile targetTile) {
        Tile startTile = getTile(piece.getX(), piece.getY());
        startTile.setPiece(null);
        targetTile.setPiece(piece);
        piece.move(targetTile.getCordX(), targetTile.getCordY());
        piece.relocate(targetTile.getCordX() * TILE_SIZE, targetTile.getCordY() * TILE_SIZE);
        if (piece.getColor().moveDir > 0 && targetTile.getCordY() == TILES_PER_COLUMN - 1) {
            piece.setKing();
        }
        else if (targetTile.getCordY() == 0) {
            piece.setKing();
        }
    }

    public boolean capturePiece(Piece piece, Tile targetTile) {
        boolean wasCaptured = false;
        if (piece.isKing()) {
            int currentX = piece.getX();
            int currentY = piece.getY();
            int targetX = targetTile.getCordX();

            int dirX = (targetX - currentX > 0 ? 1 : -1);
            int dirY = (targetTile.getCordY() - currentY > 0 ? 1 : -1);

            currentX += dirX;
            currentY += dirY;

            while (currentX != targetX) {
                Tile capturedTile = getTile(currentX, currentY);

                Piece capturedPiece = capturedTile.getPiece();

                if (capturedPiece != null) {
                    wasCaptured = true;
                    removeCapturedPiece(capturedPiece);
                    break;
                }

                currentX += dirX;
                currentY += dirY;
            }
        }
        else {
            int dirX = targetTile.getCordX() - piece.getX();
            if (Math.abs(dirX) == 2) {
                int dirY = targetTile.getCordY() - piece.getY();
                int capturedX = piece.getX() + (dirX / 2);
                int capturedY = piece.getY() + (dirY / 2);
                Tile capturedTile = getTile(capturedX, capturedY);
                Piece capturedPiece = capturedTile.getPiece();

                if (capturedPiece != null) {
                    wasCaptured = true;
                    removeCapturedPiece(capturedPiece);
                }
            }
        }

        return wasCaptured;
    }

    public void removeCapturedPiece(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();
        Tile tile = getTile(x, y);
        tile.setEmpty();
        piecesGroup.getChildren().remove(piece);
    }

    public GridPane getChessboard() {
        return chessboard;
    }

    public Group getPiecesGroup() {
        return piecesGroup;
    }

    public Tile[][] getTiles() {
        return boardTable;
    }
}