import java.util.*;

public class Game {
    private int n; //wielkość planszy (n x n)
    private Field[] board; //tablica wszystkich pól
    private List<Piece> white; //lista białych pionków
    private List<Piece> black; //lista czarnych pionków
    private boolean playerTurn; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    public Game(int n) {
        this.n = n;
        board = new Field[(n * n + 1) / 2];
        white = new ArrayList<>();
        black = new ArrayList<>();
        playerTurn = true;
        scanner = new Scanner(System.in);

        // inicjalizacja planszy i pionków
        initializeBoard();
        //initializePieces();
        displayBoard();
    }

    public static void main(String[] args) {
        Game game = new Game(10);
    }

    private void initializeBoard() {
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    board[id-1] = new Field(id, i, j, null);
                    id++;
                }
            }
        }
    }

    private void displayBoard() {
        System.out.print(" ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();

        int id = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) {
                    Field field = getFieldById(id);
                    if (field.getPiece() == null) {
                        System.out.print(" .");
                    } else {
                        System.out.print(" " + field.getPiece().getSymbol());
                    }
                    id++;
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(" " + (i + 1));
        }

        System.out.print(" ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();
    }

    public Field getFieldById(int id) {
        for (Field field : board) {
            if (field.getId() == id) {
                return field;
            }
        }
        return null;
    }

    public int getN() {
        return n;
    }

    public Field[] getBoard() {
        return board;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}
