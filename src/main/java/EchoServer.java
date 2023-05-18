import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private ServerSocket serverSocket;

	public EchoServer(String IP, int port) throws IOException {
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

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws Exception {
		if (game.isPlayerTurn()) {
			BufferedReader serverReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Server's turn. Enter piece to move: ");
			String serverPieceToMove = serverReader.readLine();

			if(serverPieceToMove.equals("xx")){
				throw new Exception("You left.");
			}

			System.out.println("Enter move: ");
			String serverMove = serverReader.readLine();

			if(serverMove.equals("xx")){
				throw new Exception("You left.");
			}

			game.makeMove(serverPieceToMove, serverMove);
			game.getBoard().displayBoard();

			out.reset();
			out.writeObject(game);
			out.flush();

		} else {
			String pieceToMove = (String) in.readObject();
			System.out.println("Server received piece to move: " + pieceToMove);

			if(pieceToMove.equals("xx")){
				throw new Exception("Other player left.");
			}

			String move = (String) in.readObject();
			System.out.println("Server received move: " + move);

			game.makeMove(pieceToMove, move);
			game.getBoard().displayBoard();

			out.reset();
			out.writeObject(game);
			out.flush();
		}
	}

	public void closeGame() throws IOException {
		if (serverSocket != null && !serverSocket.isClosed()) {
			serverSocket.close();
			System.out.println("The game has been closed and the server socket has been shut down.");
		}
	}

	public static void main(String[] args) throws Exception {
		EchoServer server = new EchoServer("localhost", 12129);
		server.host();
	}
}