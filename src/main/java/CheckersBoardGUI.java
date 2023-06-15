import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CheckersBoardGUI extends JFrame implements ActionListener {

    public CountDownLatch fieldChangedLatch = new CountDownLatch(1);
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 70;
    public Field lastClickedField;

    private JPanel boardPanel;
    private JButton[][] squares;

    public CheckersBoardGUI() {
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
    }

    private void initializeBoard() {
        Board board = new Board(BOARD_SIZE);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                squares[row][col].setBackground(getSquareColor(row, col));

                Field field = board.getFieldByIndex(row, col);
                squares[row][col].putClientProperty("field", field); // Associate Field object with the button

                squares[row][col].addActionListener(this); // Add ActionListener to each button
                boardPanel.add(squares[row][col]);
            }
        }

        updateBoardState(board);
    }
    private Color getSquareColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    private String getPieceSymbol(String symbol) {
        if (symbol.equals("b")) {
            return "blackpawn.png";
        } else if (symbol.equals("w")) {
            return "whitepawn.png";
        } else if (symbol.equals("B")) {
            return "blackpawnD.png";
        } else if (symbol.equals("W")) {
            return "whitepawnD.png";
        }
        return null;
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
                        square.setIcon(new ImageIcon(new ImageIcon(getPieceSymbol(symbol)).getImage().getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedSquare = (JButton) e.getSource();
        Field field = (Field) clickedSquare.getClientProperty("field");
        this.lastClickedField = field;
        fieldChangedLatch.countDown();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CheckersBoardGUI();
            }
        });
    }
}
