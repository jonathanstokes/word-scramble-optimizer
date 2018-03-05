package com.teratory.art.wordscramble.model;

public class FoundWord {

    private String word;

    private int startX;
    private int startY;
    private Direction direction;
    private boolean duplicate;
    private final double dictionaryValue;

    public FoundWord(String word, int startX, int startY, Direction direction, boolean isDuplicate, double dictionaryValue) {
        this.word = word;
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
        this.duplicate = isDuplicate;
        this.dictionaryValue = dictionaryValue;
    }

    @Override
    public String toString() {
        return word + " (at " + startX + "," + startY + " going " + direction + ")";
    }

    public String getWord() {
        return word;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Returns `true` if this found word appears previously in the overall set.  Note that the first occurrence of the
     * word will not be marked as a duplicate, but all subsequent occurrences will.
     */
    public boolean isDuplicate() {
        return duplicate;
    }

    /**
     * Returns the value of this word, based on which ingest list it came from in the dictionary.
     */
    public double getDictionaryValue() {
        return dictionaryValue;
    }

    public enum FoundWordType {
        STANDALONE,
        OVERLAPPED,
        EMBEDDED;

        /**
         * Not sure what to name this method, but this returns either `this` or the `otherType`, whichever is
         * of lesser value (higher ordinal).  Meaning, if one is STANDALONE and the other is OVERLAPPED, then
         * OVERLAPPED will be returned.  If OVERLAPPED and EMBEDDED are given, EMBEDDED will be returned.
         * @param otherType
         * @return
         */
        public FoundWordType orLesserOf(FoundWordType otherType) {
            return ordinal() >= otherType.ordinal() ? this : otherType;
        }
    }
}
