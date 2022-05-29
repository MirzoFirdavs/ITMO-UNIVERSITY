package info.kgeorgiy.ja.zaynidinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.IntStream;

import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 * {@link Class} HelloUDPClient sends requests to the server, accepts the results and outputs them to the console, with nonblocking input and output.
 *
 * @author Mirzofirdavs Zaynidinov.
 */
public class HelloUDPNonblockingClient implements HelloClient {
    /**
     * Method run, runs Hello client.
     *
     * @param host     server host
     * @param port     server port
     * @param prefix   request prefix
     * @param threads  number of request threads
     * @param requests number of requests per thread.
     */
    @Override
    public void run(final String host,
                    final int port,
                    final String prefix,
                    final int threads,
                    final int requests) {
        try {
            final Selector selector = Selector.open();
            final InetSocketAddress socketAddress = new InetSocketAddress(host, port);

            configuration(threads, prefix, requests, selector);

            while (!selector.keys().isEmpty() && !Thread.interrupted()) {
                selector.select(100);

                if (selector.selectedKeys().isEmpty()) {
                    selector.keys().forEach(key -> key.interestOps(OP_WRITE));
                }

                processing(selector, socketAddress, prefix);
            }
        } catch (final IOException e) {
            System.err.println("ERROR: Unable to close Selector!");
        }
    }

    /**
     * Main function for run.
     *
     * @param args array of command line arguments.
     */
    public static void main(final String[] args) {
        GeneralCode.mainForHelloUDPClient(args, false);
    }

    private void configuration(final int threads,
                               final String prefix,
                               final int requests,
                               final Selector selector) {
        IntStream.range(0, threads).forEach(i -> {
            try {
                final DatagramChannel channel = DatagramChannel.open();

                channel.configureBlocking(false);
                channel.register(selector, OP_WRITE, new GeneralCode.DataContainer(prefix, i, requests, channel));
            } catch (final IOException exception) {
                System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
            }
        });
    }

    private void processing(final Selector selector,
                            final SocketAddress socketAddress,
                            final String prefix) {
        for (final Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); iter.hasNext(); ) {
            final SelectionKey key = iter.next();
            final GeneralCode.DataContainer data = (GeneralCode.DataContainer) key.attachment();

            try {
                if (data.requestId >= data.requests) {
                    data.channel.close();
                } else {
                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isReadable()) {
                        GeneralCode.receiveData(data.buffer, data.channel);

                        if (StandardCharsets.UTF_8.decode(data.buffer).toString().contains(data.prefix + data.threadId + "_" + data.requestId)) {
                            data.requestId++;
                        }

                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        GeneralCode.putBuffer(data.buffer, (prefix + data.threadId + "_" + data.requestId).getBytes());
                        GeneralCode.sendData(data.buffer, data.channel, socketAddress, key);
                    }

                    iter.remove();
                }
            } catch (final IOException exception) {
                System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
            }
        }
    }
}