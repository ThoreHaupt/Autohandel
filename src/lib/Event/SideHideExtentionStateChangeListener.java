package lib.Event;

import java.util.EventListener;

/**
 * Listens to SideHideExtentionStateChangeEvent
 */
public interface SideHideExtentionStateChangeListener extends EventListener {
    public void extentionStateChanged(SideHideExtentionStateChangeEvent event);
}