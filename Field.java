public class Field {

    int id;
    Boolean isOccupied = false;
    Field topLeft;
    Field topRight;
    Field bottomLeft;
    Field bottomRight;
    Field striked;
    Piece piece;


    public Field getStriked() {
        return striked;
    }

    public void setStriked(Field striked) {
        this.striked = striked;
    }

    public Field(int id, int i, int j, Object o) {
        this.id = id;
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
    public Piece getPiece() {
        return piece;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }
    public Boolean getIsOccupiedOccupied() {
        return isOccupied;
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

