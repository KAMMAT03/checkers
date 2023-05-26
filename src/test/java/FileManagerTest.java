import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class FileManagerTest {
    private static final String TEST_GAME_STATE_FILE = "test_game_state.ser";
    private static final String TEST_OPTIONS_FILE = "test_options.json";

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

    @Test //test nie przechodzi???
    public void loadGameStateTest() {
        // Tworzenie przykładowej gry
        Game originalGame = new Game(8);

        // Zapisywanie stanu gry
        fileManager.saveGameState(originalGame, TEST_GAME_STATE_FILE);

        // Wczytywanie stanu gry
        Game loadedGame = fileManager.loadGameState(TEST_GAME_STATE_FILE);

        // Sprawdzanie czy wczytana gra jest taka sama jak oryginalna gra
        Assertions.assertEquals(originalGame, loadedGame);

        // Usuwanie pliku
        File file = new File(TEST_GAME_STATE_FILE);
        file.delete();
    }   

}
