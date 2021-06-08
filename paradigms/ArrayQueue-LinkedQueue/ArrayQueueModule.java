package queue;

import java.util.Arrays;

// Model: [queue1, queue2, queue3, ... queuen] && n = queue.size;
// Inv queue.size >= 0 && for i in range(0, queue.size): queue[i] != null;

public class ArrayQueueModule {

    // head == size == tail == 0; queue.size >= 0;

    private static int size = 0;
    private static int head = 0;
    private static Object[] queue = new Object[2];

    //Immutable: queue.size == queue.size()'; for i in range(0, queue.size): queue[i] == queue'[i];

    //Pre: element != null;
    //Post: tail = 'tail + 1 && queue[tail] = element && for i in range(0, queue.size): queue[i] = queue'[i];

    public static void enqueue(Object element) {
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
        } else if ((head + size) % queue.length == queue.length) {
            queue[0] = element;
        } else {
            queue[(head + size) % queue.length] = element;
        }
        size++;
    }

    //Pred: head >= 0;
    //Post: return queue[head] && Immutable;

    public static Object element() {
        assert size > 0;
        return queue[head];
    }

    //Post: queue.size > 0;
    //Pred: return queue[head]; head = head' + 1 && for i in range(0, queue.size): queue[i] = queue'[i];

    public static Object dequeue() {
        assert size > 0;
        Object cur = element();
        queue[head] = null;
        head = (head + 1) % queue.length;
        size--;
        return cur;
    }

    //Pred: True;
    //Post: return queue.size && Immutable;

    public static int size() {
        return size;
    }

    //Pre: True;
    //Post: return queue.size == 0 && Immutable;

    public static boolean isEmpty() {
        return size == 0;
    }

    //Pre: True;
    //Post: queue.size == 0 && head == 0 && tail == 0 && size == 0;

    public static void clear() {
        queue = new Object[2];
        size = 0;
        head = 0;
    }

    //Pre: element != null;
    //Post: queue[0] = elemend for i in range(1 queue.length): queue[i] = queue[i - 1];

    public static void push(Object element) {
        assert element != null;
        if (size == queue.length) {
            int i = 0;
            Object[] queueCopy = new Object[queue.length];
            do {
                queueCopy[i] = queue[(head + i) % queue.length];
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

    //Pre: queue.size > 0;
    //Post: return queue[-1];

    public static Object peek() {
        assert size > 0;
        if ((head + size) % queue.length == 0) {
            return queue[queue.length - 1];
        } else {
            return queue[(head + size) % queue.length - 1];
        }
    }

    //Pre: queue.size > 0;
    //Post: return queue.size, queue.size = queue.size - 1 && for i in range(1, n): queue[i] = queue'[i]

    public static Object remove() {
        assert  size > 0;
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
/*
enqueue – добавить элемент в очередь;
element – первый элемент в очереди;
dequeue – удалить и вернуть первый элемент в очереди;
size – текущий размер очереди;
isEmpty – является ли очередь пустой;
clear – удалить все элементы из очереди;
push - добавить элемент в начало очереди;
peek - вернуть последний элемент очереди;
remove - вернуть и удалить последний элемент очереди;
*/