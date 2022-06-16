package Model;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import Model.UserComponentes.User;
import lib.technicalComponents.Product;

public class Model {
    Car[] currentOptions;
    ArrayList<Product> allAvaliableObjects;
    // immer da
    User guest = new User();

    // usually guest, but you can log in
    User currentUser = guest;

    protected EventListenerList UserLoginChangeListenerList = new EventListenerList();

    public User getLoggedUser() {
        return currentUser;
    }

    public void registerUserLoginAttempt() {

    }

    public Car[] getCurrentOptions(Filter filter) {
        clalculteCurrentDisplayList(filter);
        return currentOptions;
    }

    private void clalculteCurrentDisplayList(Filter filter) {
    }

    public boolean isCurrentUserGuest() {
        return (currentUser == guest);
    }

}
