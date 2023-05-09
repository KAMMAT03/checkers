import java.net.ServerSocket;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class Server {
   private ServerSocket serverSocket;
   private Game game;
   private Map<Socket, DataOutputStream> clients;

   public Server(Game game) {
      this.game = game;
      clients = new HashMap<>();
   }

   ServerSocket HOST(String IP, int port){
      try {
         InetAddress inetAddress = InetAddress.getByName(IP);
         serverSocket = new ServerSocket(port, 50, inetAddress);

         while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            clients.put(socket, out);
            startListening(socket, in);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return serverSocket;
   }

   private void startListening(Socket socket, DataInputStream in) {
      Thread listeningThread = new Thread(() -> {
         while (!socket.isClosed()) {
            try {
               String message = in.readUTF();
               processClientMessage(socket, message);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });
      listeningThread.start();
   }

   private void processClientMessage(Socket socket, String message) {
      String[] parts = message.split(" ");
      switch (parts[0]) {
         case "Change board":
            game.updateBoard(parts[1]);
            broadcastMessage("Change board " + parts[1]);
            break;
         case "Move":
            Field start = game.getFieldById(Integer.parseInt(parts[1]));
            Field end = game.getFieldById(Integer.parseInt(parts[2]));
            List<Field> possibleMoves = game.showPossibleMoves(start);
            if (possibleMoves.contains(end) && CheckGameRules(start, end)) {
               game.move(start, end);
               game.endTurn();
               broadcastMessage("Move " + start.getId() + " " + end.getId());
            } else {
               sendMessage(socket, "Invalid move");
            }
            break;
         case "Close game":
            closeGame(socket);
            break;
         default:
            System.err.println("Unknown message received: " + message);
            break;
         if (message.startsWith("Error:")) {
            String errorMessage = message.substring(6);
            broadcastError(errorMessage);
      }
   }

   private void sendMessage(Socket socket, String message) {
      try {
         DataOutputStream out = clients.get(socket);
         out.writeUTF(message);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void broadcastMessage(String message) {
      for (DataOutputStream out : clients.values()) {
         try {
            out.writeUTF(message);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   void ChangeBoard(String newBoard){
      game.updateBoard(newBoard);
      broadcastMessage("Change board " + newBoard);
   }
   void CloseGame(){
      try {
         DataOutputStream out = clients.get(socket);
         out.writeUTF("Close game");
         clients.remove(socket);
         socket.close();
         if (clients.isEmpty()) {
            serverSocket.close();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   void SendMove(Field start, Field end){
      if (CheckGameRules(start, end)) {
         game.move(start, end);
         game.endTurn();
         broadcastMessage("Move " + start.getId() + " " + end.getId());
      }
   }

   boolean CheckGameRules(Field start, Field end) {
      // Implementacja logiki sprawdzania zasad gry
      // Na przykład: sprawdzenie, czy ruch jest dozwolony, czy jest zgodny z zasadami gry w warcaby
      // Jeśli ruch jest dozwolony, zwraca true. W przeciwnym razie zwraca false.
      // Przykład:
      List<Field> possibleMoves = game.showPossibleMoves(start);
      return possibleMoves.contains(end);
   }

   private void broadcastError(String errorMessage) {
      for (DataOutputStream out : clients.values()) {
         try {
            out.writeUTF("Error:" + errorMessage);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
