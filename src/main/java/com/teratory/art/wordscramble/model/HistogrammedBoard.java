package com.teratory.art.wordscramble.model;

public class HistogrammedBoard {

    private Board board;

    private BoardHistogram statistics;

    public HistogrammedBoard(Board board, BoardHistogram statistics) {
        this.board = board;
        this.statistics = statistics;
    }

    public Board getBoard() {
        return board;
    }

    public BoardHistogram getHistogram() {
        return statistics;
    }
}


