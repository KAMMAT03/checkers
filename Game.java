import java.util.*;

public class Game {
    private int n; //wielkość planszy (n x n)
    private Field[] board; //tablica wszystkich pól
    private Map<Integer, Piece> white; //lista białych pionków
    private Map<Integer, Piece> black; //lista czarnych pionków
    private boolean playerTurn; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    public Game(int n) {
        this.n = n;
        board = new Field[(n * n + 1) / 2];
        white = new HashMap<>();
        black = new HashMap<>();
        playerTurn = true;
        scanner = new Scanner(System.in);

        // inicjalizacja planszy i pionków
        initializeBoard();
        initializePieces();
        displayBoard();
    }



    public static void main(String[] args) {
        Game game = new Game(20);//tylko dla parzystych n
    }
    private void initializePieces() {
        for (int i = 0; i < n / 2 - 1; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(true, i, j); //tworzenie nowego pionka białego
                    white.put(piece.getId(), piece); //dodanie pionka do listy białych pionków
                    getFieldByIndex(i,j).setPiece(piece); //ustawienie pionka na planszy
                }
            }
        }
        for (int i = n / 2 + 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    Piece piece = new Piece(false, i, j); //tworzenie nowego pionka czarnego
                    black.put(piece.getId(), piece); //dodanie pionka do listy czarnych pionków
                    getFieldByIndex(i,j).setPiece(piece); //ustawienie pionka na planszy
                }
            }
        }
    }
    private void initializeBoard() {
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) { //tworzenie tylko nieparzystych pól planszy
                    board[id-1] = new Field(i, j, null);
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
                    Field field = getFieldByIndex(i,j);
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
            if(i+1<10) {
                System.out.println("  " + (i + 1) +" ");
            }
            else {
                System.out.println(" " +(i + 1));
            }
        }

        System.out.print(" ");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();
    }


    public Field getFieldByIndex(int i, int j) {
        for (Field field : board) {
            if (field.getI() == i && field.getJ() == j) {
                return field;
            }
        }
        return null;
    }

    List<List<Field>> showPossibleMoves(Field start){
        List<List<Field>> allPossibleMoves = new ArrayList<>();
        List<Field> possibleMoves = new ArrayList<>();
        List<Field> possibleStrikes = new ArrayList<>();
        allPossibleMoves.add(possibleMoves);
        allPossibleMoves.add(possibleStrikes);
        if (start.topLeft != null){
            if (start.topLeft.getPiece() == null){
                possibleMoves.add(start.topLeft);
            } else if (start.topLeft.topLeft != null && start.topLeft.topLeft.getPiece() == null){
                possibleStrikes.add(start.topLeft.topLeft);
                start.topLeft.topLeft.setStriked(start.topLeft);
            }
        }
        if (start.topRight != null){
            if (start.topRight.getPiece() == null){
                possibleMoves.add(start.topRight);
            } else if (start.topRight.topRight != null && start.topRight.topRight.getPiece() == null){
                possibleStrikes.add(start.topRight.topRight);
                start.topRight.topRight.setStriked(start.topRight);
            }
        }
        if (start.bottomLeft != null){
            if (start.bottomLeft.getPiece() == null){
                possibleMoves.add(start.bottomLeft);
            } else if (start.bottomLeft.bottomLeft != null && start.bottomLeft.bottomLeft.getPiece() == null){
                possibleStrikes.add(start.bottomLeft.bottomLeft);
                start.bottomLeft.bottomLeft.setStriked(start.bottomLeft);
            }
        }
        if (start.bottomRight != null){
            if (start.bottomRight.getPiece() == null){
                possibleMoves.add(start.bottomRight);
            } else if (start.bottomRight.bottomRight != null && start.bottomRight.bottomRight.getPiece() == null){
                possibleStrikes.add(start.bottomRight.bottomRight);
                start.bottomRight.bottomRight.setStriked(start.bottomRight);
            }
        }
        return allPossibleMoves;
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void move(Field start,Field end, List<List<Field>> allPossibleMoves){  // Wykonuje ruch z pola start na pole end
        if (!allPossibleMoves.get(1).isEmpty()){
            if (allPossibleMoves.get(1).contains(end)){
                if (playerTurn){
                    white.remove(end.getStriked().getPiece().getId());
                } else {
                    black.remove(end.getStriked().getPiece().getId());
                }
                end.setPiece(start.getPiece());
                start.setPiece(null);
                end.getStriked().setPiece(null);
            } else {
                return; //w tym miejscu powinien byc komunikat, ze nie wykonano obowiazkowego bicia i gracz powinien
                //wybrac inne pole
            }
        } else {
            if (allPossibleMoves.get(0).contains(end)){
                end.setPiece(start.getPiece());
                start.setPiece(null);
            } else {
                return; //w tym miejscu powinien byc komunikat, ze nie mozna wykonac takiego ruchu i gracz powinien
                //wybrac inne pole
            }
        }
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
