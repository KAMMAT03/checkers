import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JPanel {

    private static final int TILE_SIZE = 60;

    private static final Color LIGHT_COLOR = new Color(240, 217, 181);
    private static final Color DARK_COLOR = new Color(181, 136, 99);

    private int boardSize;
    private Game game;
    private boolean owner; // mayby good to know

    public BoardGUI(int boardSize,Game game,boolean owner) {
        this.boardSize = boardSize;
        this.game = game;
        this.owner = owner;
        setPreferredSize(new Dimension(boardSize * TILE_SIZE, boardSize * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                if ((row + col) % 2 == 0) {
                    g.setColor(LIGHT_COLOR);
                } else {
                    g.setColor(DARK_COLOR);
                }

                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}

