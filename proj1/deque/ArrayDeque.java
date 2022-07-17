package deque;

import java.util.Iterator;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class ArrayDeque<T> implements Deque<T> {
    private static final int DEFAULT_CAPACITY = 8;

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayDeque(int capacity) {
        size = 0;
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        items = (T[]) new Object[capacity];
        nextFirst = capacity / 2;
        nextLast = nextFirst + 1;
    }


    private int convertIdx(int idx) {
        return (idx + items.length) % items.length;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int newNextFirst = capacity / 2;
        int newNextLast = newNextFirst + 1;
        for (var ele : this) {
            newItems[newNextLast] = ele;
            newNextLast = (newNextLast + 1) % capacity;
        }
        nextFirst = newNextFirst;
        nextLast = newNextLast;
        items = newItems;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = convertIdx(nextFirst - 1);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = convertIdx(nextLast + 1);
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size < items.length / 4 && size > 4) {
            resize(items.length / 4);
        }
        int idx = convertIdx(nextFirst + 1);
        T val = items[idx];
        items[idx] = null;
        nextFirst = idx;
        size--;
        return val;
    }

    @Override
    public T removeLast() {
        if (size < items.length / 4 && size > 4) {
            resize(items.length / 4);
        }

        int idx = convertIdx(nextLast - 1);
        T val = items[idx];
        items[idx] = null;
        nextLast = idx;
        size--;
        return val;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return items[convertIdx(nextFirst + 1 + index)];
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T val = items[convertIdx(nextFirst + 1 + index)];
            index++;
            return val;
        }
    }
}