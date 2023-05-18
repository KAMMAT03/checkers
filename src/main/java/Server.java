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
				System.out.println("Czekanie na połączenie się klienta...");
				Socket soc = serverSocket.accept();
				System.out.println("Nawiązano połączenie.");

				ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

				Game game = new Game(8); // Tworzymy nową instancję gry

				out.writeObject(game);

				while (true) {
					sendAndReceiveMove(out, in, game);
				}

			} catch (Exception e) {
				System.err.println (e);
			}
		}
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game game) throws Exception {
		if (game.isPlayerTurn()) {
			BufferedReader serverReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ruch serwera. Wpisz pionek do przesunięcia: ");
			String serverPieceToMove = serverReader.readLine();

			if(serverPieceToMove.equals("xx")){
				throw new Exception("Wyszedłeś.");
			}

			System.out.println("Wpisz ruch jaki chcesz wykonać: ");
			String serverMove = serverReader.readLine();

			if(serverMove.equals("xx")){
				throw new Exception("Wyszedłeś.");
			}

			game.makeMove(serverPieceToMove, serverMove);
			game.getBoard().displayBoard();

			out.reset();
			out.writeObject(game);
			out.flush();

		} else {
			String pieceToMove = (String) in.readObject();
			System.out.println("Serwer otrzymał pionek do poruszenia: " + pieceToMove);

			if(pieceToMove.equals("xx")){
				throw new Exception("Drugi gracz wyszedł.");
			}

			String move = (String) in.readObject();
			System.out.println("Serwer otrzymał ruch do wykonania: " + move);

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
			System.out.println("Gra została zamknięta, a gniazdo serwera zostało wyłączone.");
		}
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server("localhost", 9899);
		server.host();
	}
}