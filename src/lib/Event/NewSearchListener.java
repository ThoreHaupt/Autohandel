package lib.Event;

import java.util.EventListener;

/**
 * Listens to new Search queries
 */
public interface NewSearchListener extends EventListener {
    public void onNewSearch(ChangeToCartEvent event);
}