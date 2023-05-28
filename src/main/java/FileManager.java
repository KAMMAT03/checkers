import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private static final String GAME_STATE_FILE = "game_state.ser";
    private static final String OPTIONS_FILE = "options.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveGameState(Game game, String path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            String json = gson.toJson(game);
            fileWriter.write(json);
            System.out.println("Zapisano stan gry w pliku: " + path);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania stanu gry: " + e.getMessage());
        }
    }

    public Game loadGameState(String path) {
        try (FileReader fileReader = new FileReader(path)) {
            Game game = gson.fromJson(fileReader, Game.class);
            System.out.println("Wczytano stan gry z pliku: " + path);
            return game;
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas wczytywania stanu gry: " + e.getMessage());
            return null;
        }
    }

    public void saveOptions(Client client) {
        try (FileWriter fileWriter = new FileWriter(OPTIONS_FILE)) {
            String json = gson.toJson(client);
            fileWriter.write(json);
            System.out.println("Zapisano opcje interfejsu użytkownika w pliku: " + OPTIONS_FILE);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania opcji interfejsu użytkownika: " + e.getMessage());
        }
    }

    public void loadOptions(Client client) {
        try (FileReader fileReader = new FileReader(OPTIONS_FILE)) {
            Client options = gson.fromJson(fileReader, Client.class);
            client.setOptions(options);
            System.out.println("Wczytano opcje interfejsu użytkownika z pliku: " + OPTIONS_FILE);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas wczytywania opcji interfejsu użytkownika: " + e.getMessage());
        }
    }
}
