import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class FileManagerTest {
    private static final String TEST_GAME_STATE_FILE = "test_game_state.ser";

    private FileManager fileManager;

    @BeforeEach
    public void setup() {
        fileManager = new FileManager();
    }

    @Test
    public void saveGameStateTest() {
        // Tworzenie przykładowej gry
        Game game = new Game(8);

        // Zapisywanie stanu gry
        fileManager.saveGameState(game, TEST_GAME_STATE_FILE);

        // Sprawdzanie czy plik został utworzony
        File file = new File(TEST_GAME_STATE_FILE);
        Assertions.assertTrue(file.exists());

        // Usuwanie pliku
        file.delete();
    }



}
