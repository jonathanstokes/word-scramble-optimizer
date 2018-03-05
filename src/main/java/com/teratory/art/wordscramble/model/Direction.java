package com.teratory.art.wordscramble.model;

public enum Direction {
    EAST(1,0),
    SOUTH(0,1),
    SOUTHEAST(1,1),
    NORTHEAST(1,-1),
    WEST(-1,0),
    NORTH(0,-1),
    SOUTHWEST(-1,1),
    NORTHWEST(-1,-1);

    private int xOffset;
    private int yOffset;

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }
}
