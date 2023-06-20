package com.example.checkerajavafx;

public enum PieceColor {
    DARK(1), LIGHT(-1);

    final int moveDir;

    PieceColor(int moveDir) {
        this.moveDir = moveDir;
    }
}
