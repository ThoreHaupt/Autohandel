package Model.UserComponentes;

import java.io.Serializable;
import java.util.ArrayList;

import LocalizationLogic.Language;
import lib.technicalComponents.StringTools;

public class User implements Serializable {
    private String email;
    private String username;
    private String password;
    private int id;
    private ArrayList<Order> history;
    private String first_name;
    private String last_name;
    private Filter filter;

    private Language preferredLanguage;
    private boolean preferrsDarkTheme;

    private boolean isGuest = false;

    private Cart cart = new Cart();

    private String userDataPath;
    private UserAuthKey key;

    public User() {
        isGuest = true;
        preferredLanguage = Language.ENGLISH;
        email = "";
        username = "GUEST";
        password = "";
        id = 0;
        history = null;
        first_name = "";
        last_name = "";
        filter = new Filter();
    }

    public User(String username, String password) {
        userDataPath = StringTools.getRandomString(50);
        key = new UserAuthKey(password, userDataPath);
        key.safe(username);

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
