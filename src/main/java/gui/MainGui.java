package gui;

import javax.swing.*;
import java.awt.*;

public class MainGui {

    private static final Color color1 = new Color(208, 87, 87);
    private static final Color color2 = new Color(240, 217, 181);
    private static final Color color3 = new Color(240, 217, 181);

    MainGui() {

    }

    public void startMenu() {
        JFrame frame = new JFrame("Checkers");
        JPanel panel = new JPanel();

        // Frame Configuration
        frame.setSize(300, 300);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,300);

        // Panel Configuration
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(4,1));

        // Label Configuration
        JLabel label = new JLabel("Checkerki");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial Unicode MS", Font.BOLD, 40));
        panel.add(label, BorderLayout.CENTER);

        // Buttons Configuration
        JButton button1 = new JButton("Start new game");
        button1.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton button2 = new JButton("Load game from");
        button2.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton button3 = new JButton("Exit");
        button3.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));

        button1.addActionListener(e -> {
            // New game
        });
        button2.addActionListener(e -> {
            // Load game from
        });
        button3.addActionListener(e -> {
            // Exit
        });

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);


        frame.setVisible(true);
    }
    public void test() {
        JFrame frame = new JFrame("Checkers");
        ImagePanel panel = new ImagePanel(new ImageIcon("goodframe.png").getImage().getScaledInstance(400, 500,Image.SCALE_DEFAULT));


        // Frame Configuration
        frame.setSize(500, 600);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,300);

        // Panel Configuration
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(4,1));

        // Label Configuration
        JLabel label = new JLabel("Checkerki");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial Unicode MS", Font.BOLD, 40));
        panel.add(label, BorderLayout.CENTER);

        // Buttons Configuration
        JButton button1 = new JButton("Start new game");
        button1.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        button1.setPreferredSize(new Dimension(50,50));
        JButton button2 = new JButton("Load game from");
        button2.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        JButton button3 = new JButton("Exit");
        button3.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));

        button1.addActionListener(e -> {
            // New game
        });
        button2.addActionListener(e -> {
            // Load game from
        });
        button3.addActionListener(e -> {
            // Exit
        });

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainGui mg = new MainGui();
        mg.startMenu();
//        mg.test();
    }
}