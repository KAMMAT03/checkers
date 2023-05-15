import java.util.*;

public class Game {


    private boolean playerTurn = true; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    private int n; //wielkość planszy (n x n)
    private Boolean gameOver = false;
    private int count;
    private int max;  // co to?
    List<Integer> possibleMoves;
    Tree root;

    public Game() {

        System.out.println("Wybierz wielkość planszy: A - Mała  B - Klasyczna C - Duża \n");
        scanner = new Scanner(System.in);
        switch (Character.toUpperCase(scanner.next().charAt(0))) {
            case 'A':
                setN(8);;
                break;
            case 'B':
                setN(10);;
                break;
            case 'C':
                setN(12);;
                break;
            default:
                System.out.println("Niepoprawny wybór, wybieram planszę klasyczną");
                setN(10);
                break;
        }

        Board board = new Board(n);
        playerTurn = true;
        scanner = new Scanner(System.in);
        play(board);
    }


    public void play(Board board) {
        int rowStart;
        int columnStart;
        int rowMove;
        int columnMove;
        boolean turn;
        int index;

        List<Integer> indexList = new ArrayList<>();

        while (!gameOver) {
            turn = playerTurn;
            Field endMove = null;

            if (playerTurn) {
                System.out.println("Ruch białych");
            } else {
                System.out.println("Ruch czarnych");
            }
            System.out.println("Podaj litere kolumny, a potem numer rzedu wybranego pionka: \n");

            columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
            rowStart = scanner.nextInt() - 1;
            while (turn == playerTurn){
                System.out.println("Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ");
                while (true){
                    columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    if (columnMove == 23) break;
                    rowMove = scanner.nextInt() - 1;
                    index = rowMove + 100 * columnMove;
                    indexList.add(index);
                    if (indexList.size() == 1) {
                        endMove = board.getFieldByIndex(rowMove, columnMove);
                    }
                }
                move(board.getFieldByIndex(rowStart,columnStart), endMove, indexList, board, board.getWhite(), board.getBlack());
                indexList.clear();
            }
            board.displayBoard();
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
    }

    void showPossibleMoves(Field start) {
        if (start.getTopLeft() != null) {
            checkMove(start.getTopLeft(), start.getTopLeft().getTopLeft(), !playerTurn);
            count = 0;
        }
        if (start.getTopRight() != null) {
            checkMove(start.getTopRight(), start.getTopRight().getTopRight(), !playerTurn);
            count = 0;
        }
        if (start.getBottomLeft() != null) {
            checkMove(start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), playerTurn);
            count = 0;
        }
        if (start.getBottomRight() != null) {
            checkMove(start.getBottomRight(), start.getBottomRight().getBottomRight(), playerTurn);
            count = 0;
        }
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void checkMove(Field oneAway, Field twoAway, boolean color){
        if (!oneAway.getIsOccupied() && color) {
            possibleMoves.add(oneAway.getId());
        } else if (twoAway != null && !twoAway.getIsOccupied()
                && oneAway.getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree temp = new Tree(twoAway);
            root.addChild(temp);
            twoAway.setStriked(oneAway);
            strike(twoAway, temp);
        }
    }

    void canStrike(Field start, Tree parent){
        if (start.getTopLeft().getTopLeft() != null && start.getTopLeft().getIsOccupied()
                && !start.getTopLeft().getTopLeft().getIsOccupied() && start.getTopLeft().getPiece().getColor() != playerTurn){
            strike(start.getTopLeft().getTopLeft(), parent);
            count--;
        }
        if (start.getTopRight().getTopRight() != null && start.getTopRight().getIsOccupied()
                && !start.getTopRight().getTopRight().getIsOccupied() && start.getTopRight().getPiece().getColor() != playerTurn){
            strike(start.getTopRight().getTopRight(),parent);
            count--;
        }
        if (start.getBottomLeft().getBottomLeft() != null && start.getBottomLeft().getIsOccupied()
                && !start.getBottomLeft().getBottomLeft().getIsOccupied() && start.getBottomLeft().getPiece().getColor() != playerTurn){
            strike(start.getBottomLeft().getBottomLeft(), parent);
            count--;
        }
        if (start.getBottomRight().getBottomRight() != null && start.getBottomRight().getIsOccupied()
                && !start.getBottomRight().getBottomRight().getIsOccupied() && start.getBottomRight().getPiece().getColor() != playerTurn){
            strike(start.getBottomRight().getBottomRight(), parent);
            count--;
        }
    }
    void strike(Field twoAway, Tree parent){
        count++;
        if (count > max) max = count;
        Tree child = new Tree(twoAway);
        parent.addChild(child);
        canStrike(twoAway, child);
    }

    void move(Field start, Field endMove, List<Integer> wantedMoves, Board board, Map<Integer, Piece> white, Map<Integer, Piece> black) {  // Wykonuje ruch z pola start na pole end
        possibleMoves = new ArrayList<>();
        List<Field> strikeFields = new ArrayList<>();
        root = new Tree(start);
        max = 0;
        count = 0;
        showPossibleMoves(start);
        Field end;
        Tree temp = root;
        if (max > 0 /*&& max == wantedMoves.size()*/) {
            for (int i = 0; i < wantedMoves.size(); i++) {
                if (!temp.getChildrenData().contains(wantedMoves.get(i))) {// zle bicie wykonane, ponownie wybrac ruch
                    System.out.println("Wybrano zle pola, prosze wybrac ponownie");
                    return;
                }
                int index = temp.getChildrenData().indexOf(wantedMoves.get(i));
                temp = temp.getChildren().get(index);
                strikeFields.add(temp.getData());
            }
            for (Field f : strikeFields) {
                end = f;
                end.setPiece(start.getPiece());
                if (playerTurn) {
                    black.remove(end.getStriked().getPiece().getId());
                } else {
                    white.remove(end.getStriked().getPiece().getId());
                }
                end.getStriked().setPiece(null);
                end.setStriked(null);
                start.setPiece(null);
                start = f;
            }
            root.clearStriked(root);
        } else if (possibleMoves.size() == 0){
            System.out.println("zly ruch");
            return;
        } else if (wantedMoves.size() == 1){
            if (possibleMoves.contains(wantedMoves.get(0))) {
                endMove.setPiece(start.getPiece());
                start.setPiece(null);
            } else {
                System.out.println("Niepoprawny ruch, prosze wybrac inne pole");
                return; //w tym miejscu powinien byc komunikat, ze nie mozna wykonac takiego ruchu i gracz powinien
                //wybrac inne pole
            }
        }
        playerTurn = !playerTurn;
    }

    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }


    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }


}
