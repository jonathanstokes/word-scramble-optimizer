package com.teratory.art.wordscramble.model;

import java.util.Arrays;

public class Board {

    private byte width;
    private byte height;
    private byte[] gridData;

    public Board(char[][] gridData) {
        this.width = (byte)gridData[0].length;
        this.height = (byte)gridData.length;
        this.gridData = new byte[this.width * this.height];
        for (int y = 0, h = this.height; y < h; y++) {
            for (int x = 0, w = this.width; x < w; x++) {
                this.gridData[y * w + x] = String.valueOf(gridData[y][x]).toUpperCase().getBytes()[0];
            }
        }
    }

    public Board(byte width, byte height, byte[] gridData) {
        this.width = width;
        this.height = height;
        this.gridData = gridData;
        if (gridData.length != width * height) throw new IllegalArgumentException("Grid data with " + gridData.length + " elements will not fit " + width + "x" + height + ".");
    }

    public char getCharAt(int x, int y) {
        return (char)this.gridData[y * width + x];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(width * height * 2);
        for (int y = 0, h = height; y < h; y++) {
            for (int x = 0, w = width; x < w; x++) {
                sb.append(getCharAt(x, y));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
