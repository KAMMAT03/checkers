import java.io.*;
import java.net.Socket;

public class EchoClient {
	Socket socket;

	public EchoClient(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
	}

	public void connect() throws Exception {
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

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		game.getBoard().displayBoard();
		System.out.println("Waiting for server move...");
		game = (Game) in.readObject();
		System.out.println("Client moved: " + game);
		game.getBoard().displayBoard();

		System.out.println("Enter piece to move: ");
		String pieceToMove = reader.readLine();
		out.writeObject(pieceToMove);
		out.flush();

		if(pieceToMove.equals("xx")){
			throw new Exception("You left.");
		}

		System.out.println("Enter move: ");
		String move = reader.readLine();

		if(move.equals("xx")){
			throw new Exception("You left.");
		}

		out.writeObject(move);
		out.flush();

		game.makeMove(pieceToMove,move);
		game.getBoard().displayBoard();
		//game.setMove(pieceToMove + " " + move); to setMove co mówiłam żeby jakoś dodać bo inaczej nie ma jak tego wywołać

	}

	public void closeGame() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
			System.out.println("The game has been closed and the client socket has been shut down.");
		}
	}

	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient("localhost", 12129);
		client.connect();
	}
}