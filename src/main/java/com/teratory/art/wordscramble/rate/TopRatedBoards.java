package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.utility.BoardRatingComparator;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An efficient, thread-safe collector of the best RatedBoard objects.
 *
 * Boards with any rating get added to it over time, some of which are kept and some are discarded.  When asked, this
 * collector can produce a SortedSet of the top N boards.
 *
 */
public class TopRatedBoards {

    private final int minimumTargetSize;

    /** The lowest rating currently contained in this collection, or NEGATIVE_INFINITY if this collection is empty. */
    private double currentMinimumRating = Double.NEGATIVE_INFINITY;

    private final NavigableMap<Double, List<RatedBoard>> boardsByRating;

    private final AtomicInteger currentBoardCount = new AtomicInteger();

    /** We don't normally prune until we reach this many elements. */
    private final static int WORKING_ELEMENT_COUNT = 1000;

    /** Whether any board has been added since the last pruning. */
    private volatile boolean isDirty = false;

    /**
     *
     * @param minimumTargetSize the minimum size this collection will grow to.  It can be smaller if this many items
     *                          have never been placed in it, and it can be slightly larger if the ratings of the last
     *                          few items end in a tie. (A board with a rating tied with the last kept board will never
     *                          be pruned.)
     */
    public TopRatedBoards(int minimumTargetSize) {
        this.minimumTargetSize = minimumTargetSize;
        boardsByRating = new ConcurrentSkipListMap<>();
    }

    public Collection<RatedBoard> getBoards() {
        List<RatedBoard> output = new ArrayList<>();
        pruneBoards();
        for (Map.Entry<Double, List<RatedBoard>> e : boardsByRating.descendingMap().entrySet()) {
            output.addAll(e.getValue());
        }
        return output;
    }

    /**
     * Adds a board to this collector for consideration.
     */
    public void accumulate(RatedBoard rb) {
        double rating = rb.getRating();
        if (rating >= currentMinimumRating) {
            List<RatedBoard> ratingList = getOrCreateListForRating(rating);
            ratingList.add(rb);
            isDirty = true;
            int count = currentBoardCount.incrementAndGet();
            if (count > WORKING_ELEMENT_COUNT) pruneBoards();
        }
    }

    private void pruneBoards() {
        if (isDirty) {
            int newBoardCount = 0;
            double newMinimumRating = currentMinimumRating;
            for (Iterator<Map.Entry<Double,List<RatedBoard>>> i = boardsByRating.descendingMap().entrySet().iterator(); i.hasNext(); ) {
                Map.Entry<Double,List<RatedBoard>> entry = i.next();
                if (newBoardCount >= minimumTargetSize) i.remove();
                else {
                    newBoardCount += entry.getValue().size();
                    newMinimumRating = entry.getKey();
                }
            }
            currentBoardCount.set(newBoardCount);
            currentMinimumRating = newMinimumRating;
            isDirty = false;
        }
    }

    private List<RatedBoard> getOrCreateListForRating(double rating) {
        List<RatedBoard> list = boardsByRating.get(rating);
        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<>());
            List<RatedBoard> newList = boardsByRating.putIfAbsent(rating, list);
            if (newList != null) list = newList;
        }
        return list;
    }
}
