package lib.Event;

import java.util.EventListener;

/**
 * Listens changes in the filter
 */
public interface FilterChangeListener extends EventListener {
    public void onFilterChange(FilterChangeEvent event);
}