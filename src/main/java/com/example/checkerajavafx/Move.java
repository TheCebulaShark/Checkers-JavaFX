package com.example.checkerajavafx;

public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean capture;
    private boolean multipleCaptures;

    public Move(int startX, int startY, int endX, int endY, boolean capture, boolean multipleCaptures) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.capture = capture;
        this.multipleCaptures = multipleCaptures;
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

    public boolean isCapture() {
        return capture;
    }

    public boolean isMultipleCaptures() {
        return multipleCaptures;
    }
}

