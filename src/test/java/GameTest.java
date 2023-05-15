import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        String input = "B"; // Wybór planszy klasycznej (10x10)
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game = new Game();
    }

    @Test
    public void testSetN() {
        int expected = 10;
        int actual = game.getN();
        assertEquals(expected, actual);
    }

    @Test
    public void testIsPlayerTurn() {
        boolean expected = true;
        boolean actual = game.isPlayerTurn();
        assertEquals(expected, actual);
    }
}
