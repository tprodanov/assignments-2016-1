package ru.spbau.mit;

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
        if (contains(element)) {
            return false;
        }
        addFromIndex(element, 0);
        return true;
    }

    @Override
    public boolean contains(String element) {
        StringSetImpl node = climbTo(element, 0);
        return node != null && node.endState;
    }

    @Override
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
        removeFromIndex(element, 0);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        StringSetImpl node = climbTo(prefix, 0);
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    private static int getIndex(char letter) {
        return letter - SMALLEST_LETTER;
    }

    private StringSetImpl nextNode(String word, int index) {
        return transitions[getIndex(word.charAt(index))];
    }

    private StringSetImpl climbTo(String word, int indexFrom) {
        if (indexFrom == word.length()) {
            return this;
        }
        if (nextNode(word, indexFrom) == null) {
            return null;
        }

        return nextNode(word, indexFrom).climbTo(word, indexFrom + 1);
    }

    private void addFromIndex(String element, int index) {
        ++size;
        if (index == element.length()) {
            endState = true;
        } else {
            if (nextNode(element, index) == null) {
                transitions[getIndex(element.charAt(index))] = new StringSetImpl();
            }
            nextNode(element, index).addFromIndex(element, index + 1);
        }
    }

    private void removeFromIndex(String element, int index) {
        --size;
        if (index == element.length()) {
            endState = false;
        } else {
            nextNode(element, index).removeFromIndex(element, index + 1);
            if (nextNode(element, index).size == 0) {
                transitions[getIndex(element.charAt(index))] = null;
            }
        }
    }

}
