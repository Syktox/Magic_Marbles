package magicmarbles.graphicui;

import javax.swing.*;

public class StartGameFrame {
    void Window() {
        JFrame frame = new JFrame("Input");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(250, 150);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));


        JLabel hight = new JLabel("Enter the hight: ");
        JLabel width = new JLabel("Enter the width: ");


        frame.add(hight);
        frame.add(width);

        frame.setVisible(true);
    }
}
