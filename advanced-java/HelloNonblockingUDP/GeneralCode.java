package info.kgeorgiy.ja.zaynidinov.hello;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.nio.channels.SelectionKey.OP_READ;

/**
 * {@link Class} GeneralMethods to issue a common code.
 */
public class GeneralCode {
    public static final String SHUTDOWN_ERROR_MESSAGE = "Pool did not terminate";
    public static final String IOEXCEPTION_ERROR_MESSAGE = "I/O exception occurred while sending";
    public static final String SOCKET_ERROR_MESSAGE = "The socket couldn't be opened, or create";
    public static final String INPUT_ERROR_MESSAGE = "Incorrect input data";

    /**
     * Shutdowns all threads.
     *
     * @param executorService number of threads
     */
    public static void shutdownAndAwait(final ExecutorService executorService, boolean flag) {
        if (flag) {
            executorService.shutdown();
        } else {
            executorService.shutdownNow();
        }

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
     * Gets the data of packet and convert it to String.
     *
     * @param packet, data packet.
     * @return {@link String} converted data.
     */
    public static String getData(final DatagramPacket packet) {
        return new String(packet.getData(), packet.getOffset(), packet.getLength());
    }

    /**
     * Creates the new DatagramPacket.
     *
     * @param socket DatagramSocket.
     * @return new DatagramPacket.
     * @throws SocketException if socked couldn't open or create.
     */
    public static DatagramPacket createDataPacket(final DatagramSocket socket, boolean flag) throws SocketException {
        int dataResponseSize = socket.getReceiveBufferSize();

        if (flag) {
            dataResponseSize = socket.getSendBufferSize();
        }

        final byte[] dataResponse = new byte[dataResponseSize];

        return new DatagramPacket(dataResponse, dataResponseSize);
    }

    /**
     * Sends The request/response data.
     *
     * @param data          the container of data.
     * @param socketAddress socketAddress.
     * @param socket        socket.
     * @throws IOException exception occurred while sending.
     */
    public static void sendData(final byte[] data,
                                final SocketAddress socketAddress,
                                final DatagramSocket socket) throws IOException {
        socket.send(new DatagramPacket(data, data.length, socketAddress));
    }

    /**
     * Check the correctness of the entered data.
     *
     * @param args     input data.
     * @param argsSize input size.
     * @return false if the input is right else return true.
     */
    public static boolean invalidArguments(final String[] args, final int argsSize) {
        if (args == null || args.length != argsSize) {
            return true;
        }

        final Stream<String> invalidArguments = Arrays.stream(args).
                filter(Objects::isNull);

        return invalidArguments.findAny().isPresent();
    }

    /**
     * Main function for HelloUDPClient.
     *
     * @param args input dates.
     * @param flag for control.
     */
    public static void mainForHelloUDPClient(final String[] args, boolean flag) {
        if (invalidArguments(args, 5)) {
            System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
        } else {
            try {
                final String host = args[0];
                final int port = Integer.parseInt(args[1]);
                final String prefix = args[2];
                final int threads = Integer.parseInt(args[3]);
                final int requests = Integer.parseInt(args[4]);

                if (flag) {
                    HelloUDPClient helloUDPClient = new HelloUDPClient();
                    helloUDPClient.run(host, port, prefix, threads, requests);
                } else {
                    HelloUDPNonblockingClient helloUDPNonblockingClient = new HelloUDPNonblockingClient();
                    helloUDPNonblockingClient.run(host, port, prefix, threads, requests);
                }
            } catch (final NumberFormatException exception) {
                System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Main function for HelloUDPServer.
     *
     * @param args input dates.
     * @param flag for control.
     */
    public static void mainForHelloUDPServer(final String[] args, boolean flag) {
        if (GeneralCode.invalidArguments(args, 2)) {
            System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
        } else {
            try {
                final int port = Integer.parseInt(args[0]);
                final int threads = Integer.parseInt(args[1]);
                if (flag) {
                    try (HelloUDPServer helloUDPServer = new HelloUDPServer()) {
                        helloUDPServer.start(port, threads);
                    }
                } else {
                    try (HelloUDPNonblockingServer helloUDPNonblockingServer = new HelloUDPNonblockingServer()) {
                        helloUDPNonblockingServer.start(port, threads);
                    }
                }
            } catch (final NumberFormatException exception) {
                System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Put to the buffer data.
     *
     * @param buffer buffer.
     * @param data   data;.
     */
    public static void putBuffer(final ByteBuffer buffer, final byte[] data) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
    }

    /**
     * @param buffer        buffer.
     * @param channel       channel.
     * @param socketAddress adres.
     * @param key           key.
     */
    public static void sendData(final ByteBuffer buffer,
                                final DatagramChannel channel,
                                final SocketAddress socketAddress,
                                final SelectionKey key) {
        try {
            channel.send(buffer, socketAddress);
        } catch (IOException exception) {
            System.err.println(IOEXCEPTION_ERROR_MESSAGE);
        }

        if (channel.isOpen()) {
            key.interestOps(OP_READ);
        }
    }

    /**
     * Container of data.
     */
    public static class DataContainer {
        public final ByteBuffer buffer = ByteBuffer.allocate(1024);
        public int requestId;
        public final String prefix;
        public final int threadId;
        public final int requests;
        public final DatagramChannel channel;

        /**
         * Class constructor.
         *
         * @param prefix   prefix string.
         * @param threadId id.
         * @param requests number of requests.
         * @param channel  channel.
         */
        DataContainer(final String prefix, final int threadId, final int requests, final DatagramChannel channel) {
            this.threadId = threadId;
            this.requests = requests;
            this.prefix = prefix;
            this.channel = channel;
        }
    }

    /**
     * @param buffer  buffer.
     * @param channel channel.
     * @return socketAddress.
     * @throws IOException exception.
     */
    public static SocketAddress receiveData(final ByteBuffer buffer,
                                            final DatagramChannel channel)
            throws IOException {
        buffer.clear();
        SocketAddress socketAddress = channel.receive(buffer);
        buffer.flip();

        return socketAddress;
    }
}