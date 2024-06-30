import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a player in the communication system.
 */
public class Player {
    private final String name;
    private final int port;
    private final AtomicInteger messageCounter = new AtomicInteger(0);
    private ServerSocket serverSocket;

    public Player(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("name: " + name + " port: " + port);
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String receivedMessage;
                    while ((receivedMessage = in.readLine()) != null) {
                        System.out.println(name + " received: " + receivedMessage);
                        String replyMessage = receivedMessage + " " + messageCounter.incrementAndGet();
                        out.println(replyMessage);
                        System.out.println(name + " sent: " + replyMessage);

                        if (messageCounter.get() >= 10) {
                            return; // Stop after 10 messages sent and received
                        }
                    }
                } catch (IOException e) {
                    System.err.println(name + " encountered an error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to start server for " + name + " on port " + port);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close server socket for " + name);
                }
            }
        }
    }

    public static void main(String[] args) {
        Player player = new Player("Player1", 8080);
        player.start();
    }
}
