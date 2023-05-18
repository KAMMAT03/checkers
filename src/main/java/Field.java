import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Field implements Serializable {

    int rowIndex;
    int colIndex;
    int id;
    private boolean isOccupied = false;
    private Field topLeft;
    private Field topRight;
    private Field bottomLeft;
    private Field bottomRight;
    private Field striked;
    private Tree root;
    private boolean visited = false;
    private boolean whiteDame = false;
    private boolean blackDame = false;

    private List<Integer> possibleMoves;
    Piece piece;

    public Field(int rowIndex, int colIndex, Object o) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.id = rowIndex + 100* colIndex;
        possibleMoves = new ArrayList<>();
        root = new Tree(this);
    }

    public Field getStriked() {
        return striked;
    }

    public void setStriked(Field striked) {
        this.striked = striked;
    }

    public List<Integer> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Integer> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public void addPossibleMoves(int move) {
        this.possibleMoves.add(move);
    }

    public Tree getRoot() {
        return root;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Field getBottomLeft() {
        return bottomLeft;
    }

    public Field getBottomRight() {
        return bottomRight;
    }

    public Field getTopLeft() {
        return topLeft;
    }

    public Field getTopRight() {
        return topRight;
    }

    public void setBottomLeft(Field bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public void setBottomRight(Field bottomRight) {
        this.bottomRight = bottomRight;
    }

    public void setTopLeft(Field topLeft) {
        this.topLeft = topLeft;
    }

    public void setTopRight(Field topRight) {
        this.topRight = topRight;
    }

    public int getId() {
        return id;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean isWhiteDame() {
        return whiteDame;
    }

    public void setWhiteDame(boolean whiteDame) {
        this.whiteDame = whiteDame;
    }

    public boolean isBlackDame() {
        return blackDame;
    }

    public void setBlackDame(boolean blackDame) {
        this.blackDame = blackDame;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null){
            if (piece.getColor() && whiteDame || !piece.getColor() && blackDame) {
                piece.setDame(true);
            }
        }
        setOccupied(piece != null); // Ustawienie stanu pola na zajęte, jeśli pionek jest niepusty
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}