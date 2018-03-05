package com.teratory.art.wordscramble.dictionary;

import java.io.*;
import java.util.Iterator;
import java.util.function.Consumer;

public class FileWordList implements WordList {

    BufferedReader reader;

    private String nextWord;

    public FileWordList(File wordListFile) throws IOException {
        reader = new BufferedReader(new FileReader(wordListFile));
        nextWord = reader.readLine();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        return nextWord != null;
    }

    @Override
    public String next() {
        String next = nextWord;
        if (next == null) throw new IllegalStateException();
        try {
            nextWord = reader.readLine();
            return next;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer<? super String> action) {
        throw new UnsupportedOperationException();
    }
}
