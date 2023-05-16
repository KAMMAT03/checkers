import java.util.*;

public class Game {


    private boolean playerTurn; //określa czyja jest tura (true - białe, false - czarne)
    private Scanner scanner; //scanner do pobierania danych od użytkownika

    private int n; //wielkość planszy (n x n)
    private Boolean gameOver = false;
    private int movesCount;
    private boolean winner;
    private int maxMovesCount;  // co to?
    private int maxForFields;

    private List<Integer> possibleStartFields;


    public Game() {

        System.out.println("Wybierz wielkość planszy: A - Mała  B - Klasyczna C - Duża \n");
        scanner = new Scanner(System.in);
        possibleStartFields = new ArrayList<>();
        switch (Character.toUpperCase(scanner.next().charAt(0))) {
            case 'A':
                setN(8);
                ;
                break;
            case 'B':
                setN(10);
                ;
                break;
            case 'C':
                setN(12);
                ;
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
            getPossibleStartFields(board);

            while (true) {
                columnStart = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                rowStart = scanner.nextInt() - 1;
                int id = rowStart + 100 * columnStart;
                if (possibleStartFields.contains(id)) break;
                System.out.println("Wybrano zly pionek, prosze wybrac ponownie");
            }
            possibleStartFields.clear();

            while (turn == playerTurn) {
                System.out.println("Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: ");
                while (true) {
                    columnMove = (int) Character.toUpperCase(scanner.next().charAt(0)) - 65;
                    if (columnMove == 23) break;
                    rowMove = scanner.nextInt() - 1;
                    index = rowMove + 100 * columnMove;
                    indexList.add(index);
                    if (indexList.size() == 1) {
                        endMove = board.getFieldByIndex(rowMove, columnMove);
                    }
                }
                move(board.getFieldByIndex(rowStart, columnStart), endMove, indexList, board);
                indexList.clear();
            }
            board.displayBoard();
            if (board.getWhite().isEmpty() || board.getBlack().isEmpty()) {
                if (board.getWhite().isEmpty()){
                    winner = true;
                    System.out.println("Wygraly czarne");
                } else if (board.getBlack().isEmpty()){
                    winner = false;
                    System.out.println("Wygraly biale");
                }
                gameOver = true;
            }
        }
    }

    void showPossibleMoves(Field start) {
        if (start.getTopLeft() != null) {
            checkMove(start, start.getTopLeft(), start.getTopLeft().getTopLeft(), !playerTurn);
            movesCount = 0;
        }
        if (start.getTopRight() != null) {
            checkMove(start, start.getTopRight(), start.getTopRight().getTopRight(), !playerTurn);
            movesCount = 0;
        }
        if (start.getBottomLeft() != null) {
            checkMove(start, start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), playerTurn);
            movesCount = 0;
        }
        if (start.getBottomRight() != null) {
            checkMove(start, start.getBottomRight(), start.getBottomRight().getBottomRight(), playerTurn);
            movesCount = 0;
        }
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void checkMove(Field start, Field oneAway, Field twoAway, boolean color) {
        if (!oneAway.getIsOccupied() && color) {
            start.addPossibleMoves(oneAway.getId());
        } else if (twoAway != null && !twoAway.getIsOccupied() && oneAway.getIsOccupied() && oneAway.getPiece().getColor() != playerTurn) {
            movesCount++;
            if (movesCount > maxMovesCount) maxMovesCount = movesCount;
            Tree temp = new Tree(twoAway);
            start.getRoot().addChild(temp);
            twoAway.setStriked(oneAway);
            twoAway.setVisited(true);
            canStrike(twoAway, temp);
        }
    }


