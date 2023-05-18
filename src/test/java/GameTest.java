import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game(8); // Przykładowa wielkość planszy
    }

    @Test
    public void testInitialGameSetup() {
        Assertions.assertTrue(game.getPlayerTurn()); // sprawdzenie, czy tura należy do białych graczy
        Assertions.assertFalse(game.getGameOver()); // sprawdzenie, czy gra nie jest zakończona
        Assertions.assertFalse(game.getWinner()); // sprawdzenie, czy nie ma jeszcze zwycięzcy
        Assertions.assertEquals(8, game.getN()); // sprawdzenie, czy wielkość planszy jest ustawiona na 8
    }

    @Test
    public void testMove() {
        // Przygotowanie planszy i pionków dla testu
        Board board = game.getBoard();
        Field startField = board.getFieldByIndex(2, 0); // wprowadzenie odpowiednich indeksów pola startowego
        Field endField = board.getFieldByIndex(3, 1); // wprowadzenie odpowiednich indeksów pola docelowego
        List<Integer> wantedMoves = List.of(103); // Przykładowe żądane ruchy, które powinny być dostępne dla danego pionka


        boolean moveResult = game.move(startField, endField, wantedMoves, board);

        Assertions.assertFalse(moveResult); //sprawdzenie czy to błeędny ruch
    }


}