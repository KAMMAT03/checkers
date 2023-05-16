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
				Socket socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				String fromClient = br.readLine();
				System.out.println("From client: [" + fromClient + "]");
				pw.println("Echo: " + fromClient);

				Game game = new Game();
				sendMove(game, socket);

			} catch (Exception e) {
				System.err.println("Server exception: " + e);
			}
		}
	}

	public void sendMove(Game game, Socket socket) {
		try {
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(game);
			oos.flush();
		} catch (IOException e) {
			System.err.println("Error sending move: " + e.getMessage());
		}
	}


	public static void main(String[] args) throws Exception {
		EchoServer server = new EchoServer("localhost", 12129);
		server.HOST();
	}
}
