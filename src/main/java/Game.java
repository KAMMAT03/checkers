import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return playerTurn == game.playerTurn && n == game.n && gameOver == game.gameOver && movesCount == game.movesCount && winner == game.winner && maxMovesCount == game.maxMovesCount && maxForFields == game.maxForFields && Objects.equals(board, game.board) && Objects.equals(possibleStartFields, game.possibleStartFields);
    }


    private void showPossibleMoves(Field start) {
        if (start.getTopLeft() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, start.getRoot(), 1, null);
            } else {
                checkMove(start, start.getTopLeft(), start.getTopLeft().getTopLeft(), !playerTurn);
            }
            movesCount = 0;
        }
        if (start.getTopRight() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, start.getRoot(), 2, null);
            } else {
                checkMove(start, start.getTopRight(), start.getTopRight().getTopRight(), !playerTurn);
            }
            movesCount = 0;
        }
        if (start.getBottomLeft() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, start.getRoot(), 3, null);
            } else {
                checkMove(start, start.getBottomLeft(), start.getBottomLeft().getBottomLeft(), playerTurn);
            }
            movesCount = 0;
        }
        if (start.getBottomRight() != null) {
            if (start.getPiece().getIsDame()) {
                checkDameMove(start, start.getRoot(), 4, null);
            } else {
                checkMove(start, start.getBottomRight(), start.getBottomRight().getBottomRight(), playerTurn);
            }
            movesCount = 0;
        }
    } // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy.
      // Zwraca listę referencji do pól na które możemy się poruszyć.

    private void checkDameMove(Field current, Tree parent, int direction, Tree child){
        Tree newChild;
        switch (direction) {
            case 1 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, direction, child);
                }
                if (current.getTopLeft() != null && !current.getTopLeft().getIsOccupied()) {
                    newChild = new Tree(current.getTopLeft());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getTopLeft().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getTopLeft(), parent, direction, newChild);
                } else if (current.getTopLeft() != null && current.getTopLeft().getTopLeft() != null
                        && !current.getTopLeft().getTopLeft().getIsOccupied()
                        && current.getTopLeft().getIsOccupied()
                        && current.getTopLeft().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    newChild = new Tree(current.getTopLeft().getTopLeft());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getTopLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getTopLeft().getTopLeft(), parent, direction, newChild);
                }
            }
            case 2 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, direction, child);
                }
                if (current.getTopRight() != null && !current.getTopRight().getIsOccupied()) {
                    newChild = new Tree(current.getTopRight());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getTopRight().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getTopRight(), parent, direction, newChild);
                } else if (current.getTopRight() != null && current.getTopRight().getTopRight() != null
                        && !current.getTopRight().getTopRight().getIsOccupied()
                        && current.getTopRight().getIsOccupied()
                        && current.getTopRight().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    newChild = new Tree(current.getTopRight().getTopRight());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getTopRight());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getTopRight().getTopRight(), parent, direction, newChild);
                }
            }
            case 3 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, direction, child);
                }
                if (current.getBottomRight() != null && !current.getBottomRight().getIsOccupied()) {
                    newChild = new Tree(current.getBottomRight());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getBottomRight().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getBottomRight(), parent, direction, newChild);
                } else if (current.getBottomRight() != null && current.getBottomRight().getBottomRight() != null
                        && !current.getBottomRight().getBottomRight().getIsOccupied()
                        && current.getBottomRight().getIsOccupied()
                        && current.getBottomRight().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    newChild = new Tree(current.getBottomRight().getBottomRight());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getBottomRight());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getBottomRight().getBottomRight(), parent, direction, newChild);
                }
            }
            case 4 -> {
                if (parent.isAfterStrike() && child != null) {
                    showPossibleDameMoves(current, direction, child);
                }
                if (current.getBottomLeft() != null && !current.getBottomLeft().getIsOccupied()) {
                    newChild = new Tree(current.getBottomLeft());
                    parent.addChild(newChild);
                    if (parent.isAfterStrike()){
                        current.getBottomLeft().setStriked(current.getStriked());
                    }
                    checkDameMove(current.getBottomLeft(), parent, direction, newChild);
                } else if (current.getBottomLeft() != null && current.getBottomLeft().getBottomLeft() != null
                        && !current.getBottomLeft().getBottomLeft().getIsOccupied()
                        && current.getBottomLeft().getIsOccupied()
                        && current.getBottomLeft().getPiece().getColor() != playerTurn) {
                    parent.reset();
                    newChild = new Tree(current.getBottomLeft().getBottomLeft());
                    parent.addChild(newChild);
                    newChild.getData().setStriked(current.getBottomLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(current.getBottomLeft().getBottomLeft(), parent, direction, newChild);
                }
            }
        }
    }
    private void showPossibleDameMoves(Field current, int direction, Tree parent){
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
                    newChild.getData().setStriked(current.getTopRight());
                    parent.setAfterStrike(true);
                    checkDameMove(tempField.getTopRight().getTopRight(), parent, 2, newChild);
                    break;
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
                    newChild.getData().setStriked(current.getBottomLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(tempField.getBottomLeft().getBottomLeft(), parent, 4, newChild);
                    break;
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
                    newChild.getData().setStriked(current.getTopLeft());
                    parent.setAfterStrike(true);
                    checkDameMove(tempField.getTopLeft().getTopLeft(), parent, 1, newChild);
                    break;
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
                    newChild.getData().setStriked(current.getBottomRight());
                    parent.setAfterStrike(true);
                    checkDameMove(tempField.getBottomRight().getBottomRight(), parent, 3, newChild);
                    break;
                } else break;
            }
        }
    }

    private void checkMove(Field start, Field oneAway, Field twoAway, boolean color) {
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


    private void canStrike(Field start, Tree parent) {
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

    private Tree strike(Field oneAway, Field twoAway, Tree parent) {
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
                    for (Field f : board.getFieldsWithWhite().values()){
                        f.getPossibleMoves().clear();
                    }
                } else {
                    board.getFieldsWithBlack().replace(start.getPiece().getId(), endMove);
                    for (Field f : board.getFieldsWithBlack().values()){
                        f.getPossibleMoves().clear();
                    }
                }
                start.setPiece(null);
            } else {
                System.out.println("Niepoprawny ruch, prosze wybrac inne pole");
                return false;
            }
        }
        playerTurn = !playerTurn;
        return true;
    }

    public List<Integer> getPossibleStartFields() {
        return possibleStartFields;
    }

    public void checkPossibleStartFields(Board board) {
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

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean getPlayerTurn() {
        return playerTurn;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

}
