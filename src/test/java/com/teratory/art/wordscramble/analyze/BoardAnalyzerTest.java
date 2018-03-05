package com.teratory.art.wordscramble.analyze;

import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.dictionary.MockWordList;
import com.teratory.art.wordscramble.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardAnalyzerTest {

    @Test
    public void testSingleDetectWords() {
        // If my board looks like this...
        Board board = new Board(new char[][] {
                { 'A', 'M', 'C' },
                { 'S', 'E', 'E' },
                { 'G', 'H', 'I' }
        });
        // And my word list looks like this...
        List<String> words = Arrays.asList(
                "me",
                "meh",
                "see"
        );
        Dictionary dictionary = new Dictionary(10);
        dictionary.ingest(new MockWordList(words.iterator()), 1);
        // Then...
        BoardAnalyzer analyzer = new BoardAnalyzer(dictionary, 2);
        List<FoundWord> foundWords = new ArrayList<>();
        analyzer.detectWords(board, 0, 0, Direction.EAST, foundWords);
        assertEquals(0, foundWords.size());
        analyzer.detectWords(board, 0, 1, Direction.EAST, foundWords);
        assertEquals(1, foundWords.size());
        assertEquals("SEE", foundWords.get(0).getWord());

        foundWords.clear();
        analyzer.detectWords(board, 0, 0, Direction.SOUTH, foundWords);
        assertEquals(0, foundWords.size());
        analyzer.detectWords(board, 1, 0, Direction.SOUTH, foundWords);
        assertEquals(2, foundWords.size());

    }
}