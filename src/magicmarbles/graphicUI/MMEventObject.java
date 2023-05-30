package magicmarbles.graphicUI;

import magicmarbles.model.MMGameImpl;

import java.util.EventObject;
public class MMEventObject extends EventObject {
    private final MMGameImpl game;
    public MMEventObject(Object source, MMGameImpl game) {
        super(source);
        this.game = game;
    }

    public MMGameImpl getGame() {
        return game;
    }
}
