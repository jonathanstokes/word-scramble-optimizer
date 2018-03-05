package com.teratory.art.wordscramble.analyze;

import com.teratory.art.wordscramble.model.Board;
import com.teratory.art.wordscramble.model.Direction;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BoardIteratorTest {

    @Test
    public void iterator() {
        Board board = new Board(new char[][] {
                { 'A', 'B', 'C' },
                { 'D', 'E', 'F' },
                { 'G', 'H', 'I' },
                { 'J', 'K', 'L' }
        });

        BoardIterator bi = new BoardIterator(board, 0, 0, Direction.EAST);
        Iterator<Character> i = bi.iterator();
        assertEquals(Character.valueOf('A'), i.next());
        assertEquals(Character.valueOf('B'), i.next());
        assertEquals(true, i.hasNext());
        assertEquals(Character.valueOf('C'), i.next());
        assertEquals(false, i.hasNext());

        bi = new BoardIterator(board, 2, 1, Direction.SOUTHWEST);
        i = bi.iterator();
        assertEquals(Character.valueOf('F'), i.next());
        assertEquals(Character.valueOf('H'), i.next());
        assertEquals(true, i.hasNext());
        assertEquals(Character.valueOf('J'), i.next());
        assertEquals(false, i.hasNext());
        // A new iterator should give us the same answers.
        i = bi.iterator();
        assertEquals(Character.valueOf('F'), i.next());
        assertEquals(Character.valueOf('H'), i.next());
        assertEquals(true, i.hasNext());
        assertEquals(Character.valueOf('J'), i.next());
        assertEquals(false, i.hasNext());

        bi = new BoardIterator(board, 1, 3, Direction.SOUTH);
        i = bi.iterator();
        assertEquals(true, i.hasNext());
        assertEquals(Character.valueOf('K'), i.next());
        assertEquals(false, i.hasNext());
    }

    @Test
    public void size() {
        Board board = new Board(new char[][] {
                { 'A', 'B', 'C' },
                { 'D', 'E', 'F' },
                { 'G', 'H', 'I' },
                { 'J', 'K', 'L' }
        });
        BoardIterator bi = new BoardIterator(board, 0, 0, Direction.EAST);
        assertEquals(3, bi.size());

        bi = new BoardIterator(board, 2, 1, Direction.SOUTHWEST);
        assertEquals(3, bi.size());
        bi.iterator().next();
        assertEquals(3, bi.size());

        bi = new BoardIterator(board, 1, 3, Direction.SOUTH);
        assertEquals(1, bi.size());
    }
}