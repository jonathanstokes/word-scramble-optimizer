package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.*;
import com.teratory.art.wordscramble.rate.RatedBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class RatedBoardFormatterTest {

    @Test
    public void format() {
        Board board = new Board(new char[][] {
                { 'T', 'H', 'E' },
                { 'B', 'O', 'A' },
                { 'T', 'P', 'B' },
        });

        BoardAnalysis analysis = new BoardAnalysis();
        analysis.add(new FoundWord("THE", 0, 0, Direction.EAST, false, 1));
        analysis.add(new FoundWord("BOA", 2, 0, Direction.EAST, false, 1));
        analysis.add(new FoundWord("HOP", 1, 0, Direction.SOUTH, false, 1));
        analysis.add(new FoundWord("TOE", 0, 2, Direction.NORTHEAST, false, 1));
        analysis.add(new FoundWord("BAE", 2, 2, Direction.NORTH, false, 1));
        RatedBoard ratedBoard = new RatedBoard(new AnalyzedBoard(board, analysis), 3.2301111111111);
        RatedBoardFormatter formatter = new RatedBoardFormatter();
        assertEquals(
                "┌───────┐   Rating: 3.2301\n" +
                "│ T H E │   THE (at 0,0 going EAST)      TOE (at 0,2 going NORTHEAST)\n" +
                "│ B O A │   BOA (at 2,0 going EAST)      BAE (at 2,2 going NORTH)\n" +
                "│ T P B │   HOP (at 1,0 going SOUTH)     \n" +
                "└───────┘   \n"
        , formatter.format(ratedBoard));
    }

    @Test
    public void itShouldNotDuplicateFoundWords() {
        Board board = new Board(new char[][] {
                { 'A', 'A', 'S' },
                { 'A', 'B', 'O' },
        });

        BoardAnalysis analysis = new BoardAnalysis();
        analysis.add(new FoundWord("AAS", 0, 0, Direction.EAST, false, 1));
        analysis.add(new FoundWord("SAA", 2, 0, Direction.WEST, false, 1));
        analysis.add(new FoundWord("ABO", 0, 1, Direction.EAST, false, 1));
        analysis.add(new FoundWord("OBA", 2, 1, Direction.WEST, false, 1));
        RatedBoard ratedBoard = new RatedBoard(new AnalyzedBoard(board, analysis), 0.76);
        RatedBoardFormatter formatter = new RatedBoardFormatter();
        assertEquals(
                "┌───────┐   Rating: 0.76\n" +
                        "│ A A S │   AAS (at 0,0 going EAST) ABO (at 0,1 going EAST)\n" +
                        "│ A B O │   SAA (at 2,0 going WEST) OBA (at 2,1 going WEST)\n" +
                        "└───────┘   \n"
                , formatter.format(ratedBoard)
        );
    }

    @Test
    public void itShouldNotBlowUpWithNoFoundWords() {
        Board board = new Board(new char[][] {
                { 'T', 'H', 'E' },
                { 'B', 'O', 'A' },
                { 'T', 'P', 'B' },
        });

        BoardAnalysis analysis = new BoardAnalysis();
        RatedBoard ratedBoard = new RatedBoard(new AnalyzedBoard(board, analysis), 0.000055);
        RatedBoardFormatter formatter = new RatedBoardFormatter();
        assertEquals(
                "┌───────┐   Rating: 0.0001\n" +
                        "│ T H E │\n" +
                        "│ B O A │\n" +
                        "│ T P B │\n" +
                        "└───────┘\n"
                , formatter.format(ratedBoard)
        );
    }
}