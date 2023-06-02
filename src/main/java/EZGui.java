import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EZGui extends JFrame {
    public static int boardSize;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private int choice;
    public EZGui() throws IOException {
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
    public int init(){
        //Graph part
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
            }
        });

        mainPanel.add(newGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
            }
        });
        mainPanel.add(loadGameButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice =3;
            }
        });
        mainPanel.add(exitButton);

        add(mainPanel);
        setVisible(true);
        return choice;
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

        newGameButton = new JButton("Small 8X8");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
            }
        });

        panel.add(newGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
            }
        });
        panel.add(loadGameButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice =3;
            }
        });
        panel.add(exitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialogFrame.dispose());
        panel.add(cancelButton, BorderLayout.SOUTH);

        dialogFrame.add(panel);
        dialogFrame.setVisible(true);
    }
}
