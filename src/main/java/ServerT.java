import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerT {
    public static void main(String args[])throws Exception{
        ServerSocket ss=new ServerSocket(3333);
        Socket s=ss.accept();
        DataInputStream din=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        int columnStart;
        int rowStart;
        int columnMove;
        int rowMove;
        int index;
        boolean correctStartField = false;
        boolean turn = true;
        boolean playerTurn;


        while (!gameOver){
            String board = "";
            board = din.readUTF();
            System.out.println(board);
            playerTurn = din.readBoolean();
            if (playerTurn){
                System.out.println("Ruch białych, czekaj na swój ruch");
                continue;
            }
            System.out.println("Ruch czarnych");
            String msg1 = "";
            msg1 = din.readUTF();
            System.out.println(msg1);
            while (true) {
                columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                rowStart = scanner.nextInt() - 1;
                int id = rowStart + 100 * columnStart;
                dout.writeInt(id);
                dout.flush();
                correctStartField = din.readBoolean();
                if (correctStartField) break;
                System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
            }

            while (turn){
                String msg2 = "";
                msg2 = din.readUTF();
                System.out.println(msg2);
                while (true) {
                    columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    if (columnMove == 23){
                        dout.writeBoolean(true);
                        dout.flush();
                        break;
                    }
                    dout.writeBoolean(false);
                    dout.flush();
                    rowMove = scanner.nextInt() - 1;
                    index = rowMove + 100 * columnMove;
                    dout.writeInt(index);
                    dout.flush();
                }
                turn = din.readBoolean();
            }
            gameOver = din.readBoolean();
            if (gameOver){
                din.close();
                dout.close();
            }
        }
    }
}
