import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
public class FileManager {
    private final ObjectMapper objectMapper;

    public FileManager() {
        objectMapper = new ObjectMapper();
    }
    void saveGameState(Game, path){
        // Funkcja która zapisze w pliku zserializowaną instancję gry do wskazanego pliku.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Game loadGameState(path ){
        // Funkcja która zwróci instancję gry na podstawie zserializowanego stanu we wskazanym pliku.
        Game game = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            game = (Game) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return game;
    }

    void saveOptions(UserInterface userInterface, String path ){
        // Funkcja która pobierze dane ze wskazanej klasy i zapisze je w Pliku JSON.
        try {
            objectMapper.writeValue(new File(path), userInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void loadOptions( UserInterface ){
        // Funkcja która ustawi zmienne we wskazanej klasie na podstawie pliku JSON
        UserInterface loadedUserInterface = null;
        try {
            loadedUserInterface = objectMapper.readValue(new File(path), UserInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Aktualizacja opcji interfejsu użytkownika
        userInterface.setOptions(loadedUserInterface.getOptions());
        }
}
