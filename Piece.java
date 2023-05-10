public class Piece {
    boolean isDame; // true - jest damką, false - jest zwykłym pionkiem
    boolean color; // true - biały, false - czarny
    Field field; // referencja do pola na krórym stoi pionek
    int i;
    int j;
    int id;

    /*public Piece(boolean isDame, boolean color, Field field, int i, int j) {
        this.isDame = isDame;
        this.color = color;
        this.field = field;
        this.i = i;
        this.j = j;

    }*/

    public Piece(boolean color, int i, int j) {
        this.color = color;
        this.i = i;
        this.j = j;
    }

    public int getId() {
        return id;
    }

    public boolean getColor() {
        return color;
    }

    public boolean getIsDame() {
        return isDame;
    }

    public String getSymbol(){
        if (isDame){
            if (color){
                return "W";
            }
            else{
                return "B";
            }
        }
        else{
            if (color){
                return "w";
            }
            else{
                return "b";
            }
        }
    }
}
