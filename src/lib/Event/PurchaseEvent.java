package lib.Event;

import java.util.EventObject;

/**
 * Event gets fired when there is a purchase
 */
public class PurchaseEvent extends EventObject {
    public PurchaseEvent(Object source) {
        super(source);
    }
}
