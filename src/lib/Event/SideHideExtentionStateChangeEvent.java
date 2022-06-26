package lib.Event;

import java.util.EventObject;

/**
 * Event gets fired when the side Hide Menu changes its size
 */
public class SideHideExtentionStateChangeEvent extends EventObject {
    public SideHideExtentionStateChangeEvent(Object source) {
        super(source);
    }
}
