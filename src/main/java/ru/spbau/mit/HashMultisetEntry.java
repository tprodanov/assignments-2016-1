package ru.spbau.mit;

import java.util.Map;

public class HashMultisetEntry<E> implements Multiset.Entry<E> {

    public HashMultisetEntry(Map.Entry<E, Integer> entry, HashMultiset<E> parent) {
        this.parent = parent;
        element = entry.getKey();
        count = entry.getValue();
    }

    @Override
    public E getElement() {
        return element;
    }

    @Override
    public int getCount() {
        return count;
    }

    public boolean decrease() {
        parent.remove(element);
        return --count != 0;
    }

    private E element;
    private int count;
    private HashMultiset<E> parent;

}