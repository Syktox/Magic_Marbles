package magicmarbles.graphicUI;

import magicmarbles.model.MMState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MMApp {
    private static final int TERMINATE = 1;
	private final int TOKEN_RADIUS = 20;
    private int width;
    private int height;
    private JFrame frame;
    private MMPanel paintPanel;
    private JPanel pointsPanel;
    private JLabel points;
    private MMModel model;

    public static void main(String[] args) {
        int[] tmp = getWidthAndHeight();
        if (tmp != null) {
            new MMApp(tmp[0], tmp[1]);
        }
    }

    public MMApp(int width, int height) {
        this.width = width;
        this.height = height;
        PixelHelper.setRADIUS(TOKEN_RADIUS);

        model = new MMModel(width, height);
        model.addListener(listener);

        this.frame = new JFrame("Magic Marbles");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(createMenu());

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout(1, 1));
        contentPane.setBackground(Color.BLACK);

        this.paintPanel = createPaintPanel();
        contentPane.add(this.paintPanel, BorderLayout.CENTER);
        this.pointsPanel = createPointsPanel();
        contentPane.add(this.pointsPanel, BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(150, 200));

        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createPointsPanel() {
        JPanel sp = new JPanel();
        this.points = new JLabel("Points: ");
        sp.setLayout(new FlowLayout());
        sp.add(points);
        return sp;
    }

    private MMPanel createPaintPanel() {
        MMPanel pp = new MMPanel(TOKEN_RADIUS, model);
        pp.setPreferredSize(new Dimension(height * (2 * TOKEN_RADIUS), width * (2 * TOKEN_RADIUS)));
        pp.setBackground(Color.WHITE);
        return pp;
    }

    public void reset() {
        int[] tmp = getWidthAndHeight();
        if (tmp == null) {
            System.exit(0);
        }
        this.width = tmp[0];
        this.height = tmp[1];

        model.reset(width, height);
        frame.remove(paintPanel);
        paintPanel = createPaintPanel();
        paintPanel.setPlayground();
        frame.add(paintPanel);
        frame.setVisible(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        paintPanel.repaint();
        frame.repaint();
        frame.setVisible(true);
        model.fireChangeEvent();
    }

    public void reset(int width, int height) {
        model.reset(width, height);
        frame.remove(paintPanel);
        paintPanel = createPaintPanel();
        paintPanel.setPlayground();
        frame.add(paintPanel);
        frame.setVisible(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        paintPanel.repaint();
        frame.repaint();
        frame.setVisible(true);
        model.fireChangeEvent();
    }

    public void updatePoints(int p) {
        points.setText("Points: " + p);
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem refresh = new JMenuItem("Refresh");
        JMenu credits = new JMenu("Credits");
        JMenuItem creditsItem = new JMenuItem("Made By Markus Kammerstetter");
        refresh.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset(width, height);
            }
        });
        JMenuItem newItem = new JMenuItem("New", 'N');
        newItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit", 'X');
        exitItem.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
        });
        menu.add(newItem);
        menu.add(exitItem);
        credits.add(creditsItem);
        menuBar.add(menu);
        menuBar.add(refresh);
        menuBar.add(credits);
        return menuBar;
    }

    private static int[] getWidthAndHeight() {
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JPanel uiPanel = new JPanel();
        uiPanel.add(new JLabel("Height:"));
        uiPanel.add(widthField);
        uiPanel.add(new JLabel("Width:"));
        uiPanel.add(heightField);
        uiPanel.setPreferredSize(new Dimension(250, 30));

        boolean finished = false;
        int[] widthAndHeightArr = null;
        while (!finished) {
 			int result = JOptionPane.showOptionDialog(null, uiPanel, 
					"Please enter width and height (min. 3)", 
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					new String[] {"New game", "Terminate"}, 
					"New game");

            if (result == TERMINATE) {
                JOptionPane.showMessageDialog(null, "Game has been terminated!");
                return null;
            }
            int width = -1;
            int height = -1;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input, please retry!", "Invalid format", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (width < 3 || height < 3) {
                JOptionPane.showMessageDialog(null, "Minimum is 3, please retry!", "Input out of range", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            finished = true;
            widthAndHeightArr = new int[2];
            widthAndHeightArr[0] = width;
            widthAndHeightArr[1] = height;
        }
        return widthAndHeightArr;
    }

    private void gameEnd(int points) {
        JOptionPane.showMessageDialog(frame, "Game Over - You achieved " + points + " Points!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        reset();
    }

    private final MMListener listener = evt -> {
        updatePoints(evt.getGame().getGamePoints());
        if (evt.getGame().getGameState() == MMState.END) {
            frame.dispose();
            gameEnd(evt.getGame().getGamePoints());
        }
    };

}