    void canStrike(Field start, Tree parent) {
        if (start.getTopLeft() != null && start.getTopLeft().getTopLeft() != null && start.getTopLeft().getIsOccupied() && !start.getTopLeft().getTopLeft().getIsOccupied() && !start.getTopLeft().getTopLeft().isVisited() && start.getTopLeft().getPiece().getColor() != playerTurn) {
            Tree child = strike(start.getTopLeft(), start.getTopLeft().getTopLeft(), parent);
            canStrike(start.getTopLeft().getTopLeft(), child);
            movesCount--;
        }
        if (start.getTopRight() != null && start.getTopRight().getTopRight() != null && start.getTopRight().getIsOccupied() && !start.getTopRight().getTopRight().getIsOccupied() && !start.getTopRight().getTopRight().isVisited() && start.getTopRight().getPiece().getColor() != playerTurn) {
            Tree child = strike(start.getTopRight(), start.getTopRight().getTopRight(), parent);
            canStrike(start.getTopRight().getTopRight(), child);
            movesCount--;
        }
        if (start.getBottomLeft() != null && start.getBottomLeft().getBottomLeft() != null && start.getBottomLeft().getIsOccupied() && !start.getBottomLeft().getBottomLeft().getIsOccupied() && !start.getBottomLeft().getBottomLeft().isVisited() && start.getBottomLeft().getPiece().getColor() != playerTurn) {
            Tree child = strike(start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), parent);
            canStrike(start.getBottomLeft().getBottomLeft(), child);
            movesCount--;
        }
        if (start.getBottomRight() != null && start.getBottomRight().getBottomRight() != null && start.getBottomRight().getIsOccupied() && !start.getBottomRight().getBottomRight().getIsOccupied() && !start.getBottomRight().getBottomRight().isVisited() && start.getBottomRight().getPiece().getColor() != playerTurn) {
            Tree child = strike(start.getBottomRight(), start.getBottomRight().getBottomRight(), parent);
            canStrike(start.getBottomRight().getBottomRight(), child);
            movesCount--;
        }
    }

    Tree strike(Field oneAway, Field twoAway, Tree parent) {
        movesCount++;
        if (movesCount > maxMovesCount) maxMovesCount = movesCount;
        Tree child = new Tree(twoAway);
        twoAway.setVisited(true);
        twoAway.setStriked(oneAway);
        parent.addChild(child);
        return child;
    }

    void move(Field start, Field endMove, List<Integer> wantedMoves, Board board) {  // Wykonuje ruch z pola start na pole end
        List<Field> strikeFields = new ArrayList<>();
        maxMovesCount = 0;
        movesCount = 0;
        Field end;
        Tree temp = start.getRoot();
        if (maxForFields > 0 && maxForFields == wantedMoves.size()) {
            for (Integer wantedMove : wantedMoves) {
                if (!temp.getChildrenData().contains(wantedMove)) {// zle bicie wykonane, ponownie wybrac ruch
                    System.out.println("Wybrano zle pola, prosze wybrac ponownie");
                    return;
                }
                int index = temp.getChildrenData().indexOf(wantedMove);
                temp = temp.getChildren().get(index);
                strikeFields.add(temp.getData());
            }
            for (Field f : strikeFields) {
                end = f;
                f.setVisited(false);
                end.setPiece(start.getPiece());
                if (playerTurn) {
                    board.getBlack().remove(end.getStriked().getPiece().getId());
                    board.getFieldsWithBlack().remove(end.getStriked().getPiece().getId());
                    board.getFieldsWithWhite().replace(start.getPiece().getId(), end);
                } else {
                    board.getWhite().remove(end.getStriked().getPiece().getId());
                    board.getFieldsWithWhite().remove(end.getStriked().getPiece().getId());
                    board.getFieldsWithBlack().replace(start.getPiece().getId(), end);
                }
                end.getStriked().setPiece(null);
                end.setStriked(null);
                start.setPiece(null);
                start = f;
            }
            board.clearVisited();
            start.getRoot().clearStriked(start.getRoot());
        } else if (wantedMoves.size() == 1) {
            if (start.getPossibleMoves().contains(wantedMoves.get(0))) {
                endMove.setPiece(start.getPiece());
                if (playerTurn) {
                    board.getFieldsWithWhite().replace(start.getPiece().getId(), endMove);
                } else {
                    board.getFieldsWithBlack().replace(start.getPiece().getId(), endMove);
                }
                start.setPiece(null);
            } else {
                System.out.println("Niepoprawny ruch, prosze wybrac inne pole");
                return; //w tym miejscu powinien byc komunikat, ze nie mozna wykonac takiego ruchu i gracz powinien
                //wybrac inne pole
            }
        }
        playerTurn = !playerTurn;
    }

    void getPossibleStartFields(Board board) {
        maxForFields = 0;
        if (playerTurn) {
            for (Field f : board.getFieldsWithWhite().values()) {
                possibleStartLoop(f);
            }
        } else {
            for (Field f : board.getFieldsWithBlack().values()) {
                possibleStartLoop(f);
            }
        }
    }

    private void possibleStartLoop(Field f) {
        showPossibleMoves(f);
        if (maxMovesCount > maxForFields) {
            maxForFields = maxMovesCount;
            possibleStartFields.clear();
            possibleStartFields.add(f.getId());
        } else if (maxMovesCount == maxForFields) {
            possibleStartFields.add(f.getId());
        }
        maxMovesCount = 0;
        movesCount = 0;
    }

    public boolean fieldContainsPlayerPiece(Field field) {
        if (field.getIsOccupied() && playerTurn == field.getPiece().getColor()) {
            return true;
        } else {
            return false;
        }
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
