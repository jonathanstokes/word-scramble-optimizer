package com.teratory.art.wordscramble.dictionary;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DictionaryTest {

    protected WordList mockWordList;

    @Before
    public void setUp() throws Exception {
        List<String> testWords = Arrays.asList(
            "chips",
            "fish",
            "fishoil",
            "pizza",
            "pasta",
            "chocolate"
        );
        mockWordList = new MockWordList(testWords.iterator());
    }

    @Test
    public void testIsWord() {
        Dictionary dictionary = new Dictionary(10);

        assertFalse(dictionary.isWord("chips"));
        assertFalse(dictionary.isWord("fish"));
        assertFalse(dictionary.isWord("pizza"));
        assertFalse(dictionary.isWord("other"));

        dictionary.ingest(mockWordList, 1);

        assertTrue(dictionary.isWord("chips"));
        assertTrue(dictionary.isWord("fish"));
        assertTrue(dictionary.isWord("pizza"));
        assertFalse(dictionary.isWord("other"));
    }

    @Test
    public void testGetWordResult() {
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(mockWordList, 1);

        assertEquals(false, dictionary.getWordResult("chip").isWord());
        assertEquals(true, dictionary.getWordResult("chip").isPrefix());
        assertEquals(true, dictionary.getWordResult("chips").isWord());
        assertEquals(false, dictionary.getWordResult("chips").isPrefix());
        assertEquals(true, dictionary.getWordResult("fish").isWord());
        assertEquals(true, dictionary.getWordResult("fish").isPrefix());
        assertEquals(false, dictionary.getWordResult("fisho").isWord());
        assertEquals(true, dictionary.getWordResult("fisho").isPrefix());
        assertEquals(true, dictionary.getWordResult("fishoil").isWord());
        assertEquals(false, dictionary.getWordResult("fishoil").isPrefix());
        assertEquals(false, dictionary.getWordResult("other").isWord());
        assertEquals(false, dictionary.getWordResult("other").isPrefix());
    }

    @Test
    public void testMaximumWordLength() {
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(mockWordList, 1);

        assertEquals(9, dictionary.getLongestWordLength());
    }
    
    @Test
    public void itShouldHonorWordValue() {
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(mockWordList, 1);
        List<String> betterWords = Arrays.asList(
                "fish",
                "hamburger"
        );
        WordList betterWordList = new MockWordList(betterWords.iterator());
        dictionary.ingest(betterWordList, 2);

        assertEquals(1.0, dictionary.getWordResult("chips").getWordPoints(), 0);
        assertEquals(2.0, dictionary.getWordResult("fish").getWordPoints(), 0);
        assertEquals(2.0, dictionary.getWordResult("hamburger").getWordPoints(), 0);
    }
}