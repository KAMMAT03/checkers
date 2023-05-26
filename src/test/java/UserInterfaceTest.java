    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    import java.io.ByteArrayInputStream;
    import java.io.ByteArrayOutputStream;
    import java.io.InputStream;
    import java.io.PrintStream;


    public class UserInterfaceTest {
        private UserInterface userInterface;
        private ByteArrayOutputStream output;

        @BeforeEach
        public void setup() {
            userInterface = new UserInterface();
            output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
        }

        @Test
        public void printMenuTest() {
            String expectedOutput = "Welcome to Checkers!\n" +
                    "1. New game\n" +
                    "2. Load game\n" +
                    "3. Exit\n\n";

            userInterface.printMenu();
            Assertions.assertEquals(expectedOutput, output.toString());
        }

        @Test
        public void chooseBoardSizeTest() {
            String input = "2\n";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            userInterface.chooseBoardSize();

            Assertions.assertEquals(10, UserInterface.getBoardSize());
        }

        @Test
        public void newGameTest() {
            String input = "2\nA 2\nX\n";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            userInterface.newGame();

            // Sprawdzanie wyników gry
            String expectedOutput = "Ruch czarnych\n" +
                    "Podaj litere kolumny, a potem numer rzedu wybranego pionka: \n" +
                    "Podaj litere kolumny, a potem numer rzedu planowanych ruchow, a jesli koniec to x: \n" +
                    "Wygraly czarne\n";
            Assertions.assertEquals(expectedOutput, output.toString());
        }
    }
