package ru.spbau.mit;

public class StringSetImpl implements StringSet {

    public StringSetImpl() {
        // EMPTY
    }

    @Override
    public boolean add(String element) {
        return addFromIndex(element, 0);
    }

    @Override
    public boolean contains(String element) {
        return containsFromIndex(element, 0);
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
        return startsWithPrefixFromIndex(prefix, 0);
    }

    private boolean addFromIndex(String element, int index) {
        if (index == element.length()) {
            if (endState) {
                return false;
            } else {
                endState = true;
                ++size;
                return true;
            }
        }

        if (transitions[element.charAt(index) - 'A'] == null) {
            transitions[element.charAt(index) - 'A'] = new StringSetImpl();
        }
        if (transitions[element.charAt(index) - 'A'].addFromIndex(element, index + 1)) {
            ++size;
            return true;
        }
        return false;
    }

    private boolean containsFromIndex(String element, int index) {
        if (index == element.length()) {
            return endState;
        }
        if (transitions[element.charAt(index) - 'A'] == null) {
            return false;
        }
        return transitions[element.charAt(index) - 'A'].containsFromIndex(element, index + 1);
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

        if (transitions[element.charAt(index) - 'A'] == null) {
            return false;
        }
        if (transitions[element.charAt(index) - 'A'].removeFromIndex(element, index + 1)) {
            if (transitions[element.charAt(index) - 'A'].size() == 0) {
                transitions[element.charAt(index) - 'A'] = null;
            }
            --size;
            return true;
        }
        return false;
    }

    private int startsWithPrefixFromIndex(String prefix, int index) {
        if (index == prefix.length()) {
            return size;
        }
        if (transitions[prefix.charAt(index) - 'A'] == null) {
            return 0;
        }
        return transitions[prefix.charAt(index) - 'A'].startsWithPrefixFromIndex(prefix, index + 1);
    }

    private StringSetImpl[] transitions = new StringSetImpl['z' - 'A' + 1];
    private boolean endState = false;
    private int size = 0;

}
