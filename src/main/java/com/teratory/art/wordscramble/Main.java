package com.teratory.art.wordscramble;

import com.teratory.art.wordscramble.analyze.BoardAnalyzer;
import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.dictionary.FileWordList;
import com.teratory.art.wordscramble.dictionary.WordList;
import com.teratory.art.wordscramble.generate.BoardGenerator;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.HistogrammedBoard;
import com.teratory.art.wordscramble.rate.BoardHistogrammer;
import com.teratory.art.wordscramble.rate.BoardRater;
import com.teratory.art.wordscramble.rate.RatedBoard;
import com.teratory.art.wordscramble.rate.TopRatedBoards;
import com.teratory.art.wordscramble.utility.RatedBoardFormatter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {

    /** The number of boards we'll keep over time. */
    private static final int TOP_RATED_BOARD_COUNT = 10;

    private static final int PERIODIC_UPDATE_COUNT = 10;

    private static final long PERIODIC_UPDATE_FREQUENCY = 100000000L;

    private static final double MINIMUM_SCORE_FOR_CONSIDERATION = 0.1;

    private static final int MINIMUM_WORD_LENGTH_ALLOWED = 3;

    private static final int FINAL_OUTPUT_COUNT = 30;

    private static final double MINIMUM_VOWEL_RATIO = 0.01;

    private static final double MINIMUM_CONSONANT_RATIO = 0.01;

    public static void main(String[] args) throws IOException {
        new Main().bruteForceGenerateAndRate((byte)3, (byte)3);
    }

    public void bruteForceGenerateAndRate(byte width, byte height) throws IOException {
        long startTimeInMillis = System.currentTimeMillis();
        long totalBoardCount = (long)Math.pow(26, (int)width * (int)height);
        System.out.println("Generating " + totalBoardCount + " boards.");
        Iterator<Board> boardGenerator = new BoardGenerator().generateAll(width, height);
        BoardAnalyzer analyzer = new BoardAnalyzer(loadDictionary(Math.max((int)width, (int)height)), MINIMUM_WORD_LENGTH_ALLOWED);
        BoardRater rater = new BoardRater();
        BoardHistogrammer histogrammer = new BoardHistogrammer();
        final TopRatedBoards topRatedBoards = new TopRatedBoards(TOP_RATED_BOARD_COUNT);
        final AtomicLong processedBoardCount = new AtomicLong();
        System.out.println("Rating boards, based on words " + MINIMUM_WORD_LENGTH_ALLOWED + " letters long or longer.");
        Iterable<Board> iterable = () -> boardGenerator;
        long boardsProcessed = StreamSupport.stream(iterable.spliterator(), true)
                .peek(b -> processedBoardCount.incrementAndGet())
                .map(histogrammer::computeHistogrammedBoard)
                //.dropWhile(hb -> hb.getHistogram().getVowelToConsonantRatio() < MINIMUM_VOWEL_RATIO || hb.getHistogram().getConsonantToVowelRatio() < MINIMUM_CONSONANT_RATIO)
                .dropWhile(hb -> {
                    if (hb.getHistogram().getVowelToConsonantRatio() < MINIMUM_VOWEL_RATIO || hb.getHistogram().getConsonantToVowelRatio() < MINIMUM_CONSONANT_RATIO) {
                        System.err.println("Dropping board with vowel ratio " + hb.getHistogram().getVowelToConsonantRatio() + ":\n" + hb.getBoard());
                        return true;
                    }
                    return false;
                })
                .map(HistogrammedBoard::getBoard)
                .map(analyzer::analyze)
                .map(rater::rateBoard)
                .map(rb -> {
                    if (rb.getRating() >= MINIMUM_SCORE_FOR_CONSIDERATION) topRatedBoards.accumulate(rb);
                    return rb;
                })
                .map(rb -> {
                    long count = processedBoardCount.get();
                    if (count % PERIODIC_UPDATE_FREQUENCY == 0L) printPeriodicUpdate(topRatedBoards, count, totalBoardCount, startTimeInMillis, rb.getAnalyzedBoard().getBoard());
                    return rb;
                })
                .count();
        System.out.println("processedBoardCount=" + processedBoardCount + ", count()=" + boardsProcessed + ".");
        printFinalResult(topRatedBoards, processedBoardCount.longValue(), totalBoardCount, startTimeInMillis);
    }

    protected void printFinalResult(TopRatedBoards highestRatedBoards, long processedBoardCount, long totalBoardCount, long startTimeInMillis) {
        printUpdate(highestRatedBoards, processedBoardCount, totalBoardCount, startTimeInMillis, null, FINAL_OUTPUT_COUNT);
        System.out.println("Processing complete.");
    }

    protected void printPeriodicUpdate(TopRatedBoards highestRatedBoards, long processedBoardCount, long totalBoardCount, long startTimeInMillis, Board latestBoardProcessed) {
        printUpdate(highestRatedBoards, processedBoardCount, totalBoardCount, startTimeInMillis, latestBoardProcessed, PERIODIC_UPDATE_COUNT);
    }

    protected void printUpdate(TopRatedBoards highestRatedBoards, long processedBoardCount, long totalBoardCount, long startTimeInMillis, Board latestBoardProcessed, int numberOfBoardsToPrint) {
        long currentTimeInMillis = System.currentTimeMillis();
        double elapsedTimeInMinutes = (double)(currentTimeInMillis - startTimeInMillis) / 1000.0 / 60.0;
        double boardsPerMinute = processedBoardCount / elapsedTimeInMinutes;
        double boardPercentage = (double)processedBoardCount / (double)totalBoardCount;
        DecimalFormat percentageFormatter = new DecimalFormat("#0.0000%");
        DecimalFormat formatter = new DecimalFormat("#,##0.#");
        System.out.println("***************************************************************************************************************************");
        System.out.println("After processing " + processedBoardCount + " boards (" + percentageFormatter.format(boardPercentage) + ") in " + formatter.format(elapsedTimeInMinutes) + " minutes (" + formatter.format(boardsPerMinute) + " boards/minute), here are the top " + numberOfBoardsToPrint + ":");
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
        populateDictionary(dictionary, new File("./src/main/resources/john_lawler_word_list.txt"), 4.0);
        populateDictionary(dictionary, new File("./src/main/resources/short_normal_word_list.txt"), 8.0);
        populateDictionary(dictionary, new File("./src/main/resources/noswearing.com.txt"), -50.0);
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
