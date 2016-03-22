package ru.spbau.mit;

import java.util.Iterator;

public class HashMultisetIterator<E> implements Iterator<E> {

    public HashMultisetIterator(Iterator<? extends Multiset.Entry<E>> multiSetIterator) {
        this.multiSetIterator = multiSetIterator;
    }

    @Override
    public boolean hasNext() {
        return multiSetIterator.hasNext();
    }

    @Override
    public E next() {
        if (currentCount == 0) {
            Multiset.Entry<E> currentEntry = multiSetIterator.next();
            currentCount = currentEntry.getCount();
            currentElement = currentEntry.getElement();
        }
        --currentCount;
        return currentElement;
    }

    @Override
    public void remove() {
        multiSetIterator.remove();
    }

    private Iterator<? extends Multiset.Entry<E>> multiSetIterator;
    private int currentCount = 0;
    private E currentElement;

}
