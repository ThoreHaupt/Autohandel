package lib.Event;

import java.util.EventListener;

/**
 * Listens to ChangeToCartEvent
 */
public interface ChangeToCartListener extends EventListener {
    public void onChangeToCart(ChangeToCartEvent event);
}