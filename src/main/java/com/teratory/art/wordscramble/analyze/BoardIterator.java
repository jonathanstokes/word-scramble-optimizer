package com.teratory.art.wordscramble.analyze;

import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.Direction;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * Iterates over the characters of a Board, starting from a specific point and moving in one direction.
 */
public class BoardIterator extends AbstractCollection<Character> {

    private Board board;

    private int startX;

    private int startY;

    private Direction direction;

    public BoardIterator(Board board, int startX, int startY, Direction direction) {
        this.board = board;
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
    }


    @Override
    public Iterator<Character> iterator() {
        return new BiIterator();
    }

    @Override
    public int size() {
        // We iterate to the end of the board, so our size is the length to the edge, in whatever direction
        // we're going.
        int limit = Integer.MAX_VALUE;
        if (direction.getXOffset() > 0) limit = Math.min(limit, board.getWidth() - startX);
        else if (direction.getXOffset() < 0) limit = Math.min(limit, startX + 1);
        if (direction.getYOffset() > 0) limit = Math.min(limit, board.getHeight() - startY);
        else if (direction.getYOffset() < 0) limit = Math.min(limit, startY + 1);
        return limit;
    }

    protected class BiIterator implements Iterator<Character> {

        private int nextX;

        private int nextY;

        BiIterator() {
            nextX = startX;
            nextY = startY;
        }

        @Override
        public boolean hasNext() {
            return nextX >= 0 && nextX < board.getWidth() && nextY >= 0 && nextY < board.getHeight();
        }

        @Override
        public Character next() {
            Character letter = board.getCharAt(nextX, nextY);
            nextX = nextX + direction.getXOffset();
            nextY = nextY + direction.getYOffset();
            return letter;
        }
    }
}
