package lib.Event;

import java.util.EventObject;

import Model.UserComponentes.User;

/**
 * Event gets fired, when a new User is loged in
 */
public class NewUserLoginEvent extends EventObject {
    private User newUser;
    private boolean oldUserWasGuest;

    public NewUserLoginEvent(Object source, User newUser, boolean oldUserWasGuest) {
        super(source);
        this.newUser = newUser;
        this.oldUserWasGuest = oldUserWasGuest;
    }

    /**
     * @return the newUser
     */
    public User getNewUser() {
        return newUser;
    }

    /**
     * @return the oldUserWasGuest
     */
    public boolean isOldUserWasGuest() {
        return oldUserWasGuest;
    }

}
