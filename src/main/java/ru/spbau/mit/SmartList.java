package ru.spbau.mit;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmartList<E> extends AbstractList<E> implements List<E> {

    private static final int ARRAY_SIZE = 5;
    private int size;
    private Object data;

    public SmartList() {
    }

    public SmartList(Collection<E> collection) {
        size = collection.size();
        if (size == 1) {
            data = collection.iterator().next();
        } else if (size <= ARRAY_SIZE) {
            Object[] array = new Object[ARRAY_SIZE];
            int i = 0;
            for (E e : collection) {
                array[i] = e;
                ++i;
            }
            data = array;
        } else {
            data = new ArrayList<>(collection);
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            return (E) data;
        } else if (size <= ARRAY_SIZE) {
            return (E) ((Object[]) data)[index];
        } else {
            return ((List<E>) data).get(index);
        }
    }

    @Override
    public E set(int index, E element) {
        E previous = get(index);

        if (size == 1) {
            data = element;
        } else if (size <= ARRAY_SIZE) {
            ((Object[]) data)[index] = element;
        } else {
            ((List<E>) data).set(index, element);
        }

        return previous;
    }

    @Override
    public boolean add(E e) {
        if (size == 0) {
            data = e;

        } else if (size == 1) {
            Object previous = data;
            Object[] array = new Object[ARRAY_SIZE];
            array[0] = previous;
            array[1] = e;
            data = array;

        } else if (size < ARRAY_SIZE) {
            ((Object[]) data)[size] = e;

        } else if (size == ARRAY_SIZE) {
            List<E> list = new ArrayList<>();
            for (Object element : (Object[]) data) {
                list.add((E) element);
            }
            list.add(e);
            data = list;

        } else {
            ((List<E>) data).add(e);
        }

        ++size;
        return true;
    }

    @Override
    public void add(int index, E e) {
        if (index == size) {
            add(e);
            return;
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 0) {
            data = e;

        } else if (size == 1) {
            Object[] array = new Object[2];
            array[index] = e;
            array[1 - index] = data;
            data = array;

        } else if (size < ARRAY_SIZE) {
            Object[] array = (Object[]) data;
            for (int i = size; i > index; --i) {
                array[i] = array[i - 1];
            }
            array[index] = e;

        } else if (size == ARRAY_SIZE) {
            List<E> list = new ArrayList<>();
            int i = 0;
            for (Object element : (Object[]) data) {
                if (i == index) {
                    list.add(e);
                }
                ++i;
                list.add((E) element);
            }
            if (i == index) {
                list.add(e);
            }
            list.add(e);
            data = list;

        } else {
            ((List<E>) data).add(index, e);
        }

        ++size;
    }

    @Override
    public E remove(int index) {
        E previous = get(index);

        if (size == 1) {
            data = null;

        } else if (size == 2) {
            data = ((Object[]) data)[1 - index];

        } else if (size <= ARRAY_SIZE) {
            Object[] array = (Object[]) data;
            for (int i = index; i < size - 1; ++i) {
                array[i] = array[i + 1];
            }

        } else if (size == ARRAY_SIZE + 1) {
            Object[] array = new Object[ARRAY_SIZE];
            List<E> list = (List<E>) data;

            for (int i = 0; i < index; ++i) {
                array[i] = list.get(i);
            }
            for (int i = index + 1; i < size; ++i) {
                array[i - 1] = list.get(i);
            }
            data = array;

        } else {
            ((List<E>) data).remove(index);
        }

        --size;
        return previous;
    }

    @Override
    public int size() {
        return size;
    }
}
