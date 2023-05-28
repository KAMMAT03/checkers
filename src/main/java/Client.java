import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static int boardSize;

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 3333);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        printMenu();
        int choice;
        do {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    continue;
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
        } while (choice != 1 && choice != 2 && choice != 3);

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
            turn = game.getPlayerTurn();
            Field endMove = null;
            String board = game.getBoard().displayBoard();
            dout.writeUTF(board);
            dout.flush();
            dout.writeBoolean(game.getPlayerTurn());
            dout.flush();
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
                dout.writeUTF(msg);
                dout.flush();
            }
            while (true) {
                int id;
                if (game.isPlayerTurn()) {
                    columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    rowStart = scanner.nextInt() - 1;
                } else {
                    columnStart = din.readInt();
                    rowStart = din.readInt();
                }
                id = rowStart + 100 * columnStart;
                if (game.getPossibleStartFields().contains(id)) {
                    if (game.isPlayerTurn()) {
                        break;
                    } else {
                        dout.writeBoolean(true);
                        dout.flush();
                        break;
                    }
                }
                if (game.isPlayerTurn()) {
                    System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
                }
            }
            game.getPossibleStartFields().clear();
            while (turn == game.getPlayerTurn()) {
                if (game.isPlayerTurn()) {
                    System.out.println("Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ");
                } else {
                    String msg = "Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ";
                    dout.writeUTF(msg);
                    dout.flush();
                }
                while (true) {
                    if (game.isPlayerTurn()) {
                        columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                        if (columnMove == 23) break;
                        rowMove = scanner.nextInt() - 1;
                    } else {
                        columnMove = din.readInt();
                        if (columnMove == 23) break;
                        rowMove = din.readInt();
                    }
                    index = rowMove + 100 * columnMove;
                    indexList.add(index);
                    if (indexList.size() == 1) {
                        endMove = game.getBoard().getFieldByIndex(rowMove, columnMove);
                    }
                }
                game.move(game.getBoard().getFieldByIndex(rowStart, columnStart), endMove, indexList, game.getBoard());
                indexList.clear();
                if (game.isPlayerTurn()) {
                    boolean msgBool = turn == game.getPlayerTurn();
                    dout.writeBoolean(msgBool);
                    dout.flush();
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
                dout.writeBoolean(game.getGameOver());
                dout.flush();
            }
            if (game.getGameOver()) {
                din.close();
                dout.close();
            }
        }
    }

    private static void chooseBoardSize() {
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