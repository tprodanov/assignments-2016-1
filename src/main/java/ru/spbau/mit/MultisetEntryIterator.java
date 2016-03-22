package ru.spbau.mit;

import java.util.Iterator;
import java.util.Map;

public class MultisetEntryIterator<E> implements Iterator<Multiset.Entry<E>> {

    public MultisetEntryIterator(Iterator<Map.Entry<E, Integer>> entrySetIterator) {
        this.entrySetIterator = entrySetIterator;
        current = new HashMultisetEntry<>(entrySetIterator.next());
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Multiset.Entry<E> next() {
        Multiset.Entry<E> temp = current;
        if (entrySetIterator.hasNext()) {
            current = new HashMultisetEntry<>(entrySetIterator.next());
        } else {
            current = null;
        }
        return temp;
    }

    @Override
    public void remove() {
        if (!current.decrease()) {
            next();
        }
    }

    private HashMultisetEntry<E> current;
    private Iterator<Map.Entry<E, Integer>> entrySetIterator;

}
