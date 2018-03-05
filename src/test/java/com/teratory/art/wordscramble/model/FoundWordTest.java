package com.teratory.art.wordscramble.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FoundWordTest {

    @Test
    public void testTypeComparisonMethod() {
        assertEquals(FoundWord.FoundWordType.EMBEDDED, FoundWord.FoundWordType.STANDALONE.orLesserOf(FoundWord.FoundWordType.EMBEDDED));
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, FoundWord.FoundWordType.STANDALONE.orLesserOf(FoundWord.FoundWordType.OVERLAPPED));
        assertEquals(FoundWord.FoundWordType.EMBEDDED, FoundWord.FoundWordType.OVERLAPPED.orLesserOf(FoundWord.FoundWordType.EMBEDDED));
        assertEquals(FoundWord.FoundWordType.OVERLAPPED, FoundWord.FoundWordType.OVERLAPPED.orLesserOf(FoundWord.FoundWordType.STANDALONE));
        assertEquals(FoundWord.FoundWordType.EMBEDDED, FoundWord.FoundWordType.EMBEDDED.orLesserOf(FoundWord.FoundWordType.STANDALONE));
        assertEquals(FoundWord.FoundWordType.EMBEDDED, FoundWord.FoundWordType.EMBEDDED.orLesserOf(FoundWord.FoundWordType.OVERLAPPED));
    }

}