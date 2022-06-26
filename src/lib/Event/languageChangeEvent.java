package lib.Event;

import java.util.EventObject;

/**
 * Event gets fired when there is a change to the language
 */
public class languageChangeEvent extends EventObject {
    public languageChangeEvent(Object source) {
        super(source);
    }
}
