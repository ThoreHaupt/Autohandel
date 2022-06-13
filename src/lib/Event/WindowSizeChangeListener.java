package lib.Event;

import java.util.EventListener;

public interface WindowSizeChangeListener extends EventListener {
    public void windowSizeChanged(WindowSizeChangeEvent event);
}
