import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface implements Serializable {
    private static int boardSize;
    private String playerName;

    public UserInterface() {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int choice = scanner.nextInt();
        do {
            switch (choice) {
                case 1:
                    newGame();
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
        } while (choice != 1 && choice != 2 && choice != 3);
    }

    public static void printMenu() {
        System.out.println("Welcome to Checkers!");
        System.out.println("1. New game");
        System.out.println("2. Load game");
        System.out.println("3. Exit\n");
    }

    public static void chooseBoardSize() {
        System.out.println("Choose board size: ");
        System.out.println("1. Small (8x8)");
        System.out.println("2. Classic (10x10)");
        System.out.println("3. Big (12x12)\n");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        do {
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

    public static void main(String[] args) {
        new UserInterface();
    }

    public void newGame() {
        chooseBoardSize();
        Game game = new Game(boardSize);

        int rowStart;
        int columnStart;
        int rowMove;
        int columnMove;
        boolean turn;
        int index;

        List<Integer> indexList = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);

        while (!game.getGameOver()) {
            turn = game.getPlayerTurn();
            Field endMove = null;

            if (game.getPlayerTurn()) {
                System.out.println("Ruch białych");
            } else {
                System.out.println("Ruch czarnych");
            }
            System.out.println("Podaj litere kolumny, a potem numer rzedu wybranego pionka: \n");
            game.ckeckPossibleStartFields(game.getBoard());

            while (true) {
                columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                rowStart = scanner.nextInt() - 1;
                int id = rowStart + 100 * columnStart;
                if (game.getPossibleStartFields().contains(id)) break;
                System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
            }
            game.getPossibleStartFields().clear();


            while (turn == game.getPlayerTurn()) {
                System.out.println("Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ");
                while (true) {
                    columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    if (columnMove == 23) break;
                    rowMove = scanner.nextInt() - 1;
                    index = rowMove + 100 * columnMove;
                    indexList.add(index);
                    if (indexList.size() == 1) {
                        endMove = game.getBoard().getFieldByIndex(rowMove, columnMove);
                    }
                }
                game.move(game.getBoard().getFieldByIndex(rowStart, columnStart), endMove, indexList, game.getBoard());
                indexList.clear();
            }
            game.getBoard().displayBoard();
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

        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public static void setBoardSize(int n) {
        boardSize = n;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setOptions(UserInterface newOptions) {
        boardSize = newOptions.getBoardSize();
        playerName = newOptions.getPlayerName();
    }
}