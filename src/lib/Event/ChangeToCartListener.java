package lib.Event;

import java.util.EventListener;

public interface ChangeToCartListener extends EventListener {
    public void onChangeToCart(ChangeToCartEvent event);
}