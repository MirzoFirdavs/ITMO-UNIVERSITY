package info.kgeorgiy.ja.zaynidinov.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Mapper is a container, for getting result.
 *
 * @param <T>, is the specified type.
 */
public class Mapper<T> {
    private final List<T> container;
    private int counter;

    /**
     * Constructor for class Mapper.
     *
     * @param size, is the len of the container.
     */
    public Mapper(final int size) {
        this.container = new ArrayList<>(Collections.nCopies(size, null));
        this.counter = size;
    }

    /**
     * Method setListElement, sets the element from index {index}, to element {element}.
     *
     * @param index,   is the index for new element.
     * @param element, is the element for putting into the container.
     */
    public synchronized void setListElement(final int index, final T element) {
        container.set(index, element);
        counter--;

        if (counter == 0) {
            notify();
        }
    }

    /**
     * Method getContainer, returns the list of result.
     *
     * @return returns the list of result.
     * @throws InterruptedException when any thread is interrupted.
     */
    public synchronized List<T> getContainer() throws InterruptedException {
        while (counter > 0) {
            wait();
        }

        return container;
    }
}