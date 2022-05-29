package info.kgeorgiy.ja.zaynidinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 * {@link Class} HelloUDPServer, accepts tasks sent by the {@link Class} HelloUDPClient and responds to them, with nonblocking input and output.
 *
 * @author Mirzofirdavs Zaynidinov.
 */
public class HelloUDPNonblockingServer implements HelloServer {
    private DatagramChannel channel;
    private ExecutorService receiver;
    private Selector selector;

    /**
     * Starts a new Hello server.
     *
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(final int port, final int threads) {
        final InetSocketAddress socketAddress = new InetSocketAddress(port);
        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.bind(socketAddress).configureBlocking(false).register(selector, OP_READ);
            receiver = Executors.newSingleThreadExecutor();
            receiver.submit(() -> sendHelloResponse(socketAddress, buffer));
        } catch (IOException e) {
            System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
        }
    }

    /**
     * Stops server and frees all resources.
     */
    @Override
    public void close() {
        GeneralCode.shutdownAndAwait(receiver, false);

        try {
            if (channel != null) {
                channel.close();
            }

            if (selector != null) {
                selector.close();
            }
        } catch (final IOException exception) {
            System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
        }
    }

    /**
     * Main function for run.
     *
     * @param args array of command line arguments.
     */
    public static void main(final String[] args) {
        GeneralCode.mainForHelloUDPServer(args, false);
    }

    private void sendHelloResponse(SocketAddress socketAddress, final ByteBuffer buffer) {
        while (selector.isOpen() && !Thread.interrupted() && !channel.socket().isClosed()) {
            try {
                if (selector.select() > 0) {
                    for (final Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); iter.hasNext(); ) {
                        final SelectionKey key = iter.next();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isReadable()) {
                            socketAddress = GeneralCode.receiveData(buffer, channel);

                            GeneralCode.putBuffer(buffer, ("Hello, " + StandardCharsets.UTF_8.decode(buffer)).getBytes());
                            key.interestOps(OP_WRITE);
                        } else if (key.isWritable()){
                            GeneralCode.sendData(buffer, channel, socketAddress, key);
                        }

                        iter.remove();
                    }
                }
            } catch (final IOException exception) {
                System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
            }
        }
    }
}