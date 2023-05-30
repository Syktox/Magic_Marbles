package magicmarbles.graphicUI;

import java.util.EventListener;

public interface MMListener extends EventListener {
    void gameChanged(MMEventObject evt);
}
