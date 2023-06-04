import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
//TODO Czemu to wyrzuca blad po rozłączeniu Clienta?
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        CheckersBoardGUI gui = new CheckersBoardGUI();

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
            playerTurn = din.readBoolean();
            if (playerTurn) {
                System.out.println("Ruch białych, czekaj na swój ruch");
            } else {
                System.out.println("Ruch czarnych");
                String msg1 = "";
                msg1 = din.readUTF();
                System.out.println(msg1);
                while (true) {
                    Field field = gui.lastClickedField;
                    while (field.equals(gui.lastClickedField)) {
                        try {
                            gui.fieldChangedLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    columnStart = field.getColIndex();
                    rowStart = field.getRowIndex();
                //    if (columnMove == 23) break; XD

                    dout.writeInt(columnStart);
//                    dout.flush();

                    dout.writeInt(rowStart);
//                    dout.flush();
                    correctStartField = din.readBoolean();
                    if (correctStartField) break;
                    System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
                }
                while (turn) {
                    String msg2 = "";
                    msg2 = din.readUTF();
                    System.out.println(msg2);
                    while (true) {
                        Field field = gui.lastClickedField;
                        while (field.equals(gui.lastClickedField)) {
                            try {
                                gui.fieldChangedLatch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        columnMove = field.getColIndex();
                        rowMove = field.getRowIndex();
                //        if (columnMove == 23) {break;} XD
                        dout.writeInt(columnStart);
//                    dout.flush();
                        dout.writeInt(rowStart);
//                    dout.flush();
                        break;
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
