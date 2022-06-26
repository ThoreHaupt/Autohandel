package lib.Event;

import java.util.EventListener;

/**
 * Listens to NewUserLoginEvent
 */
public interface NewUserLoginListener extends EventListener {
    public void onUserLoginEvent(NewUserLoginEvent event);
}