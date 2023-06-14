import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MainGui extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel mainMenuPanel;
    private JPanel difficultyMenuPanel;
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 70;
    public Field lastClickedField;
    private JButton[][] squares;
    private JPanel boardPanel;
    public CountDownLatch fieldChangedLatch = new CountDownLatch(1);

    MainGui() {
        // Create the board panel
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
    }

    public void startMenu() {
        frame = new JFrame("Checkers");
        mainMenuPanel = new JPanel();

//         Opcja do rozwazenia
//        mainMenuPanel = new ImagePanel(new ImageIcon("goodframe.png").getImage().getScaledInstance(400, 500,Image.SCALE_DEFAULT));

        // Frame Configuration
        frame.setSize(500, 500);
        frame.add(mainMenuPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 300);

        // Panel Configuration
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainMenuPanel.setLayout(new GridLayout(4, 1));

        // Label Configuration
        JLabel label = new JLabel("Checkerki");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial Unicode MS", Font.BOLD, 40));
        mainMenuPanel.add(label, BorderLayout.CENTER);

        // Buttons Configuration
        JButton button1 = new JButton("Start new game");
        button1.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton button2 = new JButton("Load game from");
        button2.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton button3 = new JButton("Exit");
        button3.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));

        mainMenuPanel.add(button1);
        mainMenuPanel.add(button2);
        mainMenuPanel.add(button3);

        button1.addActionListener(e -> {
            // New game
            showDifficultySubMenu();
        });
        button2.addActionListener(e -> {
            // Load game from
            // loadGameFromFile();
        });
        button3.addActionListener(e -> {
            // Exit
            System.exit(0);
        });

        frame.setVisible(true);
    }

    private void showDifficultySubMenu() {
        // Tworzenie panelu dla submenu
        difficultyMenuPanel = new JPanel();
        difficultyMenuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        difficultyMenuPanel.setLayout(new GridLayout(5, 1));



        // Label Configuration
        JLabel label = new JLabel("Choose board size");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial Unicode MS", Font.BOLD, 40));
        difficultyMenuPanel.add(label, BorderLayout.CENTER);

        // Buttons Configuration
        JButton buttonSmall = new JButton("Small (8x8)");
        buttonSmall.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton buttonClassic = new JButton("Classic (10x10)");
        buttonClassic.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton buttonBig = new JButton("Big (12x12)");
        buttonBig.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton buttonBack = new JButton("Back");
        buttonBack.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));

        buttonSmall.addActionListener(e -> {
            // Set board size to small
            lastClickedField = new Field(-1, -1, new Object());
            setTitle("Checkers");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);
            setLayout(new BorderLayout());

            boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
            squares = new JButton[BOARD_SIZE][BOARD_SIZE];

            initializeBoard();

            add(boardPanel, BorderLayout.CENTER);
            setVisible(true);
        });

        buttonClassic.addActionListener(e -> {
            // Set board size to classic (10x10)
            // Do something
        });

        buttonBig.addActionListener(e -> {
            // Set board size to big (12x12)
            // Do something
        });

        buttonBack.addActionListener(e -> {
            // Returns to main menu
            frame.remove(difficultyMenuPanel); // Delete submenu panel from frame
            frame.add(mainMenuPanel); // Add main menu panel to frame
            frame.revalidate();
            frame.repaint();
        });

        difficultyMenuPanel.add(buttonSmall);
        difficultyMenuPanel.add(buttonClassic);
        difficultyMenuPanel.add(buttonBig);
        difficultyMenuPanel.add(buttonBack);

        frame.remove(mainMenuPanel); // Delete main menu panel from frame
        frame.add(difficultyMenuPanel); // Add submenu panel to frame
        frame.revalidate();
        frame.repaint();
    }

    private void updateBoardState(Board board) {
        Map<Integer, Field> fieldsWithWhite = board.getFieldsWithWhite();
        Map<Integer, Field> fieldsWithBlack = board.getFieldsWithBlack();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Field field = board.getFieldByIndex(row, col);
                JButton square = squares[row][col];

                if ((row + col) % 2 == 0) {
                    if (field.getPiece() == null) {
                        square.setText(""); // Clear any previous text
                        square.setBackground(Color.WHITE);
                    } else {
                        String symbol = field.getPiece().getSymbol();
                        square.setText(symbol);
                        square.setBackground(getPieceColor(symbol));
                    }

                    if (fieldsWithWhite.containsValue(field)) {
                        square.setForeground(Color.BLACK);
                    } else if (fieldsWithBlack.containsValue(field)) {
                        square.setForeground(Color.WHITE);
                    } else {
                        square.setForeground(Color.BLACK);
                    }
                } else {
                    square.setText(""); // Clear any previous text
                    square.setBackground(Color.BLACK);
                }
            }
        }
    }

    private Color getPieceColor(String symbol) {
        if (symbol.equals("B")) {
            return Color.BLACK;
        } else if (symbol.equals("W")) {
            return Color.WHITE;
        } else {
            return Color.GRAY;
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton clickedSquare = (JButton) e.getSource();
        Field field = (Field) clickedSquare.getClientProperty("field");
        this.lastClickedField = field;
        fieldChangedLatch.countDown();
    }

    private Color getSquareColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    private void initializeBoard() {
        if (squares == null) {
            squares = new JButton[BOARD_SIZE][BOARD_SIZE]; // Initialize the squares array
        }

        Board board = new Board(BOARD_SIZE);

        boardPanel.removeAll(); // Remove existing components from the boardPanel

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (squares[row][col] == null) {
                    squares[row][col] = new JButton();
                }

                squares[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                squares[row][col].setBackground(getSquareColor(row, col));

                Field field = board.getFieldByIndex(row, col);
                squares[row][col].putClientProperty("field", field); // Associate Field object with the button

                squares[row][col].addActionListener(this); // Add ActionListener to each button
                boardPanel.add(squares[row][col]);
            }
        }

        updateBoardState(board);

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGui gui = new MainGui();
                gui.startMenu();
            }
        });
    }

}

