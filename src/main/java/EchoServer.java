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

				while (true) {
					Game obj = (Game) in.readObject();
					System.out.println("Server received: " + obj);

					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Enter move: ");
					String move = reader.readLine();
					// TODO: Sprawdzenie poprawności ruchu na podstawie obiektu "obj"

					sendAndReceiveMove(out, in, obj);

					if (move.equals("x")) {
						break;
					}
				}

			} catch (Exception e) {
				System.err.println("Server exception: " + e);
			}
		}
	}

	private void sendAndReceiveMove(ObjectOutputStream out, ObjectInputStream in, Game obj) throws IOException, ClassNotFoundException {
		out.writeObject(obj);
		out.flush();

		Game receivedObj = (Game) in.readObject();
		System.out.println("Server moved: " + receivedObj);
	}

	public static void main(String[] args) throws Exception {
		EchoServer server = new EchoServer("localhost", 12129);
		server.host();
	}
}