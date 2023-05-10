import java.util.*;

public class Game {


    private boolean playerTurn = true; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    private int n; //wielkość planszy (n x n)
    private Boolean gameOver = false;

    public Game(int n) {
        this.n = n;
        Board board = new Board(n);
        playerTurn = true;
        scanner = new Scanner(System.in);
    }
// W trakcie tworzenia

//    public void play(Board board) {
//        int xStart;
//        int yStart;
//        int xEnd;
//        int yEnd;
//
//        while (gameOver == false) {
//            if (playerTurn == true) {
//                System.out.println("Ruch białych");
//                System.out.println("Podaj współrzędną (x) literową wybranego pionka: ");
//                xStart = scanner.nextInt();
//                System.out.println("Podaj współrzędną (y) numeryczną wybranego pionka: ");
//                yStart = scanner.nextInt();
//
//                System.out.println("Podaj współrzędną (x) literową gdzie chcesz się ruszyć: ");
//                xEnd = scanner.nextInt();
//                System.out.println("Podaj współrzędną (y) numeryczną gdzie chcesz się ruszyć: : ");
//                yEnd= scanner.nextInt();
//
//                move(board.getFieldByIndex(xStart,yStart), board.getFieldByIndex(xEnd,yEnd), List<List<board.getFieldByIndex(xStart,yStart)>> allPossibleMoves,board);
//
//
//                playerTurn = false;
//
//            } else {
//                System.out.println("Ruch czarnych");
//                playerTurn = true;
//            }
//        }
//    }

    public static void main(String[] args) {
        Game game = new Game(20);//tylko dla parzystych n
    }

    List<List<Field>> showPossibleMoves(Field start) {
        List<List<Field>> allPossibleMoves = new ArrayList<>();
        List<Field> possibleMoves = new ArrayList<>();
        List<Field> possibleStrikes = new ArrayList<>();
        allPossibleMoves.add(possibleMoves);
        allPossibleMoves.add(possibleStrikes);
        if (start.topLeft != null) {
            if (start.topLeft.getPiece() == null) {
                possibleMoves.add(start.topLeft);
            } else if (start.topLeft.topLeft != null && start.topLeft.topLeft.getPiece() == null) {
                possibleStrikes.add(start.topLeft.topLeft);
                start.topLeft.topLeft.setStriked(start.topLeft);
            }
        }
        if (start.topRight != null) {
            if (start.topRight.getPiece() == null) {
                possibleMoves.add(start.topRight);
            } else if (start.topRight.topRight != null && start.topRight.topRight.getPiece() == null) {
                possibleStrikes.add(start.topRight.topRight);
                start.topRight.topRight.setStriked(start.topRight);
            }
        }
        if (start.bottomLeft != null) {
            if (start.bottomLeft.getPiece() == null) {
                possibleMoves.add(start.bottomLeft);
            } else if (start.bottomLeft.bottomLeft != null && start.bottomLeft.bottomLeft.getPiece() == null) {
                possibleStrikes.add(start.bottomLeft.bottomLeft);
                start.bottomLeft.bottomLeft.setStriked(start.bottomLeft);
            }
        }
        if (start.bottomRight != null) {
            if (start.bottomRight.getPiece() == null) {
                possibleMoves.add(start.bottomRight);
            } else if (start.bottomRight.bottomRight != null && start.bottomRight.bottomRight.getPiece() == null) {
                possibleStrikes.add(start.bottomRight.bottomRight);
                start.bottomRight.bottomRight.setStriked(start.bottomRight);
            }
        }
        return allPossibleMoves;
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void move(Field start, Field end, List<List<Field>> allPossibleMoves, Board board) {  // Wykonuje ruch z pola start na pole end
        if (!allPossibleMoves.get(1).isEmpty()) {
            if (allPossibleMoves.get(1).contains(end)) {
                if (playerTurn) {
                    board.getWhite().remove(end.getStriked().getPiece().getId());
                } else {
                    board.getBlack().remove(end.getStriked().getPiece().getId());
                }
                end.setPiece(start.getPiece());
                start.setPiece(null);
                end.getStriked().setPiece(null);
            } else {
                return; //w tym miejscu powinien byc komunikat, ze nie wykonano obowiazkowego bicia i gracz powinien
                //wybrac inne pole
            }
        } else {
            if (allPossibleMoves.get(0).contains(end)) {
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


    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }


}
