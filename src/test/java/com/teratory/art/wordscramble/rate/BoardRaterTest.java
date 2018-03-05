package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.analyze.BoardAnalyzer;
import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.dictionary.MockWordList;
import com.teratory.art.wordscramble.model.Board;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class BoardRaterTest {

    @Test
    public void testDeriveRating() {
        List<String> testWords = Arrays.asList(
                "another",
                "other",
                "some",
                "thing",
                "something"
        );
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(new MockWordList(testWords.iterator()), 1);


        //   .  .  .  .  .  .  .  .  S
        //   .  A  .  .  .  .  .  O  .
        //   .  .  N  .  .  .  M  .  .
        //   .  .  .  O  .  E  .  .  .
        //   .  .  .  .  T  .  .  .  .
        //   .  .  .  H  .  H  .  .  .
        //   .  .  I  .  .  .  E  .  .
        //   .  N  .  .  .  .  .  R  .
        //   G  .  .  .  .  .  .  .  .
        Board lesserBoard = new Board(new char[][] {
            { '.', '.', '.', '.', '.', '.', '.', '.', 'S' },
            { '.', 'A', '.', '.', '.', '.', '.', 'O', '.' },
            { '.', '.', 'N', '.', '.', '.', 'M', '.', '.' },
            { '.', '.', '.', 'O', '.', 'E', '.', '.', '.' },
            { '.', '.', '.', '.', 'T', '.', '.', '.', '.' },
            { '.', '.', '.', 'H', '.', 'H', '.', '.', '.' },
            { '.', '.', 'I', '.', '.', '.', 'E', '.', '.' },
            { '.', 'N', '.', '.', '.', '.', '.', 'R', '.' },
            { 'G', '.', '.', '.', '.', '.', '.', '.', '.' }
        });
        BoardRater rater = new BoardRater();
        BoardAnalyzer analyzer = new BoardAnalyzer(dictionary, 4);
        double lesserRating = rater.deriveRating(analyzer.analyze(lesserBoard).getAnalysis());
        System.out.println("Lesser rating is " + lesserRating + ".");

        //   S  .  .  .  .  .  .  .  .
        //   .  O  .  .  .  .  .  A  .
        //   .  .  M  .  .  .  N  .  .
        //   .  .  .  E  .  O  .  .  .
        //   .  .  .  .  T  .  .  .  .
        //   .  .  .  H  .  H  .  .  .
        //   .  .  E  .  .  .  I  .  .
        //   .  R  .  .  .  .  .  N  .
        //   .  .  .  .  .  .  .  .  G
        Board greaterBoard = new Board(new char[][] {
            { 'S', '.', '.', '.', '.', '.', '.', '.', '.' },
            { '.', 'O', '.', '.', '.', '.', '.', 'A', '.' },
            { '.', '.', 'M', '.', '.', '.', 'N', '.', '.' },
            { '.', '.', '.', 'E', '.', 'O', '.', '.', '.' },
            { '.', '.', '.', '.', 'T', '.', '.', '.', '.' },
            { '.', '.', '.', 'H', '.', 'H', '.', '.', '.' },
            { '.', '.', 'E', '.', '.', '.', 'I', '.', '.' },
            { '.', 'R', '.', '.', '.', '.', '.', 'N', '.' },
            { '.', '.', '.', '.', '.', '.', '.', '.', 'G' }
        });
        double greaterRating = rater.deriveRating(analyzer.analyze(greaterBoard).getAnalysis());
        System.out.println("Greater rating is " + greaterRating + ".");
        assertTrue(greaterRating > lesserRating);
    }

    @Test
    public void itShouldRateDuplicatesLess() {
        List<String> testWords = Arrays.asList(
                "another",
                "other",
                "some",
                "thing",
                "something"
        );
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(new MockWordList(testWords.iterator()), 1);


        Board greaterBoard = new Board(new char[][] {
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', 'O', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', 'T', 'T', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'H', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'I', 'E', '.', '.', '.', '.' },
                { '.', '.', '.', 'N', '.', 'R', '.', '.', '.' },
                { '.', '.', '.', 'G', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' }
        });
        BoardRater rater = new BoardRater();
        BoardAnalyzer analyzer = new BoardAnalyzer(dictionary, 4);
        double greaterRating = rater.deriveRating(analyzer.analyze(greaterBoard).getAnalysis());

        Board lesserBoard = new Board(new char[][] {
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', 'O', '.', 'O', '.', '.', '.', '.', '.' },
                { '.', '.', 'T', 'T', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'H', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'E', 'E', '.', '.', '.', '.' },
                { '.', '.', '.', 'R', '.', 'R', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' }
        });
        double lesserRating = rater.deriveRating(analyzer.analyze(lesserBoard).getAnalysis());
        assertTrue(greaterRating > lesserRating);
    }

    @Test
    public void itShouldRateLeftToRightMore() {
        List<String> testWords = Arrays.asList(
                "another",
                "other",
                "some",
                "thing",
                "something"
        );
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(new MockWordList(testWords.iterator()), 1);

        Board greaterBoard = new Board(new char[][] {
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', 'O', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', 'T', 'T', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'H', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'I', 'E', '.', '.', '.', '.' },
                { '.', '.', '.', 'N', '.', 'R', '.', '.', '.' },
                { '.', '.', '.', 'G', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' }
        });
        BoardRater rater = new BoardRater();
        BoardAnalyzer analyzer = new BoardAnalyzer(dictionary, 4);
        double greaterRating = rater.deriveRating(analyzer.analyze(greaterBoard).getAnalysis());

        Board lesserBoard = new Board(new char[][] {
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', 'O', '.', '.', '.' },
                { '.', '.', '.', 'T', 'T', '.', '.', '.', '.' },
                { '.', '.', '.', 'H', '.', '.', '.', '.', '.' },
                { '.', '.', 'E', 'I', '.', '.', '.', '.', '.' },
                { '.', 'R', '.', 'N', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'G', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.' }
        });
        double lesserRating = rater.deriveRating(analyzer.analyze(lesserBoard).getAnalysis());
        assertTrue(greaterRating > lesserRating);
    }
}