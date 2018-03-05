package com.teratory.art.wordscramble.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardAnalysisTest {

    @Test
    public void testStatistics_areOnTheSameLine() {
        BoardAnalysis analysis = new BoardAnalysis();
        BoardAnalysis.Statistics stats = analysis.getStatistics();

        FoundWord first = new FoundWord("something", 0, 0, Direction.EAST, false, 1);
        FoundWord second = new FoundWord("else", 0, 1, Direction.EAST, false, 1);
        assertEquals(false, stats.areOnTheSameLine(first, second));

        first = new FoundWord("something", 0, 0, Direction.EAST, false, 1);
        second = new FoundWord("else", 10, 0, Direction.EAST, false,1 );
        assertEquals(true, stats.areOnTheSameLine(first, second));

        first = new FoundWord("something", 3, 0, Direction.SOUTH, false, 1);
        second = new FoundWord("else", 10, 12, Direction.SOUTH, false, 1);
        assertEquals(false, stats.areOnTheSameLine(first, second));

        first = new FoundWord("something", 3, 0, Direction.SOUTH, false, 1);
        second = new FoundWord("else", 3, 12, Direction.SOUTH, false, 1);
        assertEquals(true, stats.areOnTheSameLine(first, second));

        //   .  .  .  .  .  .  .  .  S
        //   .  .  .  .  .  .  .  O  .
        //   .  .  .  .  .  .  M  .  .
        //   .  .  .  .  .  E  .  .  .
        //   .  .  .  .  T  .  .  .  .
        //   .  .  .  H  .  .  .  .  .
        //   .  .  I  .  .  .  .  .  . 
        //   .  N  .  .  .  .  .  .  .
        //   G  .  .  .  .  .  .  .  .
        first = new FoundWord("some", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("thing", 4, 4, Direction.SOUTHWEST, false, 1);
        assertEquals(true, stats.areOnTheSameLine(first, second));

        first = new FoundWord("some", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("thing", 5, 4, Direction.SOUTHWEST, false, 1);
        assertEquals(false, stats.areOnTheSameLine(first, second));

        first = new FoundWord("some", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("thing", 3, 4, Direction.SOUTHWEST, false, 1);
        assertEquals(false, stats.areOnTheSameLine(first, second));
    }

    @Test
    public void testStatistics_getRelativeTypeOfSameLineWords() {
        BoardAnalysis analysis = new BoardAnalysis();
        BoardAnalysis.Statistics stats = analysis.getStatistics();

        //   .  .  .  .  .  .  .  .  .
        //   .  .  .  .  .  .  .  .  .
        //   .  B  E  A  T  E  N  T  H
        //   .  .  .  .  .  .  .  .  .
        FoundWord first = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        FoundWord second = new FoundWord("TENTH", 4, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        second = new FoundWord("BEAT", 1, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEAT", 1, 2, Direction.EAST, false, 1);
        second = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        second = new FoundWord("EAT", 2, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("EAT", 2, 2, Direction.EAST, false, 1);
        second = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("EAT", 2, 2, Direction.EAST, false, 1);
        second = new FoundWord("ATE", 3, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("ATE", 3, 2, Direction.EAST, false, 1);
        second = new FoundWord("EAT", 2, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        second = new FoundWord("TEN", 4, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("TEN", 4, 2, Direction.EAST, false, 1);
        second = new FoundWord("BEATEN", 1, 2, Direction.EAST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        //   .  .  .  .  .  .  .  .  .
        //   .  B  .  .  .  .  .  .  .
        //   .  .  E  .  .  .  .  .  .
        //   .  .  .  A  .  .  .  .  .
        //   .  .  .  .  T  .  .  .  .
        //   .  .  .  .  .  E  .  .  .
        //   .  .  .  .  .  .  N  .  .
        //   .  .  .  .  .  .  .  T  .
        //   .  .  .  .  .  .  .  .  H
        first = new FoundWord("BEATEN", 1, 1, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("TENTH", 4, 4, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEATEN", 1, 1, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("EAT", 2, 2, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("EAT", 2, 2, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("BEATEN", 1, 1, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("EAT", 2, 2, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("ATE", 3, 3, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("ATE", 3, 3, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("EAT", 2, 2, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("BEATEN", 1, 1, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("TEN", 4, 4, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("TEN", 4, 4, Direction.SOUTHEAST, false, 1);
        second = new FoundWord("BEATEN", 1, 1, Direction.SOUTHEAST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        //   .  .  .  .  .  .  .  .  S
        //   .  .  .  .  .  .  .  O  .
        //   .  .  .  .  .  .  M  .  .
        //   .  .  .  .  .  E  .  .  .
        //   .  .  .  .  T  .  .  .  .
        //   .  .  .  H  .  .  .  .  .
        //   .  .  I  .  .  .  .  .  .
        //   .  N  .  .  .  .  .  .  .
        //   G  .  .  .  .  .  .  .  .
        first = new FoundWord("SOME", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("THING", 4, 4, Direction.SOUTHWEST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("SOME", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("SOMETHING", 8, 0, Direction.SOUTHWEST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("SOMETHING", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("SOME", 8, 0, Direction.SOUTHWEST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("THING", 4, 4, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("SOMETHING", 8, 0, Direction.SOUTHWEST, false, 1);
        assertEquals(FoundWord.FoundWordType.EMBEDDED, stats.getRelativeTypeOfSameLineWords(first, second));

        first = new FoundWord("SOMETHING", 8, 0, Direction.SOUTHWEST, false, 1);
        second = new FoundWord("THING", 4, 4, Direction.SOUTHWEST, false, 1);
        assertEquals(FoundWord.FoundWordType.STANDALONE, stats.getRelativeTypeOfSameLineWords(first, second));
    }
}