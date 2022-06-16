package lib.Event;

import java.util.EventObject;

public class NewSearchEvent extends EventObject {
    public NewSearchEvent(Object source) {
        super(source);
    }
}
