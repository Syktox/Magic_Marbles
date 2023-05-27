package magicmarbles.graphicui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class graphicUI {
    private void Window() {
        JFrame frame = new JFrame("Magic Marbles");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Circle c = new Circle(50, 50);
        Circle c1 = new Circle(100, 100);
        Circle c2 = new Circle(150, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10));
        panel.add(c);
        panel.add(c1);
        panel.add(c2);
        panel.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        fileMenu.add(newGame);
        JMenuItem exitMenu = new JMenuItem("Exit");
        fileMenu.add(exitMenu);
        frame.setJMenuBar(menuBar);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Points: "));
        Container container = frame.getContentPane();
        container.add(bottomPanel, BorderLayout.SOUTH);

        DerButtonHandler handler = new DerButtonHandler();
        exitMenu.addActionListener(handler);
        newGame.addActionListener(handler);

        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        graphicUI graphicUI = new graphicUI();
        graphicUI.Window();
    }

}

