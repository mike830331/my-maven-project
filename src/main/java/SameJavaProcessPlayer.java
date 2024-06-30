import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a player in the communication system.
 */
public class SameJavaProcessPlayer {
	private String name;
	private SameJavaProcessPlayer otherPlayer;
	private AtomicInteger messageCounter;

	public SameJavaProcessPlayer(String name) {
		this.name = name;
		this.messageCounter = new AtomicInteger(0);
	}

	public void setOtherPlayer(SameJavaProcessPlayer otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	public void sendMessage(String message) {
		if (otherPlayer != null) {
			int counter = messageCounter.incrementAndGet();
			String newMessage = message + " " + counter;
			System.out.println(name + " sent to : " + otherPlayer.name + " " + newMessage);
			otherPlayer.receiveMessage(newMessage);
		}
	}

	public void receiveMessage(String message) {
		System.out.println(name + " received: " + message);
		if (messageCounter.get() < 10) {
			sendMessage(message);
		}
	}

	public static void main(String[] args) {
		SameJavaProcessPlayer player1 = new SameJavaProcessPlayer("Player1");
		SameJavaProcessPlayer player2 = new SameJavaProcessPlayer("Player2");

		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);

		// Initiate communication
		player1.sendMessage("Message");
	}
}
