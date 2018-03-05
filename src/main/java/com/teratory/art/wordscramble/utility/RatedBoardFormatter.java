package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.FoundWord;
import com.teratory.art.wordscramble.rate.RatedBoard;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Writes out a RatedBoard as a string. */
public class RatedBoardFormatter extends Format {

    private BoardFormatter boardFormatter;
    private DecimalFormat ratingFormatter;

    public RatedBoardFormatter() {
        this(new BoardFormatter());
    }

    public RatedBoardFormatter(BoardFormatter boardFormatter) {
        this.boardFormatter = boardFormatter;
        ratingFormatter = new DecimalFormat("#,##0.0###");
    }

    /**
     * Creates a text rendering of a rated board, such as:
     * <pre>
     *     ┌───────┐   Rating: 3.2301
     *     │ T H E │   THE (at 0,0 going EAST)      TOE (at 0,2 going NORTHEAST)
     *     │ B O A │   BOA (at 2,0 going EAST)      BAE (at 2,2 going NORTH)
     *     │ T P B │   HOP (at 1,0 going SOUTH)
     *     └───────┘
     * </pre>
     */
    public StringBuffer format(RatedBoard ratedBoard, StringBuffer sb, FieldPosition fieldPosition) {
        // To start with the formatted board gives us N lines, each the same width.
        final String[] boardLines = boardFormatter.format(ratedBoard.getAnalyzedBoard().getBoard()).split("\n");
        sb.append(boardLines[0]).append("   Rating: ").append(ratingFormatter.format(ratedBoard.getRating())).append("\n");
        List<FoundWord> foundWords = ratedBoard.getAnalyzedBoard().getAnalysis();
        if (foundWords.isEmpty()) {
            for (int i = 1, len = boardLines.length; i < len; i++) {
                sb.append(boardLines[i]).append('\n');
            }
        }
        else {
            final int foundWordColumnCount = (int)Math.ceil( (double)foundWords.size() / (double)(boardLines.length - 1));
            final int foundWordRowCount = (Math.round( (float)foundWords.size() / (float)foundWordColumnCount));
            final Optional<String> longestFoundWordString = foundWords.stream().map(String::valueOf).max(Comparator.comparingInt(String::length));
            final int longestFoundWordStringLength = longestFoundWordString.get().length();
            for (int i = 1, len = boardLines.length; i < len; i++) {
                sb.append(boardLines[i]).append("   ");
                int r = i - 1;
                // Show the right found word for the columns on this row (r,c).
                for (int c = 0; c < foundWordColumnCount; c++) {
                    if (r < foundWordRowCount) {
                        int fwIndex = c * foundWordRowCount + r;
                        if (fwIndex < foundWords.size()) {
                            FoundWord fw = foundWords.get(fwIndex);
                            String fwString = fw.toString();
                            sb.append(fwString);
                            if (c < foundWordColumnCount - 1) {  // if there are columns after me
                                // pad the value so the next column will line up.
                                sb.append("                                      ".substring(0, longestFoundWordStringLength - fwString.length() + 1));
                            }
                        }
                    }
                }
                sb.append('\n');
            }
        }
        return sb;
    }

    public String format(RatedBoard ratedBoard) {
        return format(ratedBoard, new StringBuffer(), null).toString();
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return format((RatedBoard)obj, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
}
