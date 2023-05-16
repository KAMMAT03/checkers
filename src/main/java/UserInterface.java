import java.io.Serializable;

public class UserInterface implements Serializable {
    private int boardSize;
    private String playerName;
    private boolean soundEnabled;
    private int gameDifficulty;

    public UserInterface(int boardSize, String playerName, boolean soundEnabled, int gameDifficulty) {
        this.boardSize = boardSize;
        this.playerName = playerName;
        this.soundEnabled = soundEnabled;
        this.gameDifficulty = gameDifficulty;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(int gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public void setOptions(UserInterface newOptions) {
        this.boardSize = newOptions.getBoardSize();
        this.playerName = newOptions.getPlayerName();
        this.gameDifficulty = newOptions.getGameDifficulty();
    }
}