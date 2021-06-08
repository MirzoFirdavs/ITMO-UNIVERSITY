package queue;

public class LinkedQueue extends AbstractQueue {
    private static class Node {
        private Node next;
        private final Object value;


        public Node(final Node next, final Object value) {
            this.next = next;
            this.value = value;
        }
    }

    private Node head;
    private Node tail;

    protected void enqueueImpl(Object element) {
        Node node = new Node(null, element);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    protected Object dequeueImpl() {
        assert size > 0;
        Object cur = head.value;
        head = head.next;
        return cur;
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void clearImpl() {
        head = null;
        tail = null;
    }
}

/*
enqueue – добавить элемент в очередь;
element – первый элемент в очереди;
dequeue – удалить и вернуть первый элемент в очереди;
size – текущий размер очереди;
isEmpty – является ли очередь пустой;
clear – удалить все элементы из очереди;
contains - проверить содержится ли элемент в очереди;
removeFirstOccurrence -  удаляет первое вхождение элемента в очередь если он есть;
*/