import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {

    private int n; //wielkość planszy (n x n)
    private Field[][] board; //tablica wszystkich pól

    private Map<Integer, Piece> white; //lista białych pionków
    private Map<Integer, Piece> black; //lista czarnych pionków
    private Map<Integer, Field> fieldsWithWhite;
    private Map<Integer, Field> fieldsWithBlack;

    public Board(int n) {
        this.n = n;
        board = new Field[n][n];
        white = new HashMap<>();
        black = new HashMap<>();
        fieldsWithWhite = new HashMap<>();
        fieldsWithBlack = new HashMap<>();

        initializeBoard();
        initializePieces();
        displayBoard();

    }

    public Piece getPiece(int x, int y) {
        return board[x][y].getPiece();
    }

    private void initializeBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Field tempField = new Field(i, j, null);
                    board[i][j] = tempField;
                    if (i == 0){
                        tempField.setBlackDame(true);
                    } else if (i == n - 1){
                        tempField.setWhiteDame(true);
                    }
                }
            }
        }

        // Ustawienie referencji topLeft, topRight, bottomLeft, bottomRight dla pól planszy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != null) {
                    if (i > 0 && j > 0) {
                        board[i][j].setTopLeft(board[i - 1][j - 1]);
                    }
                    if (i > 0 && j < n - 1) {
                        board[i][j].setTopRight(board[i - 1][j + 1]);
                    }
                    if (i < n - 1 && j > 0) {
                        board[i][j].setBottomLeft(board[i + 1][j - 1]);
                    }
                    if (i < n - 1 && j < n - 1) {
                        board[i][j].setBottomRight(board[i + 1][j + 1]);
                    }
                }
            }
        }
    }

//

    private void initializePieces() {
        for (int i = 0; i < n / 2 - 1; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(true, i, j); //tworzenie nowego pionka białego
                    white.put(piece.getId(), piece); //dodanie pionka do listy białych pionków
                    getFieldByIndex(i, j).setPiece(piece); //ustawienie pionka na planszy
                    fieldsWithWhite.put(piece.getId(), getFieldByIndex(i, j));
                }
            }
        }
        for (int i = n / 2 + 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(false, i, j); //tworzenie nowego pionka czarnego
                    black.put(piece.getId(), piece); //dodanie pionka do listy czarnych pionków
                    getFieldByIndex(i, j).setPiece(piece); //ustawienie pionka na planszy
                    fieldsWithBlack.put(piece.getId(), getFieldByIndex(i, j));
                }
            }
        }
    }

    public Map<Integer, Field> getFieldsWithWhite() {
        return fieldsWithWhite;
    }

    public Map<Integer, Field> getFieldsWithBlack() {
        return fieldsWithBlack;
    }

    public Field getFieldByIndex(int i, int j) {
        return board[i][j];
    }

    public Field[][] getBoard() {
        return board;
    }

    public Map<Integer, Piece> getWhite() {
        return white;
    }

    public Map<Integer, Piece> getBlack() {
        return black;
    }

    void clearVisited() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j].setVisited(false);
                    board[i][j].getRoot().reset();
                }
            }
        }
    }

    void displayBoard() {
        System.out.print("  ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();

        int id = 1;

        for (int i = 0; i < n; i++) {
            if (i + 1 < 10) {
                System.out.print(i + 1 + " ");
            } else {
                System.out.print(i + 1);
            }
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) {
                    Field field = getFieldByIndex(i, j);
                    if (field.getPiece() == null) {
                        System.out.print(" .");
                    } else {
                        System.out.print(" " + field.getPiece().getSymbol());
                    }
                } else {
                    System.out.print("  ");
                }
            }

                System.out.println("  " + (i + 1) + " ");

        }

        System.out.print("  ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();

    }


}
