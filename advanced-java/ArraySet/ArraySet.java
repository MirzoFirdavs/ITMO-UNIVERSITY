package info.kgeorgiy.ja.zaynidinov.arrayset;

import java.util.*;

@SuppressWarnings("unused")
public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {
    private final ReverseList<E> container;
    private final Comparator<? super E> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    // TODO: why super?
    public ArraySet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySet(Collection<? extends E> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this.comparator = comparator;
        TreeSet<E> set = new TreeSet<>(comparator);
        set.addAll(collection);
        container = new ReverseList<>(set);
    }

    public ArraySet<E> newEmptyArraySet() {
        return new ArraySet<>();
    }

    private int binarySearch(E value) {
        return Collections.binarySearch(container, value, this::compare);
    }

    public int getIndex(E value, boolean inclusive, boolean lower) {
        int index = binarySearch(value);
        if (index >= 0) {
            if (!inclusive) { // TODO
                if (lower) {
                    index--;
                } else {
                    index++;
                }
            }
        } else {
            index = -index - 1;
            if (lower) {
                index--;
            }
        }
        return index;
    }

    @SuppressWarnings("unchecked")
    public int compare(E value1, E value2) {
        if (comparator == null) {
            return ((Comparable<E>) value1).compareTo(value2);
        } else {
            return comparator.compare(value1, value2);
        }
    }

    public E getValidValue(int index) {
        if (0 <= index && index < size()) {
            return container.get(index);
        } else {
            return null;
        }
    }

    @Override
    public E lower(E value) {
        /* Возвращает наибольший элемент в этом наборе строго меньше заданного элемента, или null если такого элемента нет. */
        return getValidValue(getIndex(value, false, true));
    }

    @Override
    public E floor(E value) {
        /* Возвращает наибольший элемент в этом наборе, меньший или равный данному элементу, или null если такого элемента нет. */
        return getValidValue(getIndex(value, true, true));
    }

    @Override
    public E ceiling(E value) {
        /* Возвращает наименьший элемент в этом наборе, больший или равный данному элементу, или null если такого элемента нет. */
        return getValidValue(getIndex(value, true, false));
    }

    @Override
    public E higher(E value) {
        /* Возвращает наименьший элемент в этом наборе, строго больший заданного элемента, или null если такого элемента нет. */
        return getValidValue(getIndex(value, false, false));
    }

    @Override
    public Iterator<E> iterator() {
        /* Возвращает итератор по элементам этого набора в порядке возрастания. */
        return container.iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        /*
        Возвращает представление элементов, содержащихся в этом наборе, в обратном порядке.
        Нисходящий набор поддерживается этим набором, поэтому изменения в наборе отражаются в нисходящем наборе, и наоборот.
        Если какой-либо набор изменяется во время выполнения итерации по любому набору
        (за исключением собственной remove операции итератора), результаты итерации не определены.
         */
        return new ArraySet<>(new ReverseList<>(container, true), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<E> descendingIterator() {
        /* Возвращает итератор по элементам этого набора в порядке убывания. */
        return descendingSet().iterator();
    }

    public NavigableSet<E> generateSubSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        int fromIndex = getIndex(fromElement, fromInclusive, false);
        int toIndex = getIndex(toElement, toInclusive, true);

        if (fromIndex > toIndex) {
            return new ArraySet<>(comparator);
        } else {
            return new ArraySet<>(container.subList(fromIndex, toIndex + 1), comparator);
        }
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        /*
        Возвращает представление части этого набора, элементы которого варьируются от fromElement до toElement.
        Если fromElement и toElement равны, возвращаемый набор пуст, если fromInclusive и toInclusive оба не истинны.
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
         */
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("Index error, fromElement must be less than toElement!");
        } else {
            return generateSubSet(fromElement, fromInclusive, toElement, toInclusive);
        }
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        /*
        Возвращает представление части этого набора, элементы которого меньше (или равны, если inclusive верно) toElement.
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
        */
        if (isEmpty()) {
            return newEmptyArraySet();
        } else {
            return generateSubSet(first(), true, toElement, inclusive);
        }
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        /*
        Возвращает представление части этого набора, элементы которого больше (или равны, если inclusive верно) fromElement.
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
        */
        if (isEmpty()) {
            return newEmptyArraySet();
        } else {
            return generateSubSet(fromElement, inclusive, last(), true);
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        /*
        Возвращает представление части этого набора , элементы которого варьируются от fromElement включительно до toElement исключительно.
        (Если fromElement и toElement равны, возвращаемый набор пуст).
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
        */
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        /*
        Возвращает представление части этого набора, элементы которого строго меньше toElement.
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
         */
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        /*
        Возвращает представление части этого набора, элементы которого больше или равны fromElement.
        Возвращаемый набор поддерживается этим набором, поэтому изменения в возвращаемом наборе отражаются в этом наборе, и наоборот.
        Возвращаемый набор поддерживает все необязательные операции набора, которые поддерживает этот набор.
         */
        return tailSet(fromElement, true);
    }

    public E elementByIndex(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("The ArraySet hasn't got any elements!");
        } else {
            return container.get(index);
        }
    }

    @Override
    public E first() {
        /* Возвращает первый элемент множества (в противном случае кидает NoSuchElementException). */
        return elementByIndex(0);
    }

    @Override
    public E last() {
        /* Возвращает последний элемент множества (в противном случае кидает NoSuchElementException). */
        return elementByIndex(size() - 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        /* Проверяет наличия объекта в множестве. */
        return binarySearch((E) o) >= 0;
    }

    @Override
    public boolean isEmpty() {
        /* Проверяет множество на пустоту. */
        return size() == 0;
    }

    @Override
    public int size() {
        /* Возвращает длину множества. */
        return container.size();
    }

    public String UNSUPPORTED_OPERATION_MESSAGE = "The ArraySet is immutable and does not support the method";

    @Override
    public E pollFirst() {
        /* Извлекает и удаляет первый (самый низкий) элемент или возвращает null, если этот набор пуст. */
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'pollFirst'!");
    }

    @Override
    public E pollLast() {
        /* Извлекает и удаляет последний (самый высокий) элемент или возвращает null, если этот набор пуст. */
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'pollLast'!");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'clear'!");
    }

    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'addAll'!");
    }

    @Override
    public boolean add(final E element) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'add'!");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'removeAll'!");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'remove'!");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE + " 'retainAll'");
    }
}
