package lib.Event;

import java.util.EventListener;

public interface PurchaseEventListener extends EventListener {
    public void onPurchaseEventCart(PurchaseEvent event);
}