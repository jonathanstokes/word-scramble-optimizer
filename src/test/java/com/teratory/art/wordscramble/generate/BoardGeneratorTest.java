package com.teratory.art.wordscramble.generate;

import com.teratory.art.wordscramble.model.Board;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardGeneratorTest {

    @Test
    public void testGenerateAll2x2() {

        Iterator<Board> i = new BoardGenerator().generateAll((byte)2, (byte)2);
        List<Board> boards = new ArrayList<>();
        for (; i.hasNext(); boards.add(i.next())) {}

        assertEquals((int)Math.pow(26, 4), boards.size());
        assertEquals('A', boards.get(0).getCharAt(0, 0));
        assertEquals('A', boards.get(0).getCharAt(1, 0));
        assertEquals('A', boards.get(0).getCharAt(0, 1));
        assertEquals('A', boards.get(0).getCharAt(1, 1));
        assertEquals('A', boards.get(1).getCharAt(0, 0));
        assertEquals('A', boards.get(1).getCharAt(1, 0));
        assertEquals('A', boards.get(1).getCharAt(0, 1));
        assertEquals('B', boards.get(1).getCharAt(1, 1));
        assertEquals('Z', boards.get(boards.size() - 1).getCharAt(0, 0));
        assertEquals('Z', boards.get(boards.size() - 1).getCharAt(1, 0));
        assertEquals('Z', boards.get(boards.size() - 1).getCharAt(0, 1));
        assertEquals('Z', boards.get(boards.size() - 1).getCharAt(1, 1));
    }

    @Test
    public void testGenerateAll3x2() {
        // Even a list of all the 3x2 boards could not be put in memory at once.
        // 3x2 (300 million) finishes in <5 seconds, while 3x3 (5.4 trillion) doesn't really finish; approximately
        // 2.8 years at the rate of 60k/second.  This is just generation, not processing.
        Iterator<Board> i = new BoardGenerator().generateAll((byte)3, (byte)2);
        long boardCount = 0;
        Board board = i.next();
        boardCount++;
        assertEquals('A', board.getCharAt(0, 0));
        assertEquals('A', board.getCharAt(1, 0));
        assertEquals('A', board.getCharAt(2, 0));
        assertEquals('A', board.getCharAt(0, 1));
        assertEquals('A', board.getCharAt(1, 1));
        assertEquals('A', board.getCharAt(2, 1));
        board = i.next();
        boardCount++;
        assertEquals('A', board.getCharAt(0, 0));
        assertEquals('A', board.getCharAt(1, 0));
        assertEquals('A', board.getCharAt(2, 0));
        assertEquals('A', board.getCharAt(0, 1));
        assertEquals('A', board.getCharAt(1, 1));
        assertEquals('B', board.getCharAt(2, 1));
        board = i.next();
        boardCount++;
        assertEquals('A', board.getCharAt(0, 0));
        assertEquals('A', board.getCharAt(1, 0));
        assertEquals('A', board.getCharAt(2, 0));
        assertEquals('A', board.getCharAt(0, 1));
        assertEquals('A', board.getCharAt(1, 1));
        assertEquals('C', board.getCharAt(2, 1));
        while (i.hasNext()) {
            board = i.next();
            boardCount++;
            // if (boardCount % 10000000L == 0) System.out.print(".");
            // if (boardCount % 100000000L == 0) System.out.println("");
        }
        assertEquals('Z', board.getCharAt(0, 0));
        assertEquals('Z', board.getCharAt(1, 0));
        assertEquals('Z', board.getCharAt(2, 0));
        assertEquals('Z', board.getCharAt(0, 1));
        assertEquals('Z', board.getCharAt(1, 1));
        assertEquals('Z', board.getCharAt(2, 1));
        assertEquals((long)Math.pow(26, 3 * 2), boardCount);   // 300 million
    }
    
    @Test
    public void testIncrementData() {
        BoardGenerator generator = new BoardGenerator();
        byte[] data = new byte[] {(byte)'A', (byte)'A'};
        assertEquals(true, generator.incrementData(data));
        assertEquals((byte)'A', data[0]);
        assertEquals((byte)'B', data[1]);
        assertEquals(true, generator.incrementData(data));
        assertEquals((byte)'A', data[0]);
        assertEquals((byte)'C', data[1]);
        assertEquals(true, generator.incrementData(data));
        assertEquals((byte)'A', data[0]);
        assertEquals((byte)'D', data[1]);

        data[0] = (byte)'Z';
        data[1] = (byte)'W';
        assertEquals(true, generator.incrementData(data));
        assertEquals((byte)'Z', data[0]);
        assertEquals((byte)'X', data[1]);
        assertEquals(true, generator.incrementData(data));
        assertEquals((byte)'Z', data[0]);
        assertEquals((byte)'Y', data[1]);
        assertEquals(false, generator.incrementData(data));
        assertEquals((byte)'Z', data[0]);
        assertEquals((byte)'Z', data[1]);


    }
}