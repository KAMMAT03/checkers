public class Field {

    int i;
    int j;
    int id;
    boolean isOccupied = false;
    private Field topLeft;
    private Field topRight;
    private Field bottomLeft;
    private Field bottomRight;
    private Field striked;
    Piece piece;

    public Field(int i, int j, Object o) {
        this.i = i;
        this.j = j;
        this.id = i + 100*j;
    }

    public Field getStriked() {
        return striked;
    }

    public void setStriked(Field striked) {
        this.striked = striked;
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

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setOccupied(piece != null); // Ustawienie stanu pola na zajęte, jeśli pionek jest niepusty
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}