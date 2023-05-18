import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;

	public Server(String IP, int port) throws IOException {
		serverSocket = new ServerSocket(port, 0, InetAddress.getByName(IP));
	}

	public void host() throws IOException {
		while (true) {
			try {
				System.out.println("Waiting...");
				Socket soc = serverSocket.accept();
				System.out.println("Established");

				ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

				Game game = new Game(8); // Tworzymy nową instancję gry

				out.writeObject(game);

				while (true) {
					sendAndReceiveMove(out, in, game);
				}
				/*

				while (!game.isGameOver()) {
					game.switchPlayerTurn();
					sendAndReceiveMove(out, in, game);
					game.makeMove(game.getMove());
					out.reset();
					out.writeObject(game);
					out.flush();
				}

				 */

			} catch (Exception e) {
				System.err.println("Server exception: " + e);
			}
		}
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws IOException, ClassNotFoundException {
		if (game.isPlayerTurn()) {
			BufferedReader serverReader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Server's turn. Enter piece to move: ");
			String serverPieceToMove = serverReader.readLine();
			System.out.println("Enter move: ");
			String serverMove = serverReader.readLine();

//			game.makeMove(serverPieceToMove, serverMove);
			out.reset();
			out.writeObject(game);
			out.flush();
		} else {
			String pieceToMove = (String) in.readObject();
			System.out.println("Server received piece to move: " + pieceToMove);

			String move = (String) in.readObject();
			System.out.println("Server received move: " + move);

//			game.makeMove(pieceToMove, move);

			out.reset();
			out.writeObject(game);
			out.flush();
		}
	}



	public static void main(String[] args) throws Exception {
		Server server = new Server("localhost", 12129);
		server.host();
	}
}