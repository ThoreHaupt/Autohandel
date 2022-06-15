package lib.Event;

import java.util.EventObject;

public class ChangeToCartEvent extends EventObject {
    public ChangeToCartEvent(Object source) {
        super(source);
    }
}
