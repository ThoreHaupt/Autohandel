package lib.Event;

import java.util.EventListener;

public interface SideHideExtentionStateChangeListener extends EventListener {
    public void extentionStateChanged(SideHideExtentionStateChangeEvent event);
}