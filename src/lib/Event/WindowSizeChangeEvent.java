package lib.Event;

import java.util.EventObject;

/**
 * Event gets fired when the user changes the size of the Window
 */
public class WindowSizeChangeEvent extends EventObject {
    public WindowSizeChangeEvent(Object source) {
        super(source);
    }
}
