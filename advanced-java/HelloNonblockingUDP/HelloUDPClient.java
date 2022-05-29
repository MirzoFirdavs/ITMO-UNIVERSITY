package info.kgeorgiy.ja.zaynidinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * {@link Class} HelloUDPClient sends requests to the server, accepts the results and outputs them to the console.
 *
 * @author Mirzofirdavs Zaynidinov.
 */
public class HelloUDPClient implements HelloClient {
    /**
     * Main function for run.
     *
     * @param args array of command line arguments.
     */
    public static void main(final String[] args) {
        GeneralCode.mainForHelloUDPClient(args, true);
    }

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
    public void run(String host,
                    final int port,
                    final String prefix,
                    final int threads,
                    final int requests) {
        final SocketAddress socketAddress = new InetSocketAddress(host, port);
        final ExecutorService executorService = Executors.newFixedThreadPool(threads);

        IntStream.range(0, threads).forEach(
                threadId -> executorService.submit(
                        () -> getProcess(threadId, requests, prefix, socketAddress)
                )
        );

        GeneralCode.shutdownAndAwait(executorService, true);
    }

    private void getProcess(final int threadId,
                            final int requests,
                            final String prefix,
                            final SocketAddress socketAddress) {
        try (final DatagramSocket socket = new DatagramSocket()) {
            final DatagramPacket packet = GeneralCode.createDataPacket(socket, true);

            socket.setSoTimeout(20);

            IntStream.range(0, requests).forEach(id -> sendRequest(threadId, id, prefix, socketAddress, packet, socket));
        } catch (final SocketException exception) {
            System.err.println(GeneralCode.SOCKET_ERROR_MESSAGE);
        }
    }

    private void sendRequest(final int threadId,
                             final int id,
                             final String prefix,
                             final SocketAddress socketAddress,
                             final DatagramPacket packet,
                             final DatagramSocket socket) {
        final String request = prefix + threadId + "_" + id;

        while (!socket.isClosed() && !Thread.interrupted()) {
            try {
                GeneralCode.sendData(request.getBytes(), socketAddress, socket);
                socket.receive(packet);
            } catch (final IOException exception) {
                System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
            }

            final String response = GeneralCode.getData(packet);

            if (response.contains(request)) {
                break;
            }
        }
    }
}