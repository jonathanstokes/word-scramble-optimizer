package com.teratory.art.wordscramble.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardHistogramTest {

    @Test
    public void itShouldProduceTheRightRatios() {
        BoardHistogram histogram = new BoardHistogram();

        // 2:1
        histogram.incrementConsonantCount();
        histogram.incrementConsonantCount();
        histogram.incrementVowelCount();
        assertEquals(2.0, histogram.getConsonantToVowelRatio(), 0.0);
        assertEquals(0.5, histogram.getVowelToConsonantRatio(), 0.0);

        // 3:1
        histogram.incrementConsonantCount();
        assertEquals(3.0, histogram.getConsonantToVowelRatio(), 0.0);
        assertEquals(0.3333, histogram.getVowelToConsonantRatio(), 0.0001);

        // 4:2
        histogram.incrementVowelAndConsonantCount();
        assertEquals(2.0, histogram.getConsonantToVowelRatio(), 0.0);
        assertEquals(0.5, histogram.getVowelToConsonantRatio(), 0.0);

        // All vowels
        histogram = new BoardHistogram();
        histogram.incrementVowelCount();
        histogram.incrementVowelCount();
        assertEquals(0.0, histogram.getConsonantToVowelRatio(), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, histogram.getVowelToConsonantRatio(), 0.0);

        // All consonants
        histogram = new BoardHistogram();
        histogram.incrementConsonantCount();
        histogram.incrementConsonantCount();
        assertEquals(Double.POSITIVE_INFINITY, histogram.getConsonantToVowelRatio(), 0.0);
        assertEquals(0.0, histogram.getVowelToConsonantRatio(), 0.0);
    }
}