package lib.Event;

import java.util.EventListener;

public interface NewUserLoginListener extends EventListener {
    public void onUserLoginListener(ChangeToCartEvent event);
}