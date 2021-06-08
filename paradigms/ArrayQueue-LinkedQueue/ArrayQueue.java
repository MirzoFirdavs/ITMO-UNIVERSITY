package queue;

import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {

    private int head = 0;
    private Object[] queue = new Object[2];

    public void clearImpl() {
        queue = new Object[2];
        head = 0;
        size = 0;
    }

    public Object elementImpl() {
        assert super.size > 0;
        return queue[head];
    }

    public void enqueueImpl(Object element) {
        assert element != null;
        if (size == queue.length) {
            int i = 0;
            Object[] queueCopy = new Object[queue.length];
            do {
                queueCopy[i] = queue[(head + i) % queue.length];
                i++;
            } while (i < queue.length);
            queue = Arrays.copyOf(queueCopy, queue.length * 2);
            queue[size()] = element;
            head = 0;
        } else {
            queue[(head + size) % queue.length] = element;
        }
    }

    public Object dequeueImpl() {
        assert size > 0;
        Object cur = element();
        queue[head] = null;
        head = (head + 1) % queue.length;
        return cur;
    }

    public void push(Object element) {
        assert element != null;
        if (size == queue.length) {
            int i = 0;
            Object[] queueCopy = new Object[queue.length];
            do {
                queueCopy[i] = queue[(this.head + i) % queue.length];
                i++;
            } while (i < queue.length);
            queue = Arrays.copyOf(queueCopy, queue.length * 2);
            head = queue.length;
            queue[--head] = element;
        } else if (head == 0) {
            head = queue.length;
            queue[--head] = element;
        } else {
            queue[--head] = element;
        }
        size++;
    }

    public Object peek() {
        assert size > 0;
        if ((head + size) % queue.length == 0) {
            return queue[queue.length - 1];
        } else {
            return queue[(head + size) % queue.length - 1];
        }
    }

    public Object remove() {
        assert size > 0;
        Object cur = peek();
        if ((head + size) % queue.length == 0) {
            queue[queue.length - 1] = null;
        } else {
            queue[(head + size) % queue.length - 1] = null;
        }
        size--;
        return cur;
    }
}
