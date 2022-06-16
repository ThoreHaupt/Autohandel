package lib.Event;

import java.util.EventListener;

public interface NewUserLoginListener extends EventListener {
    public void onUserLoginEvent(NewUserLoginEvent event);
}