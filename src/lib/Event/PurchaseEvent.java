package lib.Event;

import java.util.EventObject;

public class PurchaseEvent extends EventObject {
    public PurchaseEvent(Object source) {
        super(source);
    }
}
