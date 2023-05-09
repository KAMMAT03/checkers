public class Piece {
    boolean isDame; // true - jest damką, false - jest zwykłym pionkiem
    boolean color; // true - biały, false - czarny
    Field field; // referencja do pola na krórym stoi pionek
    int id;

    public Piece(boolean color, Field field, int id) {
        this.isDame = false;
        this.color = color;
        this.field = field;
        this.id = id;
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
