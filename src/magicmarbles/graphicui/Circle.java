package magicmarbles.graphicui;

import javax.swing.*;
import java.awt.*;
import  java.awt.geom.*;

public class Circle extends JComponent {
    private int PosX;
    private int PosY;

    public Circle(int PosX, int PosY) {
        this.PosX = PosX;
        this.PosY = PosY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        g.fillOval(PosX, PosY, 50, 50);
    }

}