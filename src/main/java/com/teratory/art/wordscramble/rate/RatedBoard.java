package com.teratory.art.wordscramble.rate;

import com.teratory.art.wordscramble.model.AnalyzedBoard;
import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.BoardAnalysis;

import java.text.DecimalFormat;

public class RatedBoard {

    private AnalyzedBoard analyzedBoard;

    private double rating;

    public RatedBoard(AnalyzedBoard analyzedBoard, double rating) {
        this.analyzedBoard = analyzedBoard;
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public AnalyzedBoard getAnalyzedBoard() {
        return analyzedBoard;
    }

//    public BoardAnalysis getAnalysis() {
//        return analyzedBoard.getAnalysis();
//    }
//
//    public Board getBoard() {
//        return analyzedBoard.getBoard();
//    }
//
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[ ");
        Board board = analyzedBoard.getBoard();
        BoardAnalysis analysis = analyzedBoard.getAnalysis();
        for (int y = 0, rows = board.getHeight(); y < rows; y++) {
            for (int x = 0, cols = board.getWidth(); x < cols; x++) {
                sb.append(board.getCharAt(x, y));
            }
        }
        sb.append(" R");
        sb.append(new DecimalFormat("0.0##").format(getRating()));
        sb.append(" ");
        sb.append(analysis.size());
        sb.append(" match");
        if (analysis.size() != 1) sb.append("es");
        sb.append(" ]");
        return sb.toString();
    }
}
