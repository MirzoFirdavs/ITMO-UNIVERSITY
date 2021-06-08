package queue;

// Model: [queue1, queue2, queue3, ... queuen] && n = queue.size;
// Inv: queue.size >= 0 && for i in range(0, queue.size): queue[i] != null;
//Immutable: queue.size == queue.size()'; for i in range(0, queue.size): queue[i] == queue'[i];

public interface Queue {

    //Pre: element != null;
    //Post: tail = 'tail + 1 && queue[tail] = element && for i in range(0, queue.size): queue[i] = queue'[i];
    void enqueue(Object element);

    //Post: queue.size > 0;
    //Pred: return queue[head]; head = head' + 1 && for i in range(0, queue.size): queue[i] = queue'[i];
    Object dequeue();

    //Pred: size > 0;
    //Post: return queue[head] && Immutable;
    Object element();

    //Pre: True;
    //Post: return queue.size == 0 && Immutable;
    boolean isEmpty();

    //Pre: True;
    //Post: queue.size == 0 && head == 0 && tail == 0 && size == 0;
    void clear();

    //Pred: True;
    //Post: return queue.size && Immutable;
    int size();

    //Pred: element != null && Immutable;
    //Post: R = queue.exists(element); return R;
    boolean contains(Object element);

    //Pred: element != null;
    //Post: R = queue.exists(element) for all i (1 ... n): if queue[i] == element: remove(min(i)) return R;
    boolean removeFirstOccurrence(Object element);
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