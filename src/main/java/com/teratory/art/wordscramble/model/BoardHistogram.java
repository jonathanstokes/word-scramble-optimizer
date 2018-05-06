package com.teratory.art.wordscramble.model;

/** A collection of statistics about the letter composition of a board. */
public class BoardHistogram {

    private int vowelCount;

    private int consonantCount;

    private int totalCount;

    public void incrementVowelCount() {
        vowelCount++;
        totalCount++;
    }

    public void incrementConsonantCount() {
        consonantCount++;
        totalCount++;
    }

    public void incrementVowelAndConsonantCount() {
        vowelCount++;
        consonantCount++;
        totalCount++;
    }

    /** Gets the ratio of vowels to consonants. */
    public double getVowelToConsonantRatio() {
        return (double)vowelCount / (double)consonantCount;
    }

    public double getConsonantToVowelRatio() {
        return (double)consonantCount / (double)vowelCount;
    }
}
