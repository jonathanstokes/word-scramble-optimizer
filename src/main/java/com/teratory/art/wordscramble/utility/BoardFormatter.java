package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.Board;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/** Writes out a Board as a string. */
public class BoardFormatter extends Format {


    /**
     * Creates a text rendering of a board, such as:
     * <pre>
     *     ┌───────┐
     *     │ T H E │
     *     │ B O A │
     *     │ T P B │
     *     └───────┘
     * </pre>
     */
    public StringBuffer format(Board board, StringBuffer sb, FieldPosition fieldPosition) {
        final int width = board.getWidth();
        final int height = board.getHeight();
        sb.append("┌─");
        for (int i = 0; i < width; i++) sb.append("──");
        sb.append("┐\n");
        for (int y = 0; y < height; y++) {
            sb.append("│ ");
            for (int x = 0; x < width; x++) {
                sb.append(board.getCharAt(x, y)).append(' ');
            }
            sb.append("│\n");
        }
        sb.append("└─");
        for (int i = 0; i < width; i++) sb.append("──");
        sb.append("┘\n");
        return sb;
    }

    public String format(Board board) {
        return format(board, new StringBuffer(), null).toString();
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return format((Board)obj, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
}
