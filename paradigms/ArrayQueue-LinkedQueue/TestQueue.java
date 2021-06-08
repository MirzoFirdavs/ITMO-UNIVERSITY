package queue;

public class TestQueue {
    public static void fill(LinkedQueue queue) {
        for (int i = 0; i < 15; ++i) {
            queue.enqueue(i);
        }
    }


    public static void dump(LinkedQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element() + " " + queue.dequeue());
        }
        for (int i = 0; i < 13; ++i) {
            System.out.println(queue.dequeue());
        }
    }

    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        fill(queue);
        dump(queue);
        queue.clear();
        System.out.println(queue.isEmpty());
        System.out.println(queue.isEmpty());
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