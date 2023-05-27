package magicmarbles.graphicui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DerButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Exit"))
            System.exit(0);
        else if (event.getActionCommand().equals("New Game")) {
            System.out.println("New Game");
            StartGameFrame startGameFrame = new StartGameFrame();
            startGameFrame.Window();
        }
        else if (event.getActionCommand().equals("c"))
            System.out.println("c");

    }
}
