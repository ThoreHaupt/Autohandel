package lib.Event;

import java.util.EventObject;

import Model.UserComponentes.User;

/**
 * Event gets fired, when a new User is loged in
 */
public class NewUserLoginEvent extends EventObject {
    User oldUser;
    boolean oldUserWasGuest;

    public NewUserLoginEvent(Object source) {
        super(source);
    }
}
