import java.io.*;
import java.net.*;
public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Game game;

    public Client(Game game) {
        this.game = game;
    }
    Socket Connect(String IP, int port){
        try {
            socket = new Socket(IP, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    private void startListening() {
        Thread listeningThread = new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    String message = in.readUTF();
                    processServerMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listeningThread.start();
    }

    private void processServerMessage(String message) {
        String[] parts = message.split(" ");
        switch (parts[0]) {
            case "Change board":
                game.updateBoard(parts[1]);
                break;
            case "Move":
                Field start = game.getFieldById(Integer.parseInt(parts[1]));
                Field end = game.getFieldById(Integer.parseInt(parts[2]));
                game.move(start, end);
                game.endTurn();
                break;
            case "Close game":
                closeGame();
                break;
            default:
                System.err.println("Unknown message received: " + message);
                break;
            if (message.startsWith("Error:")) {
                String errorMessage = message.substring(6);
                handleError(errorMessage);
            } else if (message.startsWith("Score:")) {
                int score = Integer.parseInt(message.substring(6));
                handleScoreUpdate(score);
            }
        }
    }

    void SendMove(Field start, Field end) {
        try {
            out.writeUTF("Move " + start.getId() + " " + end.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void ChangeBoard(String newBoard){
        try {
            out.writeUTF("Change board " + newBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void CloseGame(){
        try {
            out.writeUTF("Close game");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendError(String errorMessage) {
        try {
            out.writeUTF("Error:" + errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void handleError(String errorMessage) {
        // Obsługa błędu, np. wyświetlenie komunikatu o błędzie użytkownikowi
        System.err.println("Błąd: " + errorMessage);
        // Można również wyświetlić komunikat o błędzie w interfejsie użytkownika
    }

}
