import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private ServerSocket serverSocket;

	public EchoServer(String IP, int port) throws IOException {
		serverSocket = new ServerSocket(port, 0, InetAddress.getByName(IP));
	}

	public void HOST() throws IOException {
		while (true) {
			try {
				System.out.println("Waiting...");
				Socket soc = serverSocket.accept();
				System.out.println("Established");

				ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

				while (true) {
					Game obj = (Game) in.readObject();
					System.out.println("Server received: " + obj);

					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Enter move: ");
					String move = reader.readLine();
//					obj.setMove(move);

					out.writeObject(obj);
					out.flush();

					if (move.equals("x")) {
						//closeGame(serverSocket);
						break;
					}
				}

			} catch (Exception e) {
				System.err.println("Server exception: " + e);
			}
		}
	}


	public void sendAndReciveMove() {
		try {
			// musi przekazywać socket z poprzedniego żeby zadziałało
			Socket soc = serverSocket.accept(); // prowizoryczne accept drugi raz do testowania
			ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

			while (true) {
				Game obj = (Game) in.readObject();
				System.out.println("Server received: " + obj);

				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter move: ");
				String move = reader.readLine();
//					obj.setMove(move);

				out.writeObject(obj);
				out.flush();

				if (move.equals("x")) {
					//closeGame(serverSocket);
					break;
				}
			}

		} catch (Exception e) {
			System.err.println("Server exception: " + e);
		}
	}


        public void closeGame() {
        try {
            serverSocket.close();
            System.out.println("Game connection closed.");
        } catch (IOException e) {
            System.err.println("Error closing game connection: " + e.getMessage());
        }
    }

	/*

        public void checkRules(Game game) {

            tutaj będzie trzeba jakoś wywołać checkMove() albo showPossibleMoves() nie mam pojęcia jak to zaimplementować

        }


     */
	public static void main(String[] args) throws Exception {
		EchoServer server = new EchoServer("localhost",12129);
		server.HOST();
//		server.sendAndReciveMove();
	}
}
