package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.model.*;

import java.util.Map;
import java.util.Set;

import static com.teratory.art.wordscramble.model.FoundWord.FoundWordType.*;
import static java.util.Map.entry;

/**
 * A service that looks at a BoardAnalysis, applies rules, and derives a single numeric score.
 */
public class BoardRater {

    // We value a *long*, left-to-right, standalone word the most.
    // Next, a long, top-to-bottom, standalone word.
    // Then maybe diagonal down-and-to-the-right.
    // After that, we have penalty factors for backwards and upside down.
    // We also have penalty factors for overlapping words.
    // Lastly, we have steep penalty factors for embedded words.

    private Map<Direction, Double> directionFactor = Map.ofEntries(
        entry(Direction.EAST, 3.0),
        entry(Direction.SOUTH, 3.0),
        entry(Direction.SOUTHEAST, 2.0),
        entry(Direction.NORTHEAST, 1.0),
        entry(Direction.WEST, 0.8),
        entry(Direction.NORTH, 0.8),
        entry(Direction.SOUTHWEST, 0.5),
        entry(Direction.NORTHWEST, 0.5)
    );

    private static final Integer EXTRA_LARGE_WORD_LENGTH = 0;

    private Map<Integer, Double> wordLengthFactor = Map.ofEntries(
        entry(1, 0.0),
        entry(2, 0.01),
        entry(3, 0.10),
        entry(4, 1.0),
        entry(5, 2.0),          // (n - 4) ^ 2 + 1
        entry(6, 5.0),
        entry(7, 9.0),
        entry(8, 17.0),
        entry(9, 26.0),
        entry(10, 37.0),
        entry(11, 50.0),
        entry(12, 65.0),
        entry(EXTRA_LARGE_WORD_LENGTH, 82.0)
    );

    private Map<FoundWord.FoundWordType, Double> foundWordTypeFactor = Map.ofEntries(
        entry(STANDALONE, 1.0),
        entry(OVERLAPPED, 0.7),
        entry(EMBEDDED, 0.2)
    );

    private double duplicateWordPenaltyFactor = -0.1;

    public RatedBoard rateBoard(AnalyzedBoard analyzedBoard) {
        try {
            double rating = deriveRating(analyzedBoard.getAnalysis());
            return new RatedBoard(analyzedBoard, rating);
        }
        catch (RuntimeException e) {
            throw new IllegalStateException("Could not rate board:\n" + analyzedBoard.getBoard(), e);
        }
    }

    protected double deriveRating(BoardAnalysis analysis) {
        double score = 0;
        if (!analysis.isEmpty()) {
            double lastWordContribution = 0.0;
            for (Map.Entry<Direction, Map<FoundWord.FoundWordType, Map<Integer, Set<FoundWord>>>> directionEntry : analysis.getStatistics().getWordsByDirectionTypeAndLength().entrySet()) {
                Direction direction = directionEntry.getKey();
                for (Map.Entry<FoundWord.FoundWordType, Map<Integer, Set<FoundWord>>> typeEntry : directionEntry.getValue().entrySet()) {
                    FoundWord.FoundWordType type = typeEntry.getKey();
                    for (Map.Entry<Integer, Set<FoundWord>> wordLengthEntry : typeEntry.getValue().entrySet()) {
                        Integer wordLength = wordLengthEntry.getKey();
                        for (FoundWord word : wordLengthEntry.getValue()) {
                            double wordScore = word.getDictionaryValue();
                            Double wordLengthScore = wordLengthFactor.get(wordLength);
                            if (wordLengthScore == null) wordLengthScore = wordLengthFactor.get(EXTRA_LARGE_WORD_LENGTH);
                            double typeScoreFactor = foundWordTypeFactor.get(type);
                            double directionScoreFactor = directionFactor.get(direction);
                            double duplicateFactor = word.isDuplicate() ? duplicateWordPenaltyFactor : 1.0;
                            // Assessing penalties with this formula only works if one factor at most is negative.
                            if (wordScore < 0) {
                                // This is a curse word penalty.  Shorter curse words shouldn't change the value.
                                wordLengthScore = 1.0;
                                // duplicateFactor can also be negative. If it is, then...
                                if (duplicateFactor < 0) {
                                    // ... treat it as positive for the right overall effect.
                                    wordScore = Math.abs(wordScore);
                                }
                            }
                            lastWordContribution = wordScore * wordLengthScore * typeScoreFactor * directionScoreFactor * duplicateFactor;
                            score += lastWordContribution;
                            score = score;
                        }
                    }
                }

            }
            if (score == 0.0 && lastWordContribution == 0.0) {
                throw new IllegalStateException("Unexpected rating 0.0 for analysis " + analysis + ".");
            }
        }
        return score;
    }
}
