package com.teratory.art.wordscramble.dictionary;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

public class MockWordList implements WordList {

    private Iterator<String> iterator;

    public MockWordList(Iterator<String> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super String> action) {
        iterator.forEachRemaining(action);
    }

    @Override
    public void close() throws IOException {
    }
}
