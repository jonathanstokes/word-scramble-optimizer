package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.AnalyzedBoard;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.BoardAnalysis;
import com.teratory.art.wordscramble.rate.RatedBoard;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class BoardRatingComparatorTest {

    @Test
    public void itShouldSortRatedBoardsByRating() {
        Board dummyBoard = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'A' }
        });
        BoardAnalysis dummyAnalysis = new BoardAnalysis();

        Set<RatedBoard> boards = new TreeSet<>(new BoardRatingComparator());
        AnalyzedBoard dummyAnalizedBoard = new AnalyzedBoard(dummyBoard, dummyAnalysis);
        boards.add(new RatedBoard(dummyAnalizedBoard, 1.0));
        boards.add(new RatedBoard(dummyAnalizedBoard, 3.0));
        boards.add(new RatedBoard(dummyAnalizedBoard, 2.0));
        boards.add(new RatedBoard(dummyAnalizedBoard, 2.5));
        Iterator<RatedBoard> i = boards.iterator();
        assertEquals(3.0, i.next().getRating(), 0);
        assertEquals(2.5, i.next().getRating(), 0);
        assertEquals(2.0, i.next().getRating(), 0);
        assertEquals(1.0, i.next().getRating(), 0);
    }

    @Test
    public void itShouldNotTreatBoardsWithTheSameRatingAsEqual() {
        Board dummyBoard1 = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'A' }
        });
        Board dummyBoard2 = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'B' }
        });
        Board dummyBoard3 = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'C' }
        });
        BoardAnalysis dummyAnalysis = new BoardAnalysis();

        Set<RatedBoard> boards = new TreeSet<>(new BoardRatingComparator());
        boards.add(new RatedBoard(new AnalyzedBoard(dummyBoard1, dummyAnalysis), 1.0));
        boards.add(new RatedBoard(new AnalyzedBoard(dummyBoard2, dummyAnalysis), 2.0));
        boards.add(new RatedBoard(new AnalyzedBoard(dummyBoard3, dummyAnalysis), 2.0));

        assertEquals(3, boards.size());
    }
}