import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static int boardSize;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
public Client() throws IOException {
    this.s = new Socket("localhost", 3333);
    this.din = new DataInputStream(s.getInputStream());
    this.dout = new DataOutputStream(s.getOutputStream());
}
    public static void main(String[] args) throws Exception {
        Client client = new Client();

//        EZGui ezGui = new EZGui();
        Scanner scanner = new Scanner(System.in);

        printMenu();
        int choice = scanner.nextInt();
//            choice = ezGui.init();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    //loadGame();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
                    break;
            }

        chooseBoardSize();
        Game game = new Game(boardSize);

        int rowStart;
        int columnStart;
        int rowMove;
        int columnMove;
        boolean turn;
        int index;

        List<Integer> indexList = new ArrayList<>();

        while (!game.getGameOver()) {
            boolean color = game.getPlayerTurn();
            turn = game.getPlayerTurn();
            Field endMove = null;
            String board = game.getBoard().displayBoard();
            client.dout.writeUTF(board);
            client.dout.flush();
            client.dout.writeBoolean(game.getPlayerTurn());
            client.dout.flush();
            if (game.getPlayerTurn()) {
                System.out.println("Ruch białych");
            } else {
                System.out.println("Ruch czarnych");
            }
            game.checkPossibleStartFields(game.getBoard());
            if (game.isPlayerTurn()) {
                System.out.println("Podaj litere kolumny, a potem numer rzedu wybranego pionka: \n");
            } else {
                String msg = "Podaj litere kolumny, a potem numer rzedu wybranego pionka: ";
                client.dout.writeUTF(msg);
                client.dout.flush();
            }
            while (true) {
                int id;
                if (game.isPlayerTurn()) {
                    columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    rowStart = scanner.nextInt() - 1;
                } else {
                    columnStart = client.din.readInt();
                    rowStart = client.din.readInt();
                }
                id = rowStart + 100 * columnStart;
                if (game.getPossibleStartFields().contains(id)) {
                    if (game.isPlayerTurn()) {
                        break;
                    } else {
                        client.dout.writeBoolean(true);
                        client.dout.flush();
                        break;
                    }
                }
                if (game.isPlayerTurn()) {
                    System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
                } else {
                    client.dout.writeBoolean(false);
                    client.dout.flush();
                }
            }
            game.getPossibleStartFields().clear();
            while (turn == game.getPlayerTurn()) {
                if (game.isPlayerTurn()) {
                    System.out.println("Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ");
                } else {
                    String msg = "Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ";
                    client.dout.writeUTF(msg);
                    client.dout.flush();
                }
                while (true) {
                    if (game.isPlayerTurn()) {
                        columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                        if (columnMove == 23) break;
                        rowMove = scanner.nextInt() - 1;
                    } else {
                        columnMove = client.din.readInt();
                        if (columnMove == 23) break;
                        rowMove = client.din.readInt();
                    }
                    index = rowMove + 100 * columnMove;
                    indexList.add(index);
                    if (indexList.size() == 1) {
                        endMove = game.getBoard().getFieldByIndex(rowMove, columnMove);
                    }
                }
                game.move(game.getBoard().getFieldByIndex(rowStart, columnStart), endMove, indexList, game.getBoard());
                indexList.clear();
                if (!color) {
                    boolean msgBool = turn == game.getPlayerTurn();
                    client.dout.writeBoolean(msgBool);
                    client.dout.flush();
                }
            }
            System.out.println(game.getBoard().displayBoard());
            if (game.getBoard().getWhite().isEmpty() || game.getBoard().getBlack().isEmpty()) {
                if (game.getBoard().getWhite().isEmpty()) {
                    game.setWinner(true);
                    System.out.println("Wygraly czarne");
                } else if (game.getBoard().getBlack().isEmpty()) {
                    game.setWinner(false);
                    System.out.println("Wygraly biale");
                }
                game.setGameOver(true);
            }
            if (game.isPlayerTurn()) {
                client.dout.writeBoolean(game.getGameOver());
                client.dout.flush();
            }
            if (game.getGameOver()) {
                client.din.close();
                client.dout.close();
            }
        }
    }

    public static void chooseBoardSize() {
        System.out.println("Choose board size: ");
        System.out.println("1. Small (8x8)");
        System.out.println("2. Classic (10x10)");
        System.out.println("3. Big (12x12)\n");

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    setBoardSize(8);
                    break;
                case 2:
                    setBoardSize(10);
                    break;
                case 3:
                    setBoardSize(12);
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
                    break;
            }
        } while (choice != 1 && choice != 2 && choice != 3);

    }

    public static void setBoardSize(int n) {
        boardSize = n;
    }

    public void setOptions(Client options) {
    }

    public static void printMenu() {
        System.out.println("Welcome to Checkers!");
        System.out.println("1. New game");
        System.out.println("2. Load game");
        System.out.println("3. Exit\n");
    }
}