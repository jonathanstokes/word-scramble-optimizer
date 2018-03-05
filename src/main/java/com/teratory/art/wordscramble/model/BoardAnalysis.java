package com.teratory.art.wordscramble.model;

import java.util.*;

/**
 * TODO: Need to rework this class so that it breaks things down by direction as well as word length.
 *
 * Perhaps it's just a collection of FoundWord objects that can generate summary stats, including which
 * ones are overlapping or embedded.
 *
 * TODO: Also, need to track duplicates (e.g. ME found twice) so that severe penalties can be assessed.
 *
 * TODO: Also need to account for curse words.
 */
public class BoardAnalysis extends ArrayList<FoundWord> {

    private Statistics stats = null;

    public BoardAnalysis() {
    }

    public Statistics getStatistics() {
        stats = new Statistics();
        return stats;
    }

    @Override
    public boolean add(FoundWord foundWord) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        return super.add(foundWord);
    }

    @Override
    public void add(int index, FoundWord element) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        super.add(index, element);
    }

    @Override
    public FoundWord remove(int index) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        return super.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends FoundWord> c) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends FoundWord> c) {
        if (stats != null) throw new UnsupportedOperationException("Cannot modify after statistics have been gathered.");
        return super.addAll(index, c);
    }

    public class Statistics {
        private short totalWordCount;
        private short standaloneWordCount;
        private short embeddedWordCount;
        private short overlappedWordCount;

        private final Map<Direction, Set<FoundWord>> wordsByDirection;

        private final Map<FoundWord.FoundWordType, Set<FoundWord>> wordsByFoundType;

        private final Map<Direction, Map<FoundWord.FoundWordType, Map<Integer, Set<FoundWord>>>> wordsByDirectionTypeAndLength;

        protected Statistics() {
            // First separate our words by their direction.
            wordsByDirection = new HashMap<>();
            for (FoundWord word : BoardAnalysis.this) {
                Direction direction = word.getDirection();
                Set<FoundWord> words = wordsByDirection.get(direction);
                if (words == null) {
                    words = new HashSet<>();
                    wordsByDirection.put(direction, words);
                }
                words.add(word);
            }
            // Now, by comparing a word with all the others going its same direction, we can determine if
            // it is Embedded, Overlapping, or Standalone.
            wordsByFoundType = new HashMap<>();
            wordsByDirectionTypeAndLength = new HashMap<>();
            for (Direction direction : wordsByDirection.keySet()) {
                Set<FoundWord> sameDirectionWords = wordsByDirection.get(direction);
                for (FoundWord nextWord: sameDirectionWords) {
                    FoundWord.FoundWordType nextWordType = FoundWord.FoundWordType.STANDALONE;
                    for (FoundWord compareWord: sameDirectionWords) {
                        nextWordType = nextWordType.orLesserOf(getRelativeType(nextWord, compareWord));
                    }
                    // Add this word by type
                    Set<FoundWord> words = wordsByFoundType.get(nextWordType);
                    if (words == null) {
                        words = new HashSet<>();
                        wordsByFoundType.put(nextWordType, words);
                    }
                    words.add(nextWord);
                    // Also add it by direction, type, and length
                    accumulateByDirectionTypeAndLength(direction, nextWordType, nextWord);
                }
            }
        }

        private void accumulateByDirectionTypeAndLength(Direction direction, FoundWord.FoundWordType type, FoundWord word) {
            Map<FoundWord.FoundWordType, Map<Integer, Set<FoundWord>>> mapByType = wordsByDirectionTypeAndLength.get(direction);
            if (mapByType == null) {
                mapByType = new HashMap<>();
                wordsByDirectionTypeAndLength.put(direction, mapByType);
            }
            Map<Integer, Set<FoundWord>> mapByLength = mapByType.get(type);
            if (mapByLength == null) {
                mapByLength = new HashMap<>();
                mapByType.put(type, mapByLength);
            }
            Set<FoundWord> wordSet = mapByLength.get(word.getWord().length());
            if (wordSet == null) {
                wordSet = new HashSet<>();
                mapByLength.put(word.getWord().length(), wordSet);
            }
            wordSet.add(word);
        }

        public Map<Direction, Map<FoundWord.FoundWordType, Map<Integer, Set<FoundWord>>>> getWordsByDirectionTypeAndLength() {
            return wordsByDirectionTypeAndLength;
        }

        /**
         * Called from the constructor to determine the type of the left word relative to the right.
         * @return whether the left word is EMBEDDED in the right, OVERLAPPED with the right, or STANDALONE
         *         if no intersection exists.
         */
        protected FoundWord.FoundWordType getRelativeType(FoundWord left, FoundWord right) {
            // First, if these two are the same found word, we don't compare them...
            if (!left.equals(right)) {
                // Second, we need to see if these two are even on the same plane. E.g. if they're both going
                // EAST but they're on different rows, they don't intersect at all.
                if (areOnTheSameLine(left, right)) {
                    // Lastly, we get the comparison of these two words on the same plane.
                    return getRelativeTypeOfSameLineWords(left, right);
                }
            }
            return FoundWord.FoundWordType.STANDALONE;
        }

        /**
         * Determines the type of the first word relative to the second, given that both are on the same
         * line.
         * @return whether the first word is EMBEDDED in the second, OVERLAPPED with the second, or STANDALONE.
         */
        protected FoundWord.FoundWordType getRelativeTypeOfSameLineWords(FoundWord first, FoundWord second) {
            // We first transpose the two words' relative positions to think of them as on a number line, left to
            // right on a horizontal line, regardless of what direction their line really goes.
            int distanceBetweenStartingPoints;
            int distanceDirectionFactor;  // 1 or -1
            if (first.getDirection().getYOffset() != 0) {
                distanceBetweenStartingPoints = second.getStartY() - first.getStartY();
                distanceDirectionFactor = first.getDirection().getYOffset();
            }
            else {
                distanceBetweenStartingPoints = second.getStartX() - first.getStartX();
                distanceDirectionFactor = first.getDirection().getXOffset();
            }
            //int distanceBetweenStartingPoints = Math.max(Math.abs(second.getStartX() - first.getStartX()), Math.abs(second.getStartY() - first.getStartY()));
            int distanceFromSecondToFirstStartingPoint = distanceDirectionFactor * distanceBetweenStartingPoints;

            int secondStart = 0;
            int secondEnd = secondStart + second.getWord().length() - 1;
            int firstStart = secondStart - distanceFromSecondToFirstStartingPoint;
            int firstEnd = firstStart + first.getWord().length() - 1;

            if (firstStart == secondStart) {
               // Both words start at the same place, we we're either embedded or standalone depending on which is longer
               if (firstEnd > secondEnd) return FoundWord.FoundWordType.STANDALONE;
               else if (firstEnd < secondEnd) return FoundWord.FoundWordType.EMBEDDED;
               else throw new IllegalStateException("Words " + first + " and " + second + " are the same length and start in the same place.");
            }
            else if (firstStart > secondStart) {
                if (firstEnd <= secondEnd) return FoundWord.FoundWordType.EMBEDDED;
                else if (firstStart <= secondEnd) return FoundWord.FoundWordType.OVERLAPPED;
                else return FoundWord.FoundWordType.STANDALONE;
            }
            else {
                // the first starts before the second, but still could overlap.
                if (firstEnd >= secondStart) {
                    if (firstEnd >= secondEnd) {
                        // The second word is embedded in the first, so we still treat the first as STANDALONE. The second will
                        // get treated as EMBEDDED when its evaluated.
                        return FoundWord.FoundWordType.STANDALONE;
                    }
                    else return FoundWord.FoundWordType.OVERLAPPED;
                }
                else return FoundWord.FoundWordType.STANDALONE;
            }
        }

        /**
         * Determines if the two found words are on the same line with each other, whether that's
         * horizontal or vertical or diagonal.
         */
        protected boolean areOnTheSameLine(FoundWord left, FoundWord right) {
            // IMPROVE: There might be a more efficient way using y=mx+b.
            // We're on the same line if we can get from one word's starting point to the other word's
            // starting point traveling in the direction.
            Direction direction = left.getDirection();
            if (direction.getXOffset() == 0) {
                // We have a vertical line, so the X coordinates have to be the same in our two words
                return left.getStartX() == right.getStartX();
            }
            else if (direction.getYOffset() == 0) {
                // We have a horizontal line, so the Y coordinates must match
                return left.getStartY() == right.getStartY();
            }
            else {
                // We should be able to use "y = mx + b" to see if these starting points are on the same
                // diagonal.
                double m = direction.getYOffset() / direction.getXOffset();  // m = deltaY / deltaX
                // b = y - mx, using our first x,y coordinates
                double b = left.getStartY() - m * left.getStartX();
                // Now see if y really does equal mx+b in our second case.
                double secondMxPlusB = m * right.getStartX() + b;
                return (int)Math.round(secondMxPlusB) == right.getStartY();
            }
        }

        // TODO: These methods really need to be based on what is needed for rating.
        public int getTotalWordCount() {
            return BoardAnalysis.this.size();
        }

        public int getWordCount(FoundWord.FoundWordType type) {
            Set<FoundWord> words = wordsByFoundType.get(type);
            return words != null ? words.size() : 0;
        }

        public int getWordCount(Direction direction) {
            Set<FoundWord> words = wordsByDirection.get(direction);
            return words != null ? words.size() : 0;
        }
    }

    public static class WordLengthAnalysis {
        private short totalWordCount;
        private short standaloneWordCount;
        private short embeddedWordCount;
        private short overlappedWordCount;

        public void incrementStandaloneWordCount() {
            this.standaloneWordCount++;
            this.totalWordCount++;
        }

        public void incrementEmbeddedWordCount() {
            this.embeddedWordCount++;
            this.totalWordCount++;
        }

        public void incrementOverlappedWordCount() {
            this.overlappedWordCount++;
            this.totalWordCount++;
        }
        public short getTotalWordCount() {

            return totalWordCount;
        }

        public short getStandaloneWordCount() {
            return standaloneWordCount;
        }

        public short getEmbeddedWordCount() {
            return embeddedWordCount;
        }

        public short getOverlappedWordCount() {
            return overlappedWordCount;
        }
    }

}
