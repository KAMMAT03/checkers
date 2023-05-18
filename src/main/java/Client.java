import java.io.*;
import java.net.Socket;

public class Client {
	Socket socket;

	public Client(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
	}

	public void connect() throws Exception {
		System.out.println("Klient uruchomiony.");

		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		Game game = (Game) in.readObject();

		while (true) {
			sendAndReceiveMove(out, in, game);
		}
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		game.getBoard().displayBoard();
		System.out.println("Czekanie na ruch serwera...");
		game = (Game) in.readObject();
		System.out.println("Klient poruszył się: " + game);
		game.getBoard().displayBoard();

		System.out.println("Wpisz pionek do przesunięcia: ");
		String pieceToMove = reader.readLine();
		out.writeObject(pieceToMove);
		out.flush();

		if(pieceToMove.equals("xx")){
			throw new Exception("Wyszedłeś.");
		}

		System.out.println("Wpisz ruch jaki chcesz wykonać: ");
		String move = reader.readLine();

		if(move.equals("xx")){
			throw new Exception("Wyszedłeś.");
		}

		out.writeObject(move);
		out.flush();

		game.makeMove(pieceToMove,move);
		game.getBoard().displayBoard();

	}

	public void closeGame() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
			System.out.println("Gra została zamknięta, a gniazdo klienta wyłączone.");
		}
	}

	public static void main(String[] args) throws Exception {
		Client client = new Client("localhost", 9899);
		client.connect();
	}
}