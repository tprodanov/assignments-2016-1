package ru.spbau.mit;

import java.util.Iterator;
import java.util.Map;

public class MultisetEntryIterator<E> implements Iterator<Multiset.Entry<E>> {

    public MultisetEntryIterator(Iterator<Map.Entry<E, Integer>> entrySetIterator,
                                 HashMultiset<E> parent) {
        this.parent = parent;
        this.entrySetIterator = entrySetIterator;
        current = new HashMultisetEntry<>(entrySetIterator.next(), parent);
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Multiset.Entry<E> next() {
        Multiset.Entry<E> temp = current;
        if (entrySetIterator.hasNext()) {
            current = new HashMultisetEntry<>(entrySetIterator.next(), parent);
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

    private HashMultiset<E> parent;
    private HashMultisetEntry<E> current;
    private Iterator<Map.Entry<E, Integer>> entrySetIterator;

}
