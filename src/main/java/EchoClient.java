import java.io.*;
import java.net.Socket;

public class EchoClient {
	private Socket socket;

	public EchoClient(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
	}

	public void Connect() throws IOException {
		long l = 0;
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			pw.println("Hello server! " + l++);

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			System.out.println(br.readLine());

			Game game = receiveMove();

		} catch (Exception e) {
			System.err.println("Client exception: " + e);
		}
	}

	public void sendMove(Game game) {
		try {
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(game);
			oos.flush();
		} catch (IOException e) {
			System.err.println("Error sending move: " + e.getMessage());
		}
	}

	public Game receiveMove() {
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Game game = (Game) ois.readObject();
			return game;
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error receiving move: " + e.getMessage());
		}
		return null;
	}



	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient("localhost", 12129);
		client.Connect();
	}
}

