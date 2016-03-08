package ru.spbau.mit;

import java.util.Vector;

public class StringSetImpl implements StringSet {

    private static final char SMALLEST_LETTER = 'A';
    private static final char LARGEST_LETTER = 'z';

    private StringSetImpl[] transitions = new StringSetImpl[LARGEST_LETTER - SMALLEST_LETTER + 1];
    private boolean endState = false;
    private int size = 0;

    public StringSetImpl() {
        // EMPTY
    }

    @Override
    public boolean add(String element) {
        Vector<StringSetImpl> path = pathToWord(element, 0, true);
        if (path.elementAt(0).endState) {
            return false;
        } else {
            path.elementAt(0).endState = true;
            for (StringSetImpl node : path) {
                ++node.size;
            }
            return true;
        }
    }

    @Override
    public boolean contains(String element) {
        Vector<StringSetImpl> path = pathToWord(element, 0, false);
        return path != null && path.elementAt(0).endState;
    }

    @Override
    public boolean remove(String element) {
        return removeFromIndex(element, 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        Vector<StringSetImpl> path = pathToWord(prefix, 0, false);
        if (path == null) {
            return 0;
        } else {
            return path.elementAt(0).size;
        }
    }

    private static int getIndex(char letter) {
        return letter - SMALLEST_LETTER;
    }

    private Vector<StringSetImpl> pathToWord(String word, int index, boolean insertNodes) {
        if (index == word.length()) {
            Vector<StringSetImpl> path = new Vector<>();
            path.add(this);
            return path;
        }

        if (transitions[getIndex(word.charAt(index))] == null) {
            if (insertNodes) {
                transitions[getIndex(word.charAt(index))] = new StringSetImpl();
            } else {
                return null;
            }
        }

        Vector<StringSetImpl> path = transitions[getIndex(word.charAt(index))]
                .pathToWord(word, index + 1, insertNodes);
        if (path != null) {
            path.add(this);
        }

        return path;
    }

    private boolean removeFromIndex(String element, int index) {
        if (index == element.length()) {
            if (endState) {
                endState = false;
                --size;
                return true;
            } else {
                return false;
            }
        }

        if (transitions[getIndex(element.charAt(index))] == null) {
            return false;
        }
        if (transitions[getIndex(element.charAt(index))].removeFromIndex(element, index + 1)) {
            if (transitions[getIndex(element.charAt(index))].size() == 0) {
                transitions[getIndex(element.charAt(index))] = null;
            }
            --size;
            return true;
        }
        return false;
    }

}
