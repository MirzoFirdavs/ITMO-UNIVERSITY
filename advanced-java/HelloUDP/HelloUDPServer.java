package info.kgeorgiy.ja.zaynidinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * {@link Class} HelloUDPServer, accepts tasks sent by the {@link Class} HelloUDPClient and responds to them.
 *
 * @author Mirzofirdavs Zaynidinov.
 */
public class HelloUDPServer implements HelloServer {
    private DatagramSocket socket;
    private ExecutorService executorService;

    /**
     * Main function for run.
     *
     * @param args array of command line arguments.
     */
    public static void main(String[] args) {
        if (GeneralCode.invalidArguments(args, 2)) {
            System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
        } else {
            try {
                final int port = Integer.parseInt(args[0]);
                final int threads = Integer.parseInt(args[1]);

                try (HelloUDPServer helloUDPServer = new HelloUDPServer()) {
                    helloUDPServer.start(port, threads);
                }
            } catch (final NumberFormatException exception) {
                System.err.println(GeneralCode.INPUT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Starts a new Hello server.
     *
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(int port, int threads) {
        executorService = Executors.newFixedThreadPool(threads);

        try {
            if (socket == null) {
                socket = new DatagramSocket(port);
            }

            IntStream.range(0, threads).forEach(x -> executorService.submit(this::sendResponse));
        } catch (final SocketException exception) {
            System.err.println(GeneralCode.SOCKET_ERROR_MESSAGE);
        }
    }

    /**
     * Stops server and frees all resources.
     */
    @Override
    public void close() {
        socket.close();
        GeneralCode.shutdownAndAwait(executorService);
    }

    private void sendResponse() {
        while (!socket.isClosed() && !Thread.interrupted()) {
            try {
                final DatagramPacket packet = GeneralCode.createDataPacket(socket, false);

                socket.receive(packet);

                final byte[] response = ("Hello, " + GeneralCode.getData(packet)).getBytes();

                GeneralCode.sendData(response, packet.getSocketAddress(), socket);
            } catch (final IOException exception) {
                System.err.println(GeneralCode.IOEXCEPTION_ERROR_MESSAGE);
            }
        }
    }
}