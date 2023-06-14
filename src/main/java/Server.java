import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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
            makeBoardObject(board);
            System.out.println("chuj");
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
                    Field field = gui.lastClickedField;
                    while (field.equals(gui.lastClickedField)) {
                        try {
                            gui.fieldChangedLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("kloc");
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

                        System.out.println("kloc");
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

    public static Board makeBoardObject(String str) {
        // Usunięcie zbędnych spacji na początku i końcu

        // Podział na wiersze na podstawie znaku nowej linii
        System.out.println(str);
        System.out.println("Tera moje");
        String[] rows = str.split("\n");
        int size = rows[2].length();
        Board board = new Board(size);
        Map<Integer, Field> fieldsWithWhite = new HashMap<>();
        Map<Integer, Field> fieldsWithBlack = new HashMap<>();
        Field[][] fields = new Field[8][8]; //to change
        int index = 0;
        for (int i = 1; i < rows.length - 1; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                char toCheck = rows[i].charAt(j);
                Field field = new Field(i - 1, j - 1, null);
                if (toCheck == 'w') {
                    fieldsWithWhite.put(index, field);
                    index++;
                }
                if (toCheck == 'b') {
                    fieldsWithBlack.put(index, field);
                    index++;
                }
            }
        }
        board.setWhite(fieldsWithWhite);
        board.setBlack(fieldsWithBlack);

        for (int i = 0; i < fieldsWithWhite.size(); i++) {
            System.out.println(fieldsWithWhite.get(i));
        }
        System.out.println("po");
        return board;
    }
}
