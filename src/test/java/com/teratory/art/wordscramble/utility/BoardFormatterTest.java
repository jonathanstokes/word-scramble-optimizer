package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardFormatterTest {

    @Test
    public void format() {
        Board board = new Board(new char[][] {
                { 'T', 'H', 'E' },
                { 'B', 'O', 'A' },
                { 'T', 'P', 'B' },
        });
        BoardFormatter formatter = new BoardFormatter();
        assertEquals(
                "┌───────┐\n" +
                        "│ T H E │\n" +
                        "│ B O A │\n" +
                        "│ T P B │\n" +
                        "└───────┘\n"
                , formatter.format(board));
    }
}