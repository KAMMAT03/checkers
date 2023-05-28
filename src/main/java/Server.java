import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        boolean gameOver = false;
        int columnStart;
        int rowStart;
        int columnMove;
        int rowMove;
        boolean correctStartField;
        boolean turn;
        boolean playerTurn;

        while (!gameOver) {
            turn = true;
            String board = "";
            while (board.equals("")) {
                board = din.readUTF();
            }
            System.out.println(board);
            playerTurn = din.readBoolean();
            if (playerTurn) {
                System.out.println("Ruch białych, czekaj na swój ruch");
            } else {
                System.out.println("Ruch czarnych");
                String msg1 = "";
                msg1 = din.readUTF();
                System.out.println(msg1);
                while (true) {
                    columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    dout.writeInt(columnStart);
                    dout.flush();
                    rowStart = scanner.nextInt() - 1;
                    dout.writeInt(rowStart);
                    dout.flush();
                    correctStartField = din.readBoolean();
                    if (correctStartField) break;
                    System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
                }
                while (turn) {
                    String msg2 = "";
                    msg2 = din.readUTF();
                    System.out.println(msg2);
                    while (true) {
                        columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                        dout.writeInt(columnMove);
                        dout.flush();
                        if (columnMove == 23) {
                            break;
                        }
                        rowMove = scanner.nextInt() - 1;
                        dout.writeInt(rowMove);
                        dout.flush();
                    }
                    turn = din.readBoolean();
                }
                gameOver = din.readBoolean();
                if (gameOver) {
                    din.close();
                    dout.close();
                }
            }
        }
    }
}