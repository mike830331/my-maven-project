import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a client player that connects to another player.
 */
public class PlayerClient {
    private final String name;
    private final String host;
    private final int port;
    private final AtomicInteger messageCounter = new AtomicInteger(0);

    public PlayerClient(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public void start() {
        System.out.println("name: " + name + " host: " + host + " port: " + port);
		for (int i = 0; i < 10; i++) {
		    try (Socket socket = new Socket(host, port);
		         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		         PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

		        String message = "Message " + (i + 1);
		        out.println(message);
		        System.out.println(name + " sent: " + message);

		        String receivedMessage = in.readLine();
		        System.out.println(name + " received: " + receivedMessage);
		    } catch (IOException e) {
		        System.err.println(name + " encountered an error: " + e.getMessage());
		    }
		}
    }

    public static void main(String[] args) {
        PlayerClient client = new PlayerClient("Player2", "localhost", 8080);
        client.start();
    }
}
