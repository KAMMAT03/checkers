import java.io.*;
import java.net.Socket;

public class EchoClient {
//	Socket socket;
//
//	public EchoClient(String IP, int port) throws IOException {
//		socket = new Socket(IP, port);
//	}

	public void Connect() throws IOException, ClassNotFoundException {
		System.out.println("Client started");
		Socket soc = new Socket("localhost",12129);

		ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

		while (true) {
			Game obj = new Game();

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter move: ");
			String newData = reader.readLine();
//			obj.setData(newData);

			out.writeObject(obj);
			out.flush();

			Game receivedObj = (Game) in.readObject();
			System.out.println("Client moved: " + receivedObj);

			if (newData.equals("exit")) {
				break;
			}
		}
	}

	/*
	public void sendAndReciveMove() throws IOException, ClassNotFoundException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		while (true) {
			Game obj = new Game();

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter move: ");
			String move = reader.readLine();
//			obj.setMove(move);

			out.writeObject(obj);
			out.flush();

			Game receivedObj = (Game) in.readObject();
			System.out.println("Client moved: " + receivedObj);

			if (move.equals("x")) {
				break;
			}
		}
	}

*/

	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient();
		client.Connect();
	}
}

