public class Field {

    int id;
    Field topLeft;
    Field topRight;
    Field bottomLeft;
    Field bottomRight;
    Piece piece;


    public Field(int id) {
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


    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
