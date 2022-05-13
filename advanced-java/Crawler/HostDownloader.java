package info.kgeorgiy.ja.zaynidinov.crawler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * Class HostDownloader.
 */
public class HostDownloader {
    private final int perHost;
    private final Map<String, Semaphore> semaphores = new ConcurrentHashMap<>();

    /**
     * @param perHost the maximum number of pages loaded simultaneously from one host. To determine the host, use the getHost method of the URLUtils class from the tests.
     */
    public HostDownloader(final int perHost) {
        this.perHost = perHost;
    }

    /**
     * @param hostName    name of host.
     * @param downloaders the maximum number of simultaneously downloaded pages.
     * @param task        runnable task.
     */
    public void addTask(final String hostName, final ExecutorService downloaders, final Runnable task) {
        final Semaphore semaphore = semaphores.computeIfAbsent(hostName, name -> new Semaphore(perHost));

        downloaders.submit(() -> {
            try {
                semaphore.acquire();
                task.run();
                semaphore.release();
            } catch (final InterruptedException ignored) {
                // ignored;
            }
        });
    }
}