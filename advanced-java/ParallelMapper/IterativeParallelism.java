package info.kgeorgiy.ja.zaynidinov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class implements the ListIP interface, for parallel distribution of threads in interface methods.
 *
 * @author Zaynidinov Mirzofirdavs.
 */
@SuppressWarnings("unused")
public class IterativeParallelism implements ListIP {
    private final ParallelMapper parallelMapper;

    /**
     * Constructor for ParallelMapper.
     *
     * @param mapper, initializes the parallelMapper.
     */
    public IterativeParallelism(ParallelMapper mapper) {
        this.parallelMapper = mapper;
    }

    /**
     * Constructor for ParallelMapper.
     */
    public IterativeParallelism() {
        parallelMapper = null;
    }

    private <T> List<Stream<? extends T>> getThreadPart(final int threads, List<? extends T> values) {
        List<Stream<? extends T>> parts = new ArrayList<>();

        int wholePart = values.size() / threads;
        int remains = values.size() % threads;
        int left = 0;

        for (int i = 0; i < threads; ++i) {
            int right = left + wholePart + (remains-- > 0 ? 1 : 0);
            parts.add(values.subList(left, right).stream());
            left = right;
        }

        return parts;
    }

    private <T, R> R doParallel(int threads,
                                List<? extends T> values,
                                Function<Stream<? extends T>, ? extends R> calcFunction,
                                Function<Stream<? extends R>, ? extends R> resultCollector) throws InterruptedException {
        final List<R> result;
        threads = Math.min(threads, values.size());
        List<Stream<? extends T>> parts = getThreadPart(threads, values);

        if (parallelMapper == null) {
            Thread[] threadList = new Thread[threads];
            result = new ArrayList<>(Collections.nCopies(threads, null));

            for (int i = 0; i < threads; ++i) {
                final int index = i;
                threadList[index] = new Thread(() -> result.set(index, calcFunction.apply(parts.get(index))));
                threadList[index].start();
            }

            boolean flag = false;
            for (Thread thread : threadList) {
                try {
                    thread.join();
                } catch (InterruptedException ignored) {
                    flag = true;
                }
            }

            if (flag) {
                throw new InterruptedException("One of the threads failed to join");
            }
        } else {
            result = parallelMapper.map(calcFunction, parts);
        }

        return resultCollector.apply(result.stream());
    }

    /**
     * Method join, concatenates string representations of list items into multiple streams.
     *
     * @param threads {@link Thread} number of concurrent threads.
     * @param values  the {@link List} of values to join.
     * @return {@link String} return concatenated string.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public String join(int threads, List<?> values) throws InterruptedException {
        return doParallel(threads, values,
                stream -> stream.map(Object::toString).collect(Collectors.joining()),
                stream -> stream.collect(Collectors.joining())
        );
    }

    /**
     * Method filter, filters and returns a {@link List} list containing elements satisfying the predicate.
     *
     * @param threads   number of concurrent threads.
     * @param values    the {@link List} of values to filter.
     * @param predicate filter predicate.
     * @param <T>       is specified list type.
     * @return the {@link List} of filtered values.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T> List<T> filter(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return doParallel(threads, values,
                stream -> stream.filter(predicate).collect(Collectors.toList()),
                stream -> stream.flatMap(List::stream).collect(Collectors.toList())
        );
    }

    /**
     * Method map, returns a {@link List} list containing the results of the function application.
     *
     * @param threads number of concurrent threads.
     * @param values  the {@link List} of values to function application.
     * @param f       is function for element. <i>T -> U</i>.
     * @param <T>     is type of specified list.
     * @param <U>     is type of new list.
     * @return the {@link List} of function application.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T, U> List<U> map(int threads, List<? extends T> values, Function<? super T, ? extends U> f) throws InterruptedException {
        return doParallel(threads, values,
                stream -> stream.map(f).collect(Collectors.toList()),
                stream -> stream.flatMap(List::stream).collect(Collectors.toList())
        );
    }

    /**
     * Method maximum, returns the first maximum in the {@link List} list with specified {@link Comparator} comparator.
     *
     * @param threads    number or concurrent threads.
     * @param values     the {@link List} of the values.
     * @param comparator is {@link Comparator} for comparison elements.
     * @param <T>        is type of specified list.
     * @return first maximum element.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return doParallel(threads, values,
                stream -> stream.max(comparator).orElse(null),
                stream -> stream.max(comparator).orElse(null));
    }

    /**
     * Method minimum, returns the first minimum in the {@link List} list with specified {@link Comparator} comparator.
     *
     * @param threads    number or concurrent threads.
     * @param values     the {@link List} of the values.
     * @param comparator is {@link Comparator} for comparison elements.
     * @param <T>        is type of specified list.
     * @return first minimum element.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, Collections.reverseOrder(comparator));
    }

    /**
     * Method all, checks that all values in {@link List} list satisfy the {@link Predicate} predicate;
     *
     * @param threads   number or concurrent threads.
     * @param values    the {@link List} of the values.
     * @param predicate is predicate for check.
     * @param <T>       is type of specified list.
     * @return {@link Boolean} result of checking.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return doParallel(threads, values,
                stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue));
    }

    /**
     * Method any, checks that there is a list item that satisfies the predicate.
     *
     * @param threads   number or concurrent threads.
     * @param values    the {@link List} of the values.
     * @param predicate is predicate for check.
     * @param <T>       is type of specified list.
     * @return {@link Boolean} result of checking.
     * @throws InterruptedException when any thread is interrupted.
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}