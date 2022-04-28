package info.kgeorgiy.ja.zaynidinov.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

/**
 * Class ParallelMapperImpl implements interface ParallelMapper, for parallel distribution of threads.
 *
 * @author Zaynidinov Mirzofirdavs.
 */
@SuppressWarnings("unused")
public class ParallelMapperImpl implements ParallelMapper {
    private final Queue<Runnable> tasks;
    private final List<Thread> performers;

    /**
     * Constructor for ParallelMapperImpl.
     *
     * @param threads, the number of threads to parallel.
     */
    public ParallelMapperImpl(final int threads) {
        this.tasks = new ArrayDeque<>();
        this.performers = new ArrayList<>();

        for (int i = 0; i < threads; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        Runnable task;
                        synchronized (tasks) {
                            while (tasks.isEmpty()) {
                                tasks.wait();
                            }

                            task = tasks.poll();
                            tasks.notifyAll();
                        }
                        task.run();
                    }
                } catch (InterruptedException e) {
                    // ignored
                } finally {
                    Thread.currentThread().interrupt();
                }
            });

            performers.add(thread);
            thread.start();
        }
    }

    /**
     * Applies a function {@link Function} to all elements of the specified list {@link List}.
     *
     * @param f,    is function {@link Function} to apply.
     * @param args, is list {@link List} to process.
     * @param <T>,  is type the specified list.
     * @param <R>,  is type result list.
     * @return result list, when any thread is interrupted.
     * @throws InterruptedException, when any thread is interrupted.
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        final int size = args.size();
        Mapper<R> result = new Mapper<>(size);

        for (int i = 0; i < size; ++i) {
            final int index = i;
            synchronized (tasks) {
                while (tasks.size() == 1000) {
                    tasks.wait();
                }
                tasks.add(() -> result.setListElement(index, f.apply(args.get(index))));
                tasks.notifyAll();
            }
        }

        return result.getContainer();
    }

    /**
     * Closes created threads.
     */
    @Override
    public void close() {
        performers.forEach(thread -> {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignored;
            }
        });
    }
}