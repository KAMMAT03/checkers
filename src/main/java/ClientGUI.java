import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientGUI extends JFrame {

    public static int boardSize;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    private JPanel mainPanel;

    public ClientGUI() throws IOException {
        //Logic part
        this.s = new Socket("localhost", 3333);
        this.din = new DataInputStream(s.getInputStream());
        this.dout = new DataOutputStream(s.getOutputStream());

        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
    }

    private void init(){
        //Graph part

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseBoardSize();
            }
        });

        mainPanel.add(newGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        mainPanel.add(loadGameButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        mainPanel.add(exitButton);

        add(mainPanel);
        setVisible(true);
    }
    private void chooseBoardSize() {
        JFrame dialogFrame = new JFrame("Choose Board Size");
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.setSize(300, 200);
        dialogFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Choose board size:");
        panel.add(label, BorderLayout.NORTH);

        JRadioButton smallButton = new JRadioButton("Small (8x8)");
        smallButton.addActionListener(e -> {
            //prob some func to mange game here
            boardSize =8;
            dialogFrame.dispose();
            Game game = new Game(boardSize);
            BoardGUI board = new BoardGUI(boardSize,game,true);

            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(board, BorderLayout.CENTER);

            JFrame mainFrame = new JFrame("Main Frame");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.getContentPane().add(contentPane);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });

        JRadioButton classicButton = new JRadioButton("Classic (10x10)");
        classicButton.addActionListener(e -> {
            //prob some func to mange game here
            boardSize=10;
            dialogFrame.dispose();
            Game game = new Game(boardSize);
            BoardGUI board = new BoardGUI(boardSize,game,true);
        });

        JRadioButton bigButton = new JRadioButton("Big (12x12)");
        bigButton.addActionListener(e -> {
            boardSize=12;
            dialogFrame.dispose();
            Game game = new Game(boardSize);
            BoardGUI board = new BoardGUI(boardSize,game,true);
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(smallButton);
        buttonGroup.add(classicButton);
        buttonGroup.add(bigButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(smallButton);
        radioPanel.add(classicButton);
        radioPanel.add(bigButton);

        panel.add(radioPanel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialogFrame.dispose());
        panel.add(cancelButton, BorderLayout.SOUTH);

        dialogFrame.add(panel);
        dialogFrame.setVisible(true);
    }

    private void loadGame() {
    }

    private void exitGame() {
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        ClientGUI client = new ClientGUI();
        client.init();
        System.out.println(boardSize);
    }
}