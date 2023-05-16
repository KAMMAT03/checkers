import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class FileManager {
    //powinno być private ale nie działa inaczej na ten moment
    public static final String GAME_STATE_FILE = "game_state.ser";
    private static final String OPTIONS_FILE = "options.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveGameState(Game game, String path) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(game);
            System.out.println("Zapisano stan gry w pliku: " + path);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania stanu gry: " + e.getMessage());
        }
    }

    public Game loadGameState(String path) {
        try (FileInputStream fileIn = new FileInputStream(path);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Game game = (Game) objectIn.readObject();
            System.out.println("Wczytano stan gry z pliku: " + path);
            return game;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Wystąpił błąd podczas wczytywania stanu gry: " + e.getMessage());
            return null;
        }
    }

    public void saveOptions(UserInterface userInterface) {
        try (FileWriter fileWriter = new FileWriter(OPTIONS_FILE)) {
            gson.toJson(userInterface, fileWriter);
            System.out.println("Zapisano opcje interfejsu użytkownika w pliku: " + OPTIONS_FILE);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania opcji interfejsu użytkownika: " + e.getMessage());
        }
    }

    public void loadOptions(UserInterface userInterface) {
        try (FileReader fileReader = new FileReader(OPTIONS_FILE)) {
            UserInterface options = gson.fromJson(fileReader, UserInterface.class);
            userInterface.setOptions(options);
            System.out.println("Wczytano opcje interfejsu użytkownika z pliku: " + OPTIONS_FILE);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas wczytywania opcji interfejsu użytkownika: " + e.getMessage());
        }
    }
}
