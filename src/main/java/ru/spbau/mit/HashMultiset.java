package ru.spbau.mit;

import java.util.*;

public class HashMultiset<E> extends AbstractSet<E> implements Multiset<E> {

    public HashMultiset() {
        // EMPTY
    }

    @Override
    public boolean add(E element) {
        if (elementCount.containsKey(element)) {
            elementCount.put(element, elementCount.get(element) + 1);
        } else {
            elementCount.put(element, 1);
        }
        ++size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }
        E element = (E) o;

        int count = elementCount.get(element) - 1;
        if (count == 0) {
            elementCount.remove(element);
        } else {
            elementCount.put(element, count);
        }
        return true;
    }

    @Override
    public boolean contains(Object element) {
        return elementCount.containsKey(element);
    }

    @Override
    public int count(Object element) {
        Integer count = elementCount.get(element);
        if (count == null) {
            return 0;
        } else {
            return count;
        }
    }

    @Override
    public Set<E> elementSet() {
        return elementCount.keySet();
    }

    @Override
    public Set<? extends Entry<E>> entrySet() {
        return new HashMultisetEntrySet<>(elementCount.entrySet(), this);
    }

    @Override
    public Iterator<E> iterator() {
        return new HashMultisetIterator<>(entrySet().iterator());
    }

    @Override
    public int size() {
        return size;
    }

    private LinkedHashMap<E, Integer> elementCount = new LinkedHashMap<>();
    private int size = 0;

}
