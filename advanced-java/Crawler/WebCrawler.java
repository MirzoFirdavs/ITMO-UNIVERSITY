package info.kgeorgiy.ja.zaynidinov.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Thread-safe WebCrawler class that recursively crawls sites.
 *
 * @author Mirzofirdavs Zaynidinov.
 */
public class WebCrawler implements Crawler {
    private static final String INPUT_ERROR_MESSAGE = "Incorrect input data";
    private static final String DOWNLOAD_ERROR_MESSAGE = "Error occurred while creating Downloader or downloading from the given url ";
    private static final String SHUTDOWN_ERROR_MESSAGE = "Pool did not terminate";
    private final Downloader downloader;
    private final ExecutorService downloaders;
    private final ExecutorService extractors;
    private final HostDownloader hostDownloadsManager;
    private static final int DEFAULT_ARGUMENT_NUMBER = 1;
    private final Phaser phaser = new Phaser(1);

    /**
     * Class constructor.
     *
     * @param downloader  allows you to download pages and extract links from them.
     * @param downloaders the maximum number of simultaneously downloaded pages.
     * @param extractors  the maximum number of pages from which links are extracted at the same time.
     * @param perHost     the maximum number of pages loaded simultaneously from one host. To determine the host,
     *                    use the getHost method of the URLUtils class from the tests.
     */
    public WebCrawler(final Downloader downloader, final int downloaders, final int extractors, final int perHost) {
        this.downloader = downloader;
        this.extractors = Executors.newFixedThreadPool(extractors);
        this.downloaders = Executors.newFixedThreadPool(downloaders);

        hostDownloadsManager = new HostDownloader(perHost);
    }

    /**
     * {@link Class} downloader, gets list of all URLs.
     *
     * @param url   starting URL of crawler (<a href="http://tools.ietf.org/html/rfc3986">URL</a>).
     * @param depth depth of pages for visiting.
     * @return returns the list of all downloaded pages and errors.
     */
    @Override
    public Result download(final String url, final int depth) {
        final Set<String> result = ConcurrentHashMap.newKeySet();
        final ConcurrentMap<String, IOException> errors = new ConcurrentHashMap<>();
        final Set<String> usedLinks = ConcurrentHashMap.newKeySet();

        genDownloads(url, depth, result, errors, usedLinks);

        return new Result(new ArrayList<>(result), errors);
    }

    /**
     * Shutdowns all threads.
     */
    @Override
    public void close() {
            shutdownAndAwait(extractors);
            shutdownAndAwait(downloaders);
    }

    private void shutdownAndAwait(final ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                    System.err.println(SHUTDOWN_ERROR_MESSAGE);
                }
            }
        } catch (final InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Main function for run.
     *
     * @param args array of command line arguments.
     */
    public static void main(final String[] args) {
        if (invalidArguments(args)) {
            System.err.println(INPUT_ERROR_MESSAGE);
        } else {
            final String url = args[0];
            final int downloaders = safeGetArgument(args, 1);
            final int extractors = safeGetArgument(args, 2);
            final int perHost = safeGetArgument(args, 3);
            final int depth = safeGetArgument(args, 4);

            try (WebCrawler webCrawler = new WebCrawler(new CachingDownloader(), downloaders, extractors, perHost)) {
                webCrawler.download(url, depth);
            } catch (final IOException exception) {
                System.err.println(DOWNLOAD_ERROR_MESSAGE + exception);
            }
        }
    }

    private static int safeGetArgument(final String[] args, final int index) {
        if (args.length > index) {
            return Integer.parseInt(args[index]);
        }

        return DEFAULT_ARGUMENT_NUMBER;
    }

    private static boolean invalidArguments(final String[] args) {
        if (args == null || args.length <= 0 || args.length >= 5) {
            return true;
        }

        final Stream<String> invalidArguments = Arrays.stream(args).
                filter(Objects::isNull);

        return invalidArguments.findAny().isPresent();
    }

    private void genDownloads(final String url,
                              final int depth,
                              final Set<String> result,
                              final ConcurrentMap<String, IOException> errors,
                              final Set<String> usedLinks) {
        final Queue<String> queue = new ConcurrentLinkedQueue<>(Set.of(url));

        IntStream.range(0, depth).forEach(i -> {
            Stream<String> processing = new ArrayList<>(queue)
                    .stream()
                    .filter(usedLinks::add);

            queue.clear();

            processing.forEach(
                    currentUrl -> genProcess(currentUrl, depth - i, queue, errors, result)
            );

            phaser.arriveAndAwaitAdvance();
        });
    }

    private void extract(final Document page, final Queue<String> queue) {
        List<String> links = new ArrayList<>();
        try {
            links = page.extractLinks();
        } catch (final IOException ignored) {
            // ignored;
        } finally {
            queue.addAll(links);
            phaser.arriveAndDeregister();
        }
    }

    private void doTask(final String url,
                        final int newDepth,
                        final Queue<String> queue,
                        final ConcurrentMap<String, IOException> errors,
                        final Set<String> result) {
        try {
            final Document page = downloader.download(url);
            result.add(url);

            if (newDepth > 1) {
                phaser.register();
                extractors.submit(() -> extract(page, queue));
            }
        } catch (final IOException exception) {
            errors.put(url, exception);
        } finally {
            phaser.arriveAndDeregister();
        }
    }

    private void genProcess(final String url,
                            final int newDepth,
                            final Queue<String> queue,
                            final ConcurrentMap<String, IOException> errors,
                            final Set<String> result) {
        try {
            final String host = URLUtils.getHost(url);
            phaser.register();

            hostDownloadsManager.addTask(
                    host,
                    downloaders,
                    () -> doTask(url, newDepth, queue, errors, result)
            );
        } catch (final MalformedURLException exception) {
            errors.put(url, exception);
        }
    }
}