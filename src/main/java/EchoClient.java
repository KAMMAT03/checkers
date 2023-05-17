import java.io.*;
import java.net.Socket;

public class EchoClient {
	Socket socket;

	public EchoClient(String IP, int port) throws IOException {
		socket = new Socket(IP, port);
	}

	public void connect() throws IOException, ClassNotFoundException {
		System.out.println("Client started");

		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		while (true) {
			Game obj = new Game(8);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter move: ");
			String newData = reader.readLine();
			// TODO: Ustawienie odpowiednich wartości w obiekcie "obj" na podstawie danych od użytkownika

			sendAndReceiveMove(out, in, obj);

			if (newData.equals("x")) {
				break;
			}
		}
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game obj) throws IOException, ClassNotFoundException {
		out.writeObject(obj);
		out.flush();

		Game receivedObj = (Game) in.readObject();
		System.out.println("Client moved: " + receivedObj);
	}

	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient("localhost", 12129);
		client.connect();
	}
}