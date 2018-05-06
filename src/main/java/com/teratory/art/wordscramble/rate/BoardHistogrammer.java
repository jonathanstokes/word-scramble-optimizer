package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.BoardHistogram;
import com.teratory.art.wordscramble.model.HistogrammedBoard;

public class BoardHistogrammer {

    public BoardHistogram computeHistogram(Board board) {
        BoardHistogram histogram = new BoardHistogram();
        int width = board.getWidth();
        int height = board.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                accumulate(histogram, board.getCharAt(x, y));
            }
        }
        return histogram;
    }

    private void accumulate(BoardHistogram histogram, char letter) {
        if (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U') histogram.incrementVowelCount();
        else if (letter == 'Y') histogram.incrementVowelAndConsonantCount();
        else histogram.incrementConsonantCount();
    }

    public HistogrammedBoard computeHistogrammedBoard(Board board) {
        return new HistogrammedBoard(board, computeHistogram(board));
    }
}
