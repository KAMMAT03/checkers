import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {


    private final Board board;
    private final List<Integer> possibleStartFields;
    private boolean playerTurn; //określa czyja jest tura (true - białe, false - czarne)
    private int n; //wielkość planszy (n x n)
    private boolean gameOver;
    private int movesCount;
    private boolean winner;
    private int maxMovesCount;
    private int maxForFields;


    public Game(int n) {
        possibleStartFields = new ArrayList<>();
        gameOver = false;
        this.n = n;
        board = new Board(n);
        playerTurn = true;
    }

    void showPossibleMoves(Field start) {
        if (start.getTopLeft() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, !playerTurn, start.getRoot(), 1, null);
            } else {
                checkMove(start, start.getTopLeft(), start.getTopLeft().getTopLeft(), !playerTurn);
            }
            movesCount = 0;
        }
        if (start.getTopRight() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, !playerTurn, start.getRoot(), 2, null);
            } else {
                checkMove(start, start.getTopRight(), start.getTopRight().getTopRight(), !playerTurn);
            }
            movesCount = 0;
        }
        if (start.getBottomLeft() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, !playerTurn, start.getRoot(), 3, null);
            } else {
                checkMove(start, start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), playerTurn);
            }
            movesCount = 0;
        }
        if (start.getBottomRight() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, !playerTurn, start.getRoot(), 4, null);
            } else {
                checkMove(start, start.getBottomRight(), start.getBottomRight().getBottomRight(), playerTurn);
            }
            checkMove(start, start.getBottomRight(), start.getBottomRight().getBottomRight(), playerTurn);
            movesCount = 0;
        }
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy.
      // Zwraca listę referencji do pól na które możemy się poruszyć.

    void checkDameMove(Field current, boolean color, Tree parent, int direction, Tree child){
        switch (direction) {
            case 1 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, color, direction, child);
                }
                if (current.getTopLeft() != null && !current.getTopLeft().getIsOccupied()) {
                    Tree newChild = new Tree(current.getTopLeft());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getTopLeft().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getTopLeft(), color, parent, direction, newChild);
                } else if (current.getTopLeft() != null && current.getTopLeft().getTopLeft() != null
                        && !current.getTopLeft().getTopLeft().getIsOccupied()
                        && current.getTopLeft().getIsOccupied()
                        && current.getTopLeft().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    Tree newChild = new Tree(current.getTopLeft().getTopLeft());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getTopLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getTopLeft().getTopLeft(), color, parent, direction, newChild);
                }
            }
            case 2 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, color, direction, child);
                }
                if (current.getTopRight() != null && !current.getTopRight().getIsOccupied()) {
                    Tree newChild = new Tree(current.getTopRight());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getTopRight().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getTopRight(), color, parent, direction, newChild);
                } else if (current.getTopRight() != null && current.getTopRight().getTopRight() != null
                        && !current.getTopRight().getTopRight().getIsOccupied()
                        && current.getTopRight().getIsOccupied()
                        && current.getTopRight().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    Tree newChild = new Tree(current.getTopRight().getTopRight());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getTopRight());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getTopRight().getTopRight(), color, parent, direction, newChild);
                }
            }
            case 3 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, color, direction, child);
                }
                if (current.getBottomRight() != null && !current.getBottomRight().getIsOccupied()) {
                    Tree newChild = new Tree(current.getBottomRight());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getBottomRight().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getBottomRight(), color, parent, direction, newChild);
                } else if (current.getBottomRight() != null && current.getBottomRight().getBottomRight() != null
                        && !current.getBottomRight().getBottomRight().getIsOccupied()
                        && current.getBottomRight().getIsOccupied()
                        && current.getBottomRight().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    Tree newChild = new Tree(current.getBottomRight().getBottomRight());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getBottomRight());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getBottomRight().getBottomRight(), color, parent, direction, newChild);
                }
            }
            case 4 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, color, direction, child);
                }
                if (current.getBottomLeft() != null && !current.getBottomLeft().getIsOccupied()) {
                    Tree newChild = new Tree(current.getBottomLeft());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getBottomLeft().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getBottomLeft(), color, parent, direction, newChild);
                } else if (current.getBottomLeft() != null && current.getBottomLeft().getBottomLeft() != null
                        && !current.getBottomLeft().getBottomLeft().getIsOccupied()
                        && current.getBottomLeft().getIsOccupied()
                        && current.getBottomLeft().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    Tree newChild = new Tree(current.getBottomLeft().getBottomLeft());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getBottomLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getBottomLeft().getBottomLeft(), color, parent, direction, newChild);
                }
            }
        }
    }
    void showPossibleDameMoves(Field current, boolean color, int direction, Tree parent){
        Field tempField = current;
        if (direction == 1 || direction == 3){
            while (tempField.getTopRight() != null) {
                if (!tempField.getTopRight().getIsOccupied()){
                    tempField = tempField.getTopRight();
                } else if (tempField.getTopRight().getPiece().getColor() != playerTurn
                        && tempField.getTopRight().getTopRight() != null
                        && !tempField.getTopRight().getTopRight().getIsOccupied()){
                    Tree newChild  = new Tree(tempField.getTopRight().getTopRight());
                    parent.addChild(newChild);
                    checkDameMove(tempField.getTopRight().getTopRight(), color, parent, 2, newChild);
                } else break;
            }
            tempField = current;
            while (tempField.getBottomLeft() != null) {
                if (!tempField.getBottomLeft().getIsOccupied()){
                    tempField = tempField.getBottomLeft();
                } else if (tempField.getBottomLeft().getPiece().getColor() != playerTurn
                        && tempField.getBottomLeft().getBottomLeft() != null
                        && !tempField.getBottomLeft().getBottomLeft().getIsOccupied()){
                    Tree newChild  = new Tree(tempField.getBottomLeft().getBottomLeft());
                    parent.addChild(newChild);
                    checkDameMove(tempField.getBottomLeft().getBottomLeft(), color, parent, 4, newChild);
                } else break;
            }
        } else if (direction == 2 || direction == 4){
            while (tempField.getTopLeft() != null) {
                if (!tempField.getTopLeft().getIsOccupied()){
                    tempField = tempField.getTopLeft();
                } else if (tempField.getTopLeft().getPiece().getColor() != playerTurn
                        && tempField.getTopLeft().getTopLeft() != null
                        && !tempField.getTopLeft().getTopLeft().getIsOccupied()){
                    Tree newChild  = new Tree(tempField.getTopLeft().getTopLeft());
                    parent.addChild(newChild);
                    checkDameMove(tempField.getTopLeft().getTopLeft(), color, parent, 1, newChild);
                } else break;
            }
            tempField = current;
            while (tempField.getBottomRight() != null) {
                if (!tempField.getBottomRight().getIsOccupied()){
                    tempField = tempField.getBottomRight();
                } else if (tempField.getBottomRight().getPiece().getColor() != playerTurn
                        && tempField.getBottomRight().getBottomRight() != null
                        && !tempField.getBottomRight().getBottomRight().getIsOccupied()){
                    Tree newChild  = new Tree(tempField.getBottomRight().getBottomRight());
                    parent.addChild(newChild);
                    checkDameMove(tempField.getBottomRight().getBottomRight(), color, parent, 3, newChild);
                } else break;
            }
        }
    }

    void checkMove(Field start, Field oneAway, Field twoAway, boolean color) {
        if (!oneAway.getIsOccupied() && color) {
            start.addPossibleMoves(oneAway.getId());
        } else if (twoAway != null && !twoAway.getIsOccupied() && oneAway.getIsOccupied()
                && oneAway.getPiece().getColor() != playerTurn) {
            movesCount++;
            if (movesCount > maxMovesCount) maxMovesCount = movesCount;
            Tree temp = new Tree(twoAway);
            start.getRoot().addChild(temp);
            twoAway.setStriked(oneAway);
            twoAway.setVisited(true);
            canStrike(twoAway, temp);
        }
    }

    public void makeMove(String piece, String move) {
        List<Integer> wantedMoves = new ArrayList<>();

        if (move.length() == 2 && piece.length() == 2) {
                int rowIndex = move.charAt(0)-96;
                int colIndex = move.charAt(1)-48;
                wantedMoves.add(board.getFieldByIndex(rowIndex, colIndex).getId());
        } else {
                throw new IllegalArgumentException("Nieprawidłowy format ruchu: " + piece);
            }

        Field start = board.getFieldByIndex(piece.charAt(0)-96,
                piece.charAt(1)-48);
        Field end = board.getFieldByIndex(move.charAt(0)-96,
                move.charAt(1)-48);

        if (!this.move(start, end, wantedMoves, board)) {
            throw new RuntimeException("Nie udało się wykonać ruchu");
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

    public boolean move(Field start, Field endMove, List<Integer> wantedMoves, Board board) {  // Wykonuje ruch z pola start na pole end
        List<Field> strikeFields = new ArrayList<>();
        maxMovesCount = 0;
        movesCount = 0;
        Field end;
        Tree temp = start.getRoot();
        if (maxForFields > 0 && maxForFields == wantedMoves.size()) {
            for (Integer wantedMove : wantedMoves) {
                if (!temp.getChildrenData().contains(wantedMove)) {// zle bicie wykonane, ponownie wybrac ruch
//                    System.out.println("Wybrano zle pola, prosze wybrac ponownie");
                    return false;
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
                return false; //w tym miejscu powinien byc komunikat, ze nie mozna wykonac takiego ruchu i gracz powinien
                //wybrac inne pole
            }
        }
        playerTurn = !playerTurn;
        return true;
    }

    public List<Integer> getPossibleStartFields() {
        return possibleStartFields;
    }

    void ckeckPossibleStartFields(Board board) {
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
        if (f.getPiece().getIsDame()){
            maxMovesCount = f.getRoot().getMaxDepth() - 1;
            if (!f.getRoot().isAfterStrike()){
                f.setPossibleMoves(f.getRoot().getChildrenData());
            }
        }
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean getPlayerTurn() {
        return playerTurn;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getWinner() {
        return winner;
    }

    public void displayBoard() {
        board.displayBoard();
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

}
