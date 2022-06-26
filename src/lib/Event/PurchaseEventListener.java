package lib.Event;

import java.util.EventListener;

/**
 * Listens to PurchaseEvent
 */
public interface PurchaseEventListener extends EventListener {
    public void onPurchaseEventCart(PurchaseEvent event);
}