import java.util.*;

public class Game {


    private boolean playerTurn = true; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    private int n; //wielkość planszy (n x n)
    private Boolean gameOver = false;
    private int count;
    private int max;
    List<Integer> possibleMoves;
    Tree root;


    public Game(int n) {
        this.n = n;
        Board board = new Board(n);
        playerTurn = true;
        scanner = new Scanner(System.in);
        play(board);
    }
// W trakcie tworzenia

    public void play(Board board) {
        int xStart;
        int yStart;
        List<Integer> indexList = new ArrayList<>();

        while (!gameOver) {
            boolean turn = playerTurn;
            if (playerTurn) {
                System.out.println("Ruch białych");
            } else {
                System.out.println("Ruch czarnych");
            }
            System.out.println("Podaj współrzędną (x), a potem (y) wybranego pionka: ");
            xStart = scanner.nextInt();
            yStart = scanner.nextInt();
            while (turn == playerTurn){
                System.out.println("Podaj współrzędną (x), a potem (y) planowanych ruchow, a jesli koniec to x 99: ");
                while (true){
                    int xMove = scanner.nextInt();
                    if (xMove == 99) break;
                    int yMove = scanner.nextInt();
                    int index = xMove + 100 * yMove;
                    indexList.add(index);
                }
                move(board.getFieldByIndex(xStart,yStart), indexList, board, board.getWhite(), board.getBlack());
            }
            board.displayBoard();
        }
    }

    public static void main(String[] args) {
        Game game = new Game(20);//tylko dla parzystych n
    }

    void showPossibleMoves(Field start) {
        if (start.topLeft != null) {
            checkMove(start.topLeft, start.topLeft.topLeft, playerTurn);
        }
        if (start.topRight != null) {
            checkMove(start.topRight, start.topRight.topRight, playerTurn);
        }
        if (start.bottomLeft != null) {
            checkMove(start.bottomLeft, start.bottomLeft.bottomLeft, !playerTurn);
        }
        if (start.bottomRight != null) {
            checkMove(start.bottomRight, start.bottomRight.bottomRight, !playerTurn);
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
        if (start.topLeft.topLeft != null && start.topLeft.getIsOccupied()
                && !start.topLeft.topLeft.getIsOccupied() && start.topLeft.getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.topLeft.topLeft);
            parent.addChild(child);
            strike(start.topLeft.topLeft, child);
        }
        if (start.topRight.topRight != null && start.topRight.getIsOccupied()
                && !start.topRight.topRight.getIsOccupied() && start.topRight.getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.topRight.topRight);
            parent.addChild(child);
            strike(start.topRight.topRight, child);
        }
        if (start.bottomLeft.bottomLeft != null && start.bottomLeft.getIsOccupied()
                && !start.bottomLeft.bottomLeft.getIsOccupied() && start.bottomLeft.getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.bottomLeft.bottomLeft);
            parent.addChild(child);
            strike(start.bottomLeft.bottomLeft, child);
        }
        if (start.bottomRight.bottomRight != null && start.bottomRight.getIsOccupied()
                && !start.bottomRight.bottomRight.getIsOccupied() && start.bottomRight.getPiece().getColor() != playerTurn) {
            count++;
            if (count > max) max = count;
            Tree child = new Tree(start.bottomRight.bottomRight);
            parent.addChild(child);
            strike(start.bottomRight.bottomRight, child);
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


    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }


}
