package com.teratory.art.wordscramble.functional;

import com.teratory.art.wordscramble.analyze.BoardAnalyzer;
import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.dictionary.FileWordList;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.rate.BoardRater;
import com.teratory.art.wordscramble.rate.RatedBoard;
import com.teratory.art.wordscramble.utility.RatedBoardFormatter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AccuracyFunctionalTest {

    @Test
    public void itShouldRateBoardsAccurately() throws IOException {
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(new FileWordList(new File("./src/main/resources/dwyl_alpha_word_list.txt")), 1);
        BoardAnalyzer analyzer = new BoardAnalyzer(dictionary, 3);
        BoardRater rater = new BoardRater();

        Board leftBoard = new Board(new char[][] {
                { 'A', 'A', 'B' },
                { 'S', 'A', 'A' }
        });
        RatedBoard leftRatedBoard = rater.rateBoard(analyzer.analyze(leftBoard));
        Board rightBoard = new Board(new char[][] {
                { 'B', 'A', 'A' },
                { 'A', 'A', 'S' }
        });
        RatedBoard rightRatedBoard = rater.rateBoard(analyzer.analyze(rightBoard));
        System.out.println(new RatedBoardFormatter().format(rightRatedBoard));
        System.out.println(new RatedBoardFormatter().format(leftRatedBoard));
        assertTrue(rightRatedBoard.getRating() > leftRatedBoard.getRating());
    }
}