package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.model.Board;

import java.util.Comparator;

public class BoardComparator implements Comparator<Board> {

    @Override
    public int compare(Board left, Board right) {
        int cmp;
        final int rWidth = right.getWidth();
        final int rHeight = right.getHeight();
        final int lWidth = left.getWidth();
        final int lHeight = left.getHeight();
        if (rWidth != lWidth || rHeight != lHeight) {
            // bigger boards first
            cmp = (rWidth * rHeight) - (lWidth * lHeight);
            // wider boards next
            if (cmp == 0) cmp = rWidth - lWidth;
            // taller boards next
            if (cmp == 0) cmp = rHeight - lHeight;
        }
        else {
            // then compare alphabetically
            cmp = 0;
            for (int y = 0; cmp == 0 && y < lHeight; y++) {
                for (int x = 0; cmp == 0 && x < lWidth; x++) {
                    cmp = right.getCharAt(x, y) - left.getCharAt(x, y);
                }
            }
        }
        return cmp;
    }
}
