package lib.Event;

import java.util.EventObject;

public class WindowSizeChangeEvent extends EventObject {
    public WindowSizeChangeEvent(Object source) {
        super(source);
    }
}
