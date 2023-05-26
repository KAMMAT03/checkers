import java.io.*;
import java.net.Socket;

public class Client {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void playGame() throws IOException, ClassNotFoundException {
		UserInterface ui = new UserInterface();
		Game game = (Game) in.readObject();
		ui.setGame(game);

		while (true) {
			if (ui.getGame().isPlayerTurn()) {
				ui.newMove();
				out.reset();
				out.writeObject(ui.getGame());
				out.flush();
			} else {
				game = (Game) in.readObject();
				ui.setGame(game);
				System.out.println("Server made a move.");
				ui.getGame().getBoard().displayBoard();
			}
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Client client = new Client("localhost", 9899);
		client.playGame();
	}
}
