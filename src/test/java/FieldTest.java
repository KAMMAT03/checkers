import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldTest {

    private Field field;

    @BeforeEach
    public void setUp() {
        field = new Field(0, 0, null);
    }

    @Test
    public void testAddPossibleMoves() {
        field.addPossibleMoves(1);
        field.addPossibleMoves(2);

        Assertions.assertEquals(2, field.getPossibleMoves().size());
        Assertions.assertTrue(field.getPossibleMoves().contains(1));
        Assertions.assertTrue(field.getPossibleMoves().contains(2));
    }

    @Test
    public void testSetPieceWithPiecePieceIsSet() {
        Piece piece = new Piece(true, 0, 0);
        field.setPiece(piece);

        Assertions.assertEquals(piece, field.getPiece());
        Assertions.assertTrue(field.getIsOccupied());
    }

    @Test
    public void testSetPieceWithNullPieceIsNull() {
        field.setPiece(null);

        Assertions.assertNull(field.getPiece());
        Assertions.assertFalse(field.getIsOccupied());
    }
}