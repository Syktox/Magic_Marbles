package magicmarbles.graphicUI;

import magicmarbles.model.MMException;
import magicmarbles.model.MMFieldState;
import magicmarbles.model.MMGameImpl;

import java.util.ArrayList;
import java.util.List;

public class MMModel {

    List<MMListener> listeners = new ArrayList<MMListener>();
    MMGameImpl game;

    MMModel(int height, int width) {
        game = new MMGameImpl(width, height);
    }

    public void fireChangeEvent() {
        MMEventObject evt = new MMEventObject(this, game);
        for (MMListener listener : listeners) {
            listener.gameChanged(evt);
        }
    }

    public void addListener(MMListener listener) {
        listeners.add(listener);
    }

    public void select(int row, int col) throws MMException {
        game.select(col, row);
        fireChangeEvent();
    }

    public MMGameImpl getGame() {
        return game;
    }

    public MMFieldState getFieldState(int row, int col) {
        return game.getFieldState(row, col);
    }

    public void reset(int width, int height)
    {
        this.game = new MMGameImpl(width,height);
        game.setPoints(0);
    }

}
