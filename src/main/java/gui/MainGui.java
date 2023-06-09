package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui {

    private JFrame frame;
    private JPanel mainMenuPanel;
    private JPanel difficultyMenuPanel;

    MainGui() {

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
            // Do something
        });

        buttonClassic.addActionListener(e -> {
            // Set board size to classic
            // Do something
        });

        buttonBig.addActionListener(e -> {
            // Set board size to big
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

    public static void main(String[] args) {
        MainGui mg = new MainGui();
        mg.startMenu();
//        mg.test();
    }
}
