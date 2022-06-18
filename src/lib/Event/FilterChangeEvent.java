package lib.Event;

import java.util.EventObject;

/**
 * When the information in the filter changes this is called.
 */
public class FilterChangeEvent extends EventObject {

    public FilterChangeEvent(Object source) {
        super(source);
    }
}
