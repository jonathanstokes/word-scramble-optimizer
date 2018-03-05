package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.model.AnalyzedBoard;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.BoardAnalysis;
import com.teratory.art.wordscramble.utility.BoardRatingComparator;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class TopRatedBoardsTest {

    @Test
    public void itShouldPruneAsExpected() {
        Board dummyBoard = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'A' }
        });
        BoardAnalysis dummyAnalysis = new BoardAnalysis();
        TopRatedBoards boards = new TopRatedBoards(3);
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 1.0));
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 3.0));
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 2.0));
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 2.5));

        Collection<RatedBoard> result = boards.getBoards();
        assertEquals(3, result.size());
        Iterator<RatedBoard> i = result.iterator();
        assertEquals(3.0, i.next().getRating(), 0);
        assertEquals(2.5, i.next().getRating(), 0);
        assertEquals(2.0, i.next().getRating(), 0);
    }

    @Test
    public void itShouldAllowExtraItemsBecauseOfARatingsTie() {
        Board dummyBoard = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'A' }
        });
        BoardAnalysis dummyAnalysis = new BoardAnalysis();
        TopRatedBoards boards = new TopRatedBoards(3);
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 1.0));
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 3.0));
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 2.0));
        assertEquals(3, boards.getBoards().size());
        // Pushing on a 2.5 should push out the 1.0.
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 2.5));
        assertEquals(3, boards.getBoards().size());
        // But pushing a 2.0 will extend the collection to 4 because it ties for last.
        boards.accumulate(new RatedBoard(new AnalyzedBoard(dummyBoard, new BoardAnalysis()), 2.0));
        assertEquals(4, boards.getBoards().size());
        Iterator<RatedBoard> i = boards.getBoards().iterator();
        assertEquals(3.0, i.next().getRating(), 0);
        assertEquals(2.5, i.next().getRating(), 0);
        assertEquals(2.0, i.next().getRating(), 0);
        assertEquals(2.0, i.next().getRating(), 0);
    }
}