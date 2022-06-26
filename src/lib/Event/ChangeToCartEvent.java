package lib.Event;

import java.util.EventObject;

/**
 * Event gets fired when there is a change to the cart
 */
public class ChangeToCartEvent extends EventObject {
    public ChangeToCartEvent(Object source) {
        super(source);
    }
}
