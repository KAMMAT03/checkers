import java.util.HashMap;
import java.util.Map;

public class Board {

    private int n; //wielkość planszy (n x n)
    private Field[] board; //tablica wszystkich pól

    private Map<Integer, Piece> white; //lista białych pionków
    private Map<Integer, Piece> black; //lista czarnych pionków

    public Board(int n) {
        this.n = n;
        board = new Field[n * n];
        white = new HashMap<>();
        black = new HashMap<>();
        // inicjalizacja planszy i pionków
        initializeBoard();
        initializePieces();
        displayBoard();

    }

    private void initializeBoard() {
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    board[id - 1] = new Field(i, j, null);
                    id++;
                }

            }
        }
    }

    private void initializePieces() {
        for (int i = 0; i < n / 2 - 1; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(true, i, j); //tworzenie nowego pionka białego
                    white.put(piece.getId(), piece); //dodanie pionka do listy białych pionków
                    getFieldByIndex(i, j).setPiece(piece); //ustawienie pionka na planszy
                }
            }
        }
        for (int i = n / 2 + 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(false, i, j); //tworzenie nowego pionka czarnego
                    black.put(piece.getId(), piece); //dodanie pionka do listy czarnych pionków
                    getFieldByIndex(i, j).setPiece(piece); //ustawienie pionka na planszy
                }
            }
        }
    }

    public Field getFieldByIndex(int i, int j) {
        for (Field field : board) {
            if (field.getI() == i && field.getJ() == j) {
                return field;
            }
        }
        return null;
    }

    public Field[] getBoard() {
        return board;
    }

    public Map<Integer, Piece> getWhite() {
        return white;
    }

    public Map<Integer, Piece> getBlack() {
        return black;
    }

     void displayBoard() {
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
                    Field field = getFieldByIndex(i, j);
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
            if (i + 1 < 10) {
                System.out.println("  " + (i + 1) + " ");
            } else {
                System.out.println(" " + (i + 1));
            }
        }

        System.out.print(" ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();
    }
}
