package com.teratory.art.wordscramble;

import com.teratory.art.wordscramble.analyze.BoardAnalyzer;
import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.dictionary.FileWordList;
import com.teratory.art.wordscramble.dictionary.WordList;
import com.teratory.art.wordscramble.generate.BoardGenerator;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.rate.BoardRater;
import com.teratory.art.wordscramble.rate.RatedBoard;
import com.teratory.art.wordscramble.rate.TopRatedBoards;
import com.teratory.art.wordscramble.utility.RatedBoardFormatter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

public class Main {

    /** The number of boards we'll keep over time. */
    private static final int TOP_RATED_BOARD_COUNT = 10;

    private static final int PERIODIC_UPDATE_COUNT = 10;

    private static final long PERIODIC_UPDATE_FREQUENCY = 100000000L;

    private static final double MINIMUM_SCORE_FOR_CONSIDERATION = 0.1;

    private static final int MINIMUM_WORD_LENGTH_ALLOWED = 2;

    private static final int FINAL_OUTPUT_COUNT = 30;

    public static void main(String[] args) throws IOException {
        new Main().bruteForceGenerateAndRate((byte)3, (byte)3);
    }

    public void bruteForceGenerateAndRate(byte width, byte height) throws IOException {
        long startTimeInMillis = System.currentTimeMillis();
        System.out.println("Generating " + Math.pow(25, (int)width * (int)height) + " boards.");
        Iterator<Board> boardGenerator = new BoardGenerator().generateAll(width, height);
        BoardAnalyzer analyzer = new BoardAnalyzer(loadDictionary(Math.max((int)width, (int)height)), MINIMUM_WORD_LENGTH_ALLOWED);
        BoardRater rater = new BoardRater();
        final TopRatedBoards topRatedBoards = new TopRatedBoards(TOP_RATED_BOARD_COUNT);
        final AtomicInteger processedBoardCount = new AtomicInteger();
        System.out.println("Rating boards, based on words " + MINIMUM_WORD_LENGTH_ALLOWED + " letters long or longer.");
        Iterable<Board> iterable = () -> boardGenerator;
        long boardsProcessed = StreamSupport.stream(iterable.spliterator(), true)
                .map(analyzer::analyze)
                .map(rater::rateBoard)
                .map(rb -> {
                    if (rb.getRating() >= MINIMUM_SCORE_FOR_CONSIDERATION) topRatedBoards.accumulate(rb);
                    return rb;
                })
                .map(rb -> {
                    int count = processedBoardCount.incrementAndGet();
                    if (count % PERIODIC_UPDATE_FREQUENCY == 0L) printPeriodicUpdate(topRatedBoards, count, startTimeInMillis, rb.getAnalyzedBoard().getBoard());
                    return rb;
                })
                .count();
        System.out.println("processedBoardCount=" + processedBoardCount + ", count()=" + boardsProcessed + ".");
        printFinalResult(topRatedBoards, processedBoardCount.longValue(), startTimeInMillis);
    }

    private void printFinalResult(TopRatedBoards highestRatedBoards, long processedBoardCount, long startTimeInMillis) {
        printUpdate(highestRatedBoards, processedBoardCount, startTimeInMillis, null, FINAL_OUTPUT_COUNT);
        System.out.println("Processing complete.");
    }

    protected void printPeriodicUpdate(TopRatedBoards highestRatedBoards, long processedBoardCount, long startTimeInMillis, Board latestBoardProcessed) {
        printUpdate(highestRatedBoards, processedBoardCount, startTimeInMillis, latestBoardProcessed, PERIODIC_UPDATE_COUNT);
    }

    protected void printUpdate(TopRatedBoards highestRatedBoards, long processedBoardCount, long startTimeInMillis, Board latestBoardProcessed, int numberOfBoardsToPrint) {
        long currentTimeInMillis = System.currentTimeMillis();
        double elapsedTimeInMinutes = (double)(currentTimeInMillis - startTimeInMillis) / 1000.0 / 60.0;
        double boardsPerMinute = processedBoardCount / elapsedTimeInMinutes;
        DecimalFormat formatter = new DecimalFormat("#,##0.#");
        System.out.println("***************************************************************************************************************************");
        System.out.println("After processing " + processedBoardCount + " boards in " + formatter.format(elapsedTimeInMinutes) + " minutes (" + formatter.format(boardsPerMinute) + " boards/minute), here are the top " + numberOfBoardsToPrint + ":");
        Iterator<RatedBoard> boards = highestRatedBoards.getBoards().iterator();
        for (int i = 0, len = numberOfBoardsToPrint; boards.hasNext() && i < len; i++) {
            RatedBoard rb = boards.next();
            printBoard(rb);
        }
        System.out.println("***************************************************************************************************************************");
        if (latestBoardProcessed != null) {
            System.out.println("Also, the most recent board processed was:");
            System.out.println(latestBoardProcessed.toString());
        }
    }

    protected void printBoard(RatedBoard rb) {
        System.out.println(new RatedBoardFormatter().format(rb));
    }


    protected Dictionary loadDictionary(int maximumPossibleWordLength) throws IOException {
        Dictionary dictionary = new Dictionary(maximumPossibleWordLength);
        populateDictionary(dictionary, new File("./src/main/resources/dwyl_alpha_word_list.txt"), 1.0);
        populateDictionary(dictionary, new File("./src/main/resources/john_lawler_word_list.txt"), 2.0);
        populateDictionary(dictionary, new File("./src/main/resources/short_normal_word_list.txt"), 4.0);
        System.out.println("Dictionary ready with " + dictionary.getWordCount() + " words.  Longest word is " + dictionary.getLongestWordLength() + " characters long.");
        return dictionary;
    }

    private void populateDictionary(Dictionary dictionary, File wordListFile, double wordPointValue) throws IOException {
        System.out.println("Loading dictionary from " + wordListFile + ".");
        try (WordList allWords = new FileWordList(wordListFile)) {
            dictionary.ingest(allWords, wordPointValue);
        }
    }
}
