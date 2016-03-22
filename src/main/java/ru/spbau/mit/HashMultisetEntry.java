package ru.spbau.mit;

import java.util.Map;

public class HashMultisetEntry<E> implements Multiset.Entry<E> {

    public HashMultisetEntry(Map.Entry<E, Integer> entry) {
        this.element = entry.getKey();
        this.count = entry.getValue();
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
        return --count != 0;
    }

    private E element;
    private int count;

}