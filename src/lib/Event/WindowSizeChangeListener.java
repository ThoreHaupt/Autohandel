package lib.Event;

import java.util.EventListener;

/**
 * Listens to WindowSizeChangeEvent
 */
public interface WindowSizeChangeListener extends EventListener {
    public void windowSizeChanged(WindowSizeChangeEvent event);
}
