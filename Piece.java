public class Piece {
    boolean isDame; // true - jest damką, false - jest zwykłym pionkiem
    boolean color; // true - biały, false - czarny
    Field field; // referencja do pola na krórym stoi pionek


    public Piece(boolean color, Field field) {
        this.isDame = false;
        this.color = color;
        this.field = field;
    }

    public boolean getColor() {
        return color;
    }
    public boolean getIsDame() {
        return isDame;
    }
}
