import java.io.Serializable;

public class Piece implements Serializable {
    boolean isDame = false; // true - jest damką, false - jest zwykłym pionkiem
    boolean color; // true - biały, false - czarny
    Field field; // referencja do pola na krórym stoi pionek
    int i;
    int j;
    int id;


    public Piece(boolean color, int i, int j) {
        this.color = color;
        this.i = i;
        this.j = j;
    }

    public int getId() {
        id = i + 100*j;
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
