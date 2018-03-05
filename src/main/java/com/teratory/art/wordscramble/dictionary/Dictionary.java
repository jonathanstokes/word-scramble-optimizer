package com.teratory.art.wordscramble.dictionary;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private Map<String,Node> nodeByWord = new HashMap<>();

    private Node fullNodeMap = new Node();
    private int longestWordLength = 0;
    private final int maximumAllowedWordLength;

    /** Creates an empty dictionary. */
    public Dictionary(int maximumAllowedWordLength) {
        this.maximumAllowedWordLength = maximumAllowedWordLength;
    }

    public int getLongestWordLength() {
        return longestWordLength;
    }

    public int getWordCount() {
        return nodeByWord.size();
    }

    protected boolean isWord(String testWord) {
        return nodeByWord.containsKey(testWord.toLowerCase());
    }

    public WordResult getWordResult(String testWord) {
        testWord = testWord.toLowerCase();
        Node node = nodeByWord.get(testWord);
        if (node != null) return new WordResult(node.getWordPoints(), node.isPrefix());
        else {
            node = fullNodeMap;
            for (int i = 0, len = testWord.length(); i < len; i++) {
                node = node.getNextLetter(testWord.charAt(i));
                if (node == null) return new WordResult(0.0, false);
            }
            return new WordResult(node.getWordPoints(), node.isPrefix());
        }
    }

    public void ingest(WordList words, double wordPoints) {
        while (words.hasNext()) {
            ingestWord(words.next(), wordPoints);
        }
    }

    /** Process a single, legal word. */
    protected void ingestWord(String word, double wordPoints) {
        if (word.length() <= maximumAllowedWordLength) {
            Node nextNode = fullNodeMap;
            for (int i = 0, len = word.length(); i < len; i++) {
                nextNode = nextNode.getOrAddNextLetter(word.charAt(i), (i == len - 1 ? wordPoints : 0.0));
            }
            nodeByWord.put(word, nextNode);
            longestWordLength = Math.max(longestWordLength, word.length());
        }
    }

    protected static class Node {
        /** The string of characters up to and including this node. */
        private String fragment;
        /** The individual letter for this node. */
        private char letter;
        /** The valid of the word completed by this node, or 0 if this node does not complete a legal word. */
        private double wordPoints;

        /** A map of letters that follow this one to eventually complete a legal word. */
        private Map<Character,Node> nextLetters = new HashMap<>();

        /** Creates (only) the root node. */
        private Node() {
            this.fragment = "";
            this.wordPoints = 0.0;
        }

        /** Creates a normal node. */
        public Node(String fragment, double wordPoints) {
            this.fragment = fragment;
            this.letter = this.fragment.charAt(this.fragment.length() - 1);
            this.wordPoints = wordPoints;
        }

        public char getLetter() {
            return letter;
        }

        public boolean isWord() {
            return wordPoints != 0.0;
        }

        /** Sets whether this node represents a word by assigning it points. */
        public void setWord(double wordPoints) {
            this.wordPoints = wordPoints;
        }

        public Node getOrAddNextLetter(char nextLetter, double wordPoints) {
            Node nextNode = nextLetters.get(nextLetter);
            if (nextNode != null) {
                if (wordPoints != 0.0) nextNode.setWord(wordPoints);
            }
            else {
                nextNode = new Node(fragment + nextLetter, wordPoints);
                nextLetters.put(nextLetter, nextNode);
            }
            return nextNode;
        }

        public boolean isPrefix() {
            return !nextLetters.isEmpty();
        }

        public Node getNextLetter(char nextLetter) {
            return nextLetters.get(nextLetter);
        }

        public double getWordPoints() {
            return wordPoints;
        }
    }

    public static class WordResult {

        private boolean isPrefix;

        private double wordPoints;

        /**
         *
         * @param wordPoints the points for this word, or 0 if this is not a word.
         */
        public WordResult(double wordPoints, boolean isPrefix) {
            this.isPrefix = isPrefix;
            this.wordPoints = wordPoints;
        }

        public boolean isWord() {
            return wordPoints != 0.0;
        }

        public boolean isPrefix() {
            return isPrefix;
        }

        public double getWordPoints() {
            return wordPoints;
        }
    }
}
