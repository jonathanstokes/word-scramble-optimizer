package com.teratory.art.wordscramble.model;

public class AnalyzedBoard {

    private Board board;

    private BoardAnalysis analysis;

    public AnalyzedBoard(Board board, BoardAnalysis analysis) {
        this.board = board;
        this.analysis = analysis;
    }

    public Board getBoard() {
        return board;
    }

    public BoardAnalysis getAnalysis() {
        return analysis;
    }
}


