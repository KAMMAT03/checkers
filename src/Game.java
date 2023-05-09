public class Game {

    int n ; // wilekość planszy (n x n)
    Field[n*n/2] board  // tablica wszystkich pól
    List<Piece> white   // lista białych pionków
    List<Piece> black   // lista czarnych pionków

    boolean playerTurn  // określa czyja jest tura

    List<Field> showPossibleMoves(Field start) // Pokazuje wszystkie możliwe ruchy dla danego pionka. Przyjmuje referencje do pola dla którego ruchy sprawdzamy. Zwraca listę referencji do pól na które możemy się poruszyć.

    void move(Field start,Field end){  // Wykonuje ruch z pola start na pole end

    }

    void endTurn(){ // Zmienia kolor gracza. Sprawdzamy, czy gracz jeszcze ma pionki i czy są jakieś inne ruchy (możliwe zbicia). Jeżeli nie ten gracz przegrywa i należy zakończyć grę

    }

}
