package info.kgeorgiy.ja.zaynidinov.arrayset;

import java.util.*;

public class ReverseList<E> extends AbstractList<E> implements RandomAccess {
    List<E> container;
    boolean reversed;

    ReverseList(Collection<E> collection) {
        this.container = List.copyOf(collection);
        this.reversed = false;
    }

    public ReverseList(List<E> set) {
        this.container = Collections.unmodifiableList(set);
        reversed = false;
    }

    @Override
    public ReverseList<E> subList(int fromIndex, int toIndex) {
        if (reversed) {
            return new ReverseList<>(container.subList(size() - toIndex - 2, size() - fromIndex));
        } else {
            return new ReverseList<>(container.subList(fromIndex, toIndex));
        }
    }

    public ReverseList(ReverseList<E> set, boolean reversed) {
        this.container = set.container;
        this.reversed = set.reversed ^ reversed;
    }

    @Override
    public E get(int index) {
        if (reversed) {
            return container.get(size() - index - 1);
        } else {
            return container.get(index);
        }
    }

    @Override
    public int size() {
        return container.size();
    }
}
