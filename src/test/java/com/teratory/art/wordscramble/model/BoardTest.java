package com.teratory.art.wordscramble.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testMultiDimentionalConstructor() {
        Board board = new Board(new char[][] {
                { 'A', 'B', 'C' },
                { 'D', 'E', 'F' }
        });
        assertEquals(3, board.getWidth());
        assertEquals(2, board.getHeight());
        assertEquals('A', board.getCharAt(0, 0));
        assertEquals('B', board.getCharAt(1, 0));
        assertEquals('C', board.getCharAt(2, 0));
        assertEquals('D', board.getCharAt(0, 1));
        assertEquals('E', board.getCharAt(1, 1));
        assertEquals('F', board.getCharAt(2, 1));

        char[][] data = new char[][] {
            { 'A', 'B', 'C' },
            { 'D', 'E', 'F' },
            { 'G', 'H', 'I' }
        };
        board = new Board(data);
        assertEquals(3, board.getWidth());
        assertEquals(3, board.getHeight());
        assertEquals('A', board.getCharAt(0, 0));
        assertEquals('B', board.getCharAt(1, 0));
        assertEquals('C', board.getCharAt(2, 0));
        assertEquals('E', board.getCharAt(1, 1));
        assertEquals('H', board.getCharAt(1, 2));
        assertEquals('I', board.getCharAt(2, 2));
    }

    @Test
    public void testToString() {
        Board board = new Board(new char[][] {
                { 'A', 'B', 'C', 'D', 'E' },
                { 'F', 'G', 'H', 'I', 'J' },
                { 'K', 'L', 'M', 'N', 'O' },
                { 'P', 'Q', 'R', 'S', 'T' },
                { 'U', 'V', 'W', 'X', 'Y' },
        });
        assertEquals(
            "ABCDE\n" +
            "FGHIJ\n" +
            "KLMNO\n" +
            "PQRST\n" +
            "UVWXY\n",
            board.toString()
        );

    }
}