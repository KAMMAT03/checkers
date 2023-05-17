import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(8);
    }

    @Test
    public void testGetFieldByIndex() {
        Field field = board.getFieldByIndex(0, 0);

        Assertions.assertNotNull(field);
        Assertions.assertEquals(0, field.getRowIndex());
        Assertions.assertEquals(0, field.getColIndex());
    }

    @Test
    public void testGetWhite() {
       board.getWhite();
        Assertions.assertEquals(12, board.getWhite().size());
    }
}