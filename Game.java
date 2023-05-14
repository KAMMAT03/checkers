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
        int xStart;
        int yStart;
        int xMove;
        int yMove;
        int index;

        List<Integer> indexList = new ArrayList<>();

        while (!gameOver) {
            xMove = 0;


            if (playerTurn) {
                System.out.println("\nRuch białych \n");
                System.out.println("Podaj współrzędną (x), a potem (y) wybranego pionka: \n");

                xStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 64;
                yStart = scanner.nextInt();

                System.out.println("Podaj współrzędną (x), a potem (y) planowanych ruchow, a jesli koniec to wpisz 'X': \n");

                while (true){
                    xMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 64;
                    yMove = scanner.nextInt();
                    if (xMove == 24) break;
                    index = xMove + 100 * yMove;
                    indexList.add(index);
                }

                move(board.getFieldByIndex(xStart,yStart), indexList, board, board.getWhite(), board.getBlack());
                board.displayBoard();



            } else {
                System.out.println("Ruch czarnych");

                // analogicznie dla czarnych
            }
            board.displayBoard();
            playerTurn = !playerTurn;
            }
        }


    public static void main(String[] args) {
        Game game = new Game();
    }

    void showPossibleMoves(Field start) {
        if (start.getTopLeft() != null) {
            checkMove(start.getTopLeft(), start.getTopLeft().getTopLeft(), playerTurn);
        }
        if (start.getTopRight() != null) {
            checkMove(start.getTopRight(), start.getTopRight().getTopRight(), playerTurn);
        }
        if (start.getBottomLeft() != null) {
            checkMove(start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), !playerTurn);
        }
        if (start.getBottomRight() != null) {
            checkMove(start.getBottomRight(), start.getBottomRight().getBottomRight(), !playerTurn);
        }
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void checkMove(Field oneAway, Field twoAway, boolean color){
        if (!oneAway.getIsOccupied() && color) {
            possibleMoves.add(oneAway.getId());
        } else if (twoAway != null && !twoAway.getIsOccupied()
                && oneAway.getPiece().getColor() != playerTurn) {
            count++;
            max = count;
            root.setData(twoAway);
            twoAway.setStriked(oneAway);
            strike(twoAway, root);
        }
    }

    void strike(Field start, Tree parent){
        if (start.getTopLeft().getTopLeft() != null && start.getTopLeft().getIsOccupied()
                && !start.getTopLeft().getTopLeft().getIsOccupied() && start.getTopLeft().getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.getTopLeft().getTopLeft());
            parent.addChild(child);
            strike(start.getTopLeft().getTopLeft(), child);
        }
        if (start.getTopRight().getTopRight() != null && start.getTopRight().getIsOccupied()
                && !start.getTopRight().getTopRight().getIsOccupied() && start.getTopRight().getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.getTopRight().getTopRight());
            parent.addChild(child);
            strike(start.getTopRight().getTopRight(), child);
        }
        if (start.getBottomLeft().getBottomLeft() != null && start.getBottomLeft().getIsOccupied()
                && !start.getBottomLeft().getBottomLeft().getIsOccupied() && start.getBottomLeft().getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.getBottomLeft().getBottomLeft());
            parent.addChild(child);
            strike(start.getBottomLeft().getBottomLeft(), child);
        }
        if (start.getBottomRight().getBottomRight() != null && start.getBottomRight().getIsOccupied()
                && !start.getBottomRight().getBottomRight().getIsOccupied() && start.getBottomRight().getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.getBottomRight().getBottomRight());
            parent.addChild(child);
            strike(start.getBottomRight().getBottomRight(), child);
        }
    }

    void move(Field start, List<Integer> wantedMoves, Board board, Map<Integer, Piece> white, Map<Integer, Piece> black) {  // Wykonuje ruch z pola start na pole end
        possibleMoves = new ArrayList<>();
        List<Field> strikeFields = new ArrayList<>();
         root = new Tree(null);
        max = 0;
        count = 0;
        showPossibleMoves(start);
        Field end = root.getData();
        Tree temp = new Tree(root.getData());
        if (max > 0 && max == wantedMoves.size()) {
            if (temp.getData().getId() != wantedMoves.get(0)) return; // zle bicie wykonane, ponownie wybrac ruch
            strikeFields.add(temp.getData());
            for (int i = 1; i < wantedMoves.size(); i++){
                if (!temp.getChildrenData().contains(wantedMoves.get(i))) return; // zle bicie wykonane, ponownie wybrac ruch
                int index = temp.getChildrenData().indexOf(wantedMoves.get(i));
                temp = temp.getChildren().get(index);
                strikeFields.add(temp.getData());
            }
            for (Field f : strikeFields){
                end = f;
                end.setPiece(start.getPiece());
                if (playerTurn){
                    white.remove(end.getStriked().getPiece().getId());
                } else {
                    black.remove(end.getStriked().getPiece().getId());
                }
                end.getStriked().setPiece(null);
                end.setStriked(null);
                start.setPiece(null);
                start = f;
            }

        } else if (wantedMoves.size() == 1){
            if (possibleMoves.contains(wantedMoves.get(0))) {
                end.setPiece(start.getPiece());
                start.setPiece(null);
            } else {
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
