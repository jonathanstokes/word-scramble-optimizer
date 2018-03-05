package com.teratory.art.wordscramble.generate;

import com.teratory.art.wordscramble.model.Board;

import java.util.*;

public class BoardGenerator {

    private static final byte Z = (byte)'Z';

    private static final byte A = (byte)'A';

    public Iterator<Board> generateAll(byte width, byte height) {
        return new SequentialBoardIterator(width, height);
    }

    protected class SequentialBoardIterator implements Iterator<Board> {
        private byte[] nextBoard;
        private byte width;
        private byte height;
        private int arrayLength;
        private boolean generatedLastBoard;
        private boolean hasNext;

        public SequentialBoardIterator(byte width, byte height) {
            this.width = width;
            this.height = height;
            arrayLength = (int)width * (int)height;
            nextBoard = new byte[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                nextBoard[i] = 'A';
            }
            generatedLastBoard = false;
            hasNext = true;
        }


        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public Board next() {
            if (hasNext) {
                Board b = new Board(width, height, Arrays.copyOf(nextBoard, arrayLength));
                if (generatedLastBoard) hasNext = false;
                else  generatedLastBoard = !incrementData(nextBoard);
                return b;
            }
            else throw new NoSuchElementException();
        }
    }

    /**
     *
     * @param data
     * @return `true` if the data can be incremented again.
     */
    protected boolean incrementData(byte[] data) {
        int changes = 0;
        int onesPosition = data.length - 1;
        for (int i = onesPosition; i >= 0; i--) {
            byte letter = data[i];
            if (letter == Z) {
                // Reset this digit to A and let the neighbor to the left get cycled too.
                data[i] = A;
                changes++;
            }
            else {
                data[i] = (byte)(letter + 1);
                changes++;
                break;
            }
        }
        // If we've got all Zs, we have to return false.
        for (int i = 0, len = data.length; i < len; i++) {
            if (data[i] != Z) return true;
        }
        return false;
    }
}
