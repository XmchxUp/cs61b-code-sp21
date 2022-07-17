package deque;

import com.google.common.base.Objects;

import java.util.Iterator;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class LinkedListDeque<T> implements Deque<T> {
    private static class Node<T> {
        public T value;
        public Node<T> next;
        public Node<T> prev;

        public Node() {
            next = this;
            prev = this;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<T> node = (Node<T>) o;
            return Objects.equal(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }

    private int size;
    private Node<T> sentinel;


    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<>();
    }


    @Override
    public void addFirst(T item) {
        var head = sentinel.next;
        sentinel.next = new Node<>(item, sentinel, head);
        head.prev = sentinel.next;
        size++;
    }

    @Override
    public void addLast(T item) {
        var last = sentinel.prev;
        sentinel.prev = new Node<>(item, last, sentinel);
        last.next = sentinel.prev;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) return null;
        Node<T> removedNode = sentinel.next;
        sentinel.next = removedNode.next;
        sentinel.next.prev = sentinel;
        size--;
        return removedNode.value;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        var removedNode = sentinel.prev;
        sentinel.prev = removedNode.prev;
        removedNode.prev.next = sentinel;
        size--;
        return removedNode.value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        var cur = sentinel;
        while (index >= 0) {
            cur = cur.next;
            index--;
        }
        return cur.value;
    }

    private T helper(Node<T> node, int index) {
        if (index == 0) return node.value;
        return helper(node.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) return null;
        return helper(sentinel.next, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListDeque<T> that = (LinkedListDeque<T>) o;
        if (size != that.size) return false;

        Node<T> x = this.sentinel.next;
        Node<T> y = that.sentinel.next;
        for (int i = 0; i < size; i++) {
            if (!x.equals(y)) return false;
            x = x.next;
            y = y.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(size, sentinel);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }


    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> cur = sentinel.next;

        @Override
        public boolean hasNext() {
            return cur != sentinel;
        }

        @Override
        public T next() {
            T val = cur.value;
            cur = cur.next;
            return val;
        }
    }
}