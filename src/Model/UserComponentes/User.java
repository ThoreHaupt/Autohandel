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

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = " f_name";
    public static final String LAST_NAME = " l_name";
    public static final String BIRTHDAY = "birthday";
    public static final String PASSWORD1 = "password1";
    public static final String PASSWORD2 = "password2";
    public static final String PREFERED_LANGUAGE = "preferredLanguage";

    public static final String[] INPUTTAGS = new String[] { USERNAME, EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, PASSWORD1,
            PASSWORD2, PREFERED_LANGUAGE };

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
