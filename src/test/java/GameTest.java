import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game(8); // Przykładowa wielkość planszy - można dostosować do potrzeb
    }

    @Test
    public void testInitialGameSetup() {
        Assertions.assertTrue(game.getPlayerTurn()); // Sprawdź, czy tura należy do białych graczy
        Assertions.assertFalse(game.getGameOver()); // Sprawdź, czy gra nie jest zakończona
        Assertions.assertFalse(game.getWinner()); // Sprawdź, czy nie ma jeszcze zwycięzcy
        Assertions.assertEquals(8, game.getN()); // Sprawdź, czy wielkość planszy jest ustawiona na 8
        // Dodaj inne asercje w zależności od oczekiwanego stanu gry na początku
    }

    @Test
    public void testMove() {
        // Przygotowanie planszy i pionków dla testu
        Board board = game.getBoard();
        Field startField = board.getField(2, 0); // Wprowadź odpowiednie indeksy pola startowego
        Field endField = board.getField(3, 1); // Wprowadź odpowiednie indeksy pola docelowego
        List<Integer> wantedMoves = List.of(103); // Przykładowe żądane ruchy, które powinny być dostępne dla danego pionka


        boolean moveResult = game.move(startField, endField, wantedMoves, board);

        Assertions.assertFalse(moveResult); //sprawdzenie czy to błeędny ruch
    }


}