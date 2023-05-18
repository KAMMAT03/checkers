import java.io.Serializable;

public class Piece implements Serializable {
    private boolean isDame = false; // true - jest damką, false - jest zwykłym pionkiem
    private boolean color; // true - biały, false - czarny
    private Field field; // referencja do pola na krórym stoi pionek
    private int i;
    private int j;
    private int id;


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

    public void setDame(boolean dame) {
        isDame = dame;
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
