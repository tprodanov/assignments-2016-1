package ru.spbau.mit;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMultisetEntrySet<E> extends AbstractSet<Multiset.Entry<E>> {

    public HashMultisetEntrySet(Set<Map.Entry<E, Integer>> entrySet) {
        this.entrySet = entrySet;
    }

    @Override
    public Iterator<Multiset.Entry<E>> iterator() {
        return new MultisetEntryIterator<>(entrySet.iterator());
    }

    @Override
    public int size() {
        return entrySet.size();
    }

    private Set<Map.Entry<E, Integer>> entrySet;

}
