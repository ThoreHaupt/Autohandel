package Model.UserComponentes;

import java.io.Serializable;
import java.util.ArrayList;

import LocalizationLogic.Language;

public class User implements Serializable {
    private String email = "";
    private String username = "GUEST";
    private String password = "";
    private int id = 0;
    private ArrayList<Order> history;
    private String first_name = "";
    private String last_name = "";
    private Filter filter = new Filter();

    private Language preferredLanguage;
    private boolean preferrsDarkTheme;

    private boolean isGuest = false;

    private Cart cart = new Cart();

    public User() {

    }

    public Filter getFilter() {
        return filter;
    }

    public String getUserFirstName() {
        return first_name;
    }

    public String getUserLastName() {
        return last_name;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
