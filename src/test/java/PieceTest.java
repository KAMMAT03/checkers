import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceTest {

    private Piece piece;

    @BeforeEach
    public void setUp() {
        piece = new Piece(true, 0, 0);
    }

    @Test
    public void testGetIdReturnsCorrectId() {
        int expectedId = 0;
        int id = piece.getId();

        Assertions.assertEquals(expectedId, id);
    }

    @Test
    public void testGetColor_ReturnsCorrectColor() {
        boolean color = piece.getColor();

        Assertions.assertTrue(color);
    }

    @Test
    public void testGetSymbolIsNotDameReturnsLowercaseSymbol() {
        boolean expectedSymbol = false;
        boolean symbol = piece.getIsDame();

        Assertions.assertEquals(expectedSymbol, symbol);
    }

    @Test
    public void testGetSymbolIsDameReturnsUppercaseSymbol() {
        piece.setDame(true);
        String expectedSymbol = "W";
        String symbol = piece.getSymbol();

        Assertions.assertEquals(expectedSymbol, symbol);
    }

}