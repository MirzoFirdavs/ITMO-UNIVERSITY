package queue;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void clearImpl();

    protected abstract Object elementImpl();

    protected abstract Object dequeueImpl();

    protected abstract void enqueueImpl(Object element);

    public boolean modQueue(Object element, boolean flag) {
        assert element != null;
        boolean ans = false;
        int len = size();
        for (int i = 0; i < len; ++i) {
            Object cur = element();
            if (!ans && element.equals(cur) && flag) {
                ans = true;
                dequeue();
                continue;
            }
            if (!ans && element.equals(cur) && !flag) {
                ans = true;
            }
            dequeue();
            enqueue(cur);
        }
        return ans;
    }

    public boolean contains(Object element) {
        return modQueue(element, false);
    }

    public boolean removeFirstOccurrence(Object element) {
        return modQueue(element, true);
    }

    public void enqueue(Object element) {
        enqueueImpl(element);
        size++;
    }

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Object dequeue() {
        Object cur = dequeueImpl();
        size--;
        return cur;
    }

    public void clear() {
        size = 0;
        clearImpl();
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