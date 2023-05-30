package magicmarbles.graphicUI;

import magicmarbles.model.MMFieldState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")

// VIEW
public class MMPanel extends JPanel {
    private int diameter;
    private MMModel model;
    public MMPanel(int radius, MMModel model) {
        super();
        this.model = model;
        this.model.addListener(listener);
        diameter = radius * 2;
        setPlayground();
        this.addMouseListener(new MMController()); 
    }

    public void setPlayground() {
        setPreferredSize(new Dimension(diameter * model.getGame().getWidth(), diameter * model.getGame().getHeight()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < model.getGame().getHeight(); row++) {
        	for (int col = 0; col < model.getGame().getWidth(); col++) {
                Color c = Color.BLACK;
                g.setColor(c);
                g.fillRect(col * diameter, row * diameter, diameter, diameter);

                MMFieldState tmp = model.game.getFieldState(row, col);
                if (tmp == MMFieldState.BLUE) {
                    g.setColor(Color.BLUE);
                } else if (tmp == MMFieldState.GREEN) {
                    g.setColor(Color.GREEN);
                } else if (tmp == MMFieldState.RED) {
                    g.setColor(Color.RED);
                }

                int[] pixelPos = PixelHelper.convertIndexToPixel(col, row);
                g.fillOval(pixelPos[0], pixelPos[1], diameter, diameter);
            }
        }
    }

    MMListener listener = new MMListener() {
        @Override
        public void gameChanged(MMEventObject evt) {
            repaint();
        }
    };

    class MMController extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int[] tmp = PixelHelper.convertPixelToIndex(e.getX(), e.getY());
            try {
                model.select(tmp[0], tmp[1]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
