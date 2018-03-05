package com.teratory.art.wordscramble.analyze;

import com.teratory.art.wordscramble.dictionary.Dictionary;
import com.teratory.art.wordscramble.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BoardAnalyzer {


    private final Dictionary dictionary;

    /** The shortest word that will be even considered by this analyzer, regardless of what is in the dictionary. */
    private final int minimumWordLength;

    public BoardAnalyzer(Dictionary dictionary, int minimumWordLength) {
        this.dictionary = dictionary;
        this.minimumWordLength = minimumWordLength;
    }

    public AnalyzedBoard analyze(Board board) {
        BoardAnalysis analysis = new BoardAnalysis();
        for (int y = 0, rows = board.getHeight(); y < rows; y++) {
            for (int x = 0, columns = board.getWidth(); x < columns; x++) {
                detectWords(board, x, y, analysis);
            }
        }
        return new AnalyzedBoard(board, analysis);
    }

    /** Finds the words that start from the given point on the board. */
    protected void detectWords(Board board, int startX, int startY, Collection<FoundWord> foundWords) {
        // Check horizontally from the current position
        for (Direction eastSouthEtc : Direction.values()) {
            detectWords(board, startX, startY, eastSouthEtc, foundWords);
        }
    }

    protected void detectWords(Board board, int startX, int startY, Direction direction, Collection<FoundWord> foundWords) {
        final BoardIterator bi = new BoardIterator(board, startX, startY, direction);
        if (bi.size() >= minimumWordLength) {
            StringBuilder potentialWord = new StringBuilder(bi.size());
            for (Character c : bi) {
                potentialWord.append(c);
                String potentialWordString = potentialWord.toString();
                Dictionary.WordResult wr = dictionary.getWordResult(potentialWordString);
                if (wr.isWord()) {
                    if (potentialWord.length() >= minimumWordLength) {
                        boolean isDuplicate = foundWords.stream().anyMatch(w -> w.getWord().equals(potentialWordString));
                        foundWords.add(new FoundWord(potentialWordString, startX, startY, direction, isDuplicate, wr.getWordPoints()));
                    }
                }
                if (!wr.isPrefix()) break;
            }
        }
    }

}
