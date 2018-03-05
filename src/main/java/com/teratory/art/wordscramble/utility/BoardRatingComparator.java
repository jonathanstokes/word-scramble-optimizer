package com.teratory.art.wordscramble.utility;

import com.teratory.art.wordscramble.rate.RatedBoard;

import java.util.Comparator;

/** A comparator that sorts higher rated boards first. */
public class BoardRatingComparator implements Comparator<RatedBoard> {

    private BoardComparator boardComparator = new BoardComparator();

    @Override
    public int compare(RatedBoard left, RatedBoard right) {
        double difference = right.getRating() - left.getRating();
        int cmp = difference < 0 ? -1 : difference > 0 ? 1 : 0;
        if (cmp == 0) {
            // Our ratings are equal, but we can't return 0 unless *everything* is equal.
            cmp = boardComparator.compare(left.getAnalyzedBoard().getBoard(), right.getAnalyzedBoard().getBoard());
            if (cmp == 0) {
                // We have the same board with the same rating. Break the tie arbitrarily by using the analyses.
                cmp = System.identityHashCode(left.getAnalyzedBoard().getAnalysis()) - System.identityHashCode(right.getAnalyzedBoard().getAnalysis());
            }
        }
        return cmp;
    }
}
