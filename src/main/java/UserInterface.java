import java.io.Serializable;
import java.util.Scanner;

public class UserInterface implements Serializable {
    private static int boardSize;
    private String playerName;
    private int gameDifficulty;

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
    public void newGame() {
        chooseBoardSize();
        Game game = new Game(boardSize);
    }

    public static void main(String[] args) {
        new UserInterface();
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

    public int getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(int gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public void setOptions(UserInterface newOptions) {
        this.boardSize = newOptions.getBoardSize();
        this.playerName = newOptions.getPlayerName();
    }
}