package info.kgeorgiy.ja.zaynidinov.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
    public static void shutdownAndAwait(final ExecutorService executorService) {
        executorService.shutdownNow();

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
}