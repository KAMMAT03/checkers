import java.io.*;
import java.net.Socket;

public class Client {
	Socket socket;

	public Client(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
	}

	public void connect() throws IOException, ClassNotFoundException {
		System.out.println("Client started");

		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		Game game = (Game) in.readObject();

		while (true) {
			sendAndReceiveMove(out, in, game);
		}

		/* zmienić na to jak sprawdzicie to isGameOver

		while (!game.isGameOver()) {
			System.out.println(game.getBoard());
			sendAndReceiveMove(out, in, game);
			out.reset();
			out.writeObject(game);
			out.flush();
			game = (Game) in.readObject();
		}
		 */
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws IOException, ClassNotFoundException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter piece to move: ");
		String pieceToMove = reader.readLine();
		out.writeObject(pieceToMove);
		out.flush();

		System.out.println("Enter move: ");
		String move = reader.readLine();
		out.writeObject(move);
		out.flush();

		//game.setMove(pieceToMove + " " + move); to setMove co mówiłam żeby jakoś dodać bo inaczej nie ma jak tego wywołać

		System.out.println("Waiting for server move...");
		game = (Game) in.readObject();
		System.out.println("Client moved: " + game);
	}


	public static void main(String[] args) throws Exception {
		Client client = new Client("localhost", 12129);
		client.connect();
	}
}