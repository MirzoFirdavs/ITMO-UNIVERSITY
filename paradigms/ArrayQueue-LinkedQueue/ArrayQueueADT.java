package queue;

import java.util.Arrays;

// Model: [queue1, queue2, queue3, ... queuen] && n = queue.size;
// Inv queue.size >= 0 && for i in range(0, queue.size): queue[i] != null;

public class ArrayQueueADT {

    // head == size == tail == 0; queue.size >= 0;

    private int size = 0;
    private int head = 0;
    private Object[] queue = new Object[2];

    //Immutable: queue.size == queue.size()'; for i in range(0, queue.size): queue[i] == queue'[i];

    //Pre: element != null;
    //Post: tail = 'tail + 1 && queue[tail] = element && for i in range(0, queue.size): queue[i] = queue'[i];

    public static void enqueue(ArrayQueueADT elements, Object element) {
        assert element != null;
        if (elements.size == elements.queue.length) {
            int i = 0;
            Object[] queueCopy = new Object[elements.queue.length];
            do {
                queueCopy[i] = elements.queue[(elements.head + i) % elements.queue.length];
                i++;
            } while (i < elements.queue.length);
            elements.queue = Arrays.copyOf(queueCopy, elements.queue.length * 2);
            elements.queue[size(elements)] = element;
            elements.head = 0;
        } else if ((elements.head + elements.size) % elements.queue.length == elements.queue.length) {
            elements.queue[0] = element;
        } else {
            elements.queue[(elements.head + elements.size) % elements.queue.length] = element;
        }
        elements.size++;
    }

    //Pred: head >= 0;
    //Post: return queue[head] && Immutable;

    public static Object element(ArrayQueueADT elements) {
        assert elements.size > 0;
        return elements.queue[elements.head];
    }

    //Post: queue.size > 0;
    //Pred: return queue[head]; head = head' + 1 && for i in range(0, queue.size): queue[i] = queue'[i];

    public static Object dequeue(ArrayQueueADT elements) {
        assert elements.size > 0;
        Object cur = element(elements);
        elements.queue[elements.head] = null;
        elements.head = (elements.head + 1) % elements.queue.length;
        elements.size--;
        return cur;
    }

    //Pred: True;
    //Post: return queue.size && Immutable;

    public static int size(ArrayQueueADT elements) {
        return elements.size;
    }

    //Pre: True;
    //Post: return queue.size == 0 && Immutable;

    public static boolean isEmpty(ArrayQueueADT elements) {
        return elements.size == 0;
    }

    //Pre: True;
    //Post: queue.size == 0 && head == 0 && tail == 0 && size == 0;

    public static void clear(ArrayQueueADT elements) {
        elements.queue = new Object[2];
        elements.size = 0;
        elements.head = 0;
    }

    //Pre: element != null;
    //Post: queue[0] = elemend for i in range(1 queue.length): queue[i] = queue[i - 1];

    public static void push(ArrayQueueADT elements, Object element) {
        assert element != null;
        if (elements.size == elements.queue.length) {
            int i = 0;
            Object[] queueCopy = new Object[elements.queue.length];
            do {
                queueCopy[i] = elements.queue[(elements.head + i) % elements.queue.length];
                i++;
            } while (i < elements.queue.length);
            elements.queue = Arrays.copyOf(queueCopy, elements.queue.length * 2);
            elements.head = elements.queue.length;
            elements.queue[--elements.head] = element;
        } else if (elements.head == 0) {
            elements.head = elements.queue.length;
            elements.queue[--elements.head] = element;
        } else {
            elements.queue[--elements.head] = element;
        }
        elements.size++;
    }

    //Pre: queue.size > 0;
    //Post: return queue[-1];

    public static Object peek(ArrayQueueADT elements) {
        assert elements.size > 0;
        if ((elements.head + elements.size) % elements.queue.length == 0) {
            return elements.queue[elements.queue.length - 1];
        } else {
            return elements.queue[(elements.head + elements.size) % elements.queue.length - 1];
        }
    }

    //Pre: queue.size > 0;
    //Post: return queue.size, queue.size = queue.size - 1 && for i in range(1, n): queue[i] = queue'[i]

    public static Object remove(ArrayQueueADT elements) {
        assert  elements.size > 0;
        Object cur = peek(elements);
        if ((elements.head + elements.size) % elements.queue.length == 0) {
            elements.queue[elements.queue.length - 1] = null;
        } else {
            elements.queue[(elements.head + elements.size) % elements.queue.length - 1] = null;
        }
        elements.size--;
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