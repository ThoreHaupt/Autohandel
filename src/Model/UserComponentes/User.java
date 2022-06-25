package Model.UserComponentes;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import LocalizationLogic.Language;
import Model.Model;
import lib.DataStructures.HashMapImplementation.KeyValuePair;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Other.StringTools;
import lib.fileHandling.FileSaver;

public class User implements Serializable {

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "f_name";
    public static final String LAST_NAME = "l_name";
    public static final String BIRTHDAY = "birthday";
    public static final String PASSWORD1 = "password1";
    public static final String PASSWORD2 = "password2";
    public static final String PREFERED_LANGUAGE = "preferredLanguage";

    public static final String USERDATA_STORAGE_PATH = "Data/UserProfiles/UserProfileIformation/";

    public static final String[] INPUTTAGS = new String[] { USERNAME, EMAIL, FIRST_NAME, LAST_NAME, BIRTHDAY, PASSWORD1,
            PASSWORD2, PREFERED_LANGUAGE };

    transient Model model;

    private String email = "";
    private String username = "";
    private String password = "";
    private String birthDay = "";
    private ArrayList<Order> history;
    private String first_name = "";
    private String last_name = "";
    private Filter filter;

    private Language preferredLanguage;
    private boolean preferrsDarkTheme = true;

    private boolean isGuest = false;
    private boolean importGuestCartOnLogin = true;

    private Cart cart;

    private String userDataPath;
    private transient UserAuthKey key;

    public User(Model model) {
        this.model = model;
        isGuest = true;
        history = new ArrayList<>();
        preferredLanguage = Language.ENGLISH;
        email = "";
        username = "GUEST";
        password = "";
        first_name = "";
        last_name = "";
        filter = new Filter(this);
        cart = new Cart(model, this);
    }

    public User(Model model, String username, String password) {
        this.model = model;
        this.username = username;
        this.password = password;
        history = new ArrayList<>();
        filter = new Filter(this);
        cart = new Cart(model, this);
        createAuthKey();
    }

    public void setUserSettingsWithDataMap(THashMap<String, String> userDataInitMap) {
        for (KeyValuePair<String, String> dataPair : userDataInitMap.asKeyValuePair()) {
            switch (dataPair.key()) {
                case EMAIL:
                    this.email = dataPair.value();
                    break;
                case USERNAME:
                    this.username = dataPair.value();
                    break;
                case PASSWORD1:
                    this.password = dataPair.value();
                    break;
                case FIRST_NAME:
                    this.first_name = dataPair.value();
                    break;
                case LAST_NAME:
                    this.last_name = dataPair.value();
                    break;
                case BIRTHDAY:
                    this.birthDay = dataPair.value();
                    break;
                case PREFERED_LANGUAGE:
                    this.preferredLanguage = Language.getByIndex(Integer.parseInt(dataPair.value()));
                    break;

                default:
                    break;
            }
        }
    }

    public void createAuthKey() {
        userDataPath = StringTools.getRandomString(50);
        key = new UserAuthKey(password, userDataPath);
        key.safe(username);
    }

    public void regenerateAuthKey() {
        deleteAuthKey();
        deleteOldData();
        createAuthKey();
        model.replaceUserAuthKey(username, key);
    }

    private void deleteOldData() {
        File file = new File(User.USERDATA_STORAGE_PATH + userDataPath + ".ser");
        file.delete();
    }

    private void deleteAuthKey() {
        File authKeyFile = new File(UserAuthKey.authKeyFolderPath + username + ".ser");
        authKeyFile.delete();
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

    public String getEmail() {
        return email;
    }

    public Order[] getShopHistory() {
        return history.toArray(new Order[history.size()]);
    }

    public void buy() {

    }

    public Language getPreferredLanguage() {
        return (preferredLanguage == null) ? Language.getByIndex(0) : preferredLanguage;
    }

    public void logOff() {
        regenerateAuthKey();
        safeUser();
    }

    private void safeUser() {
        FileSaver.safeSerializableObject(User.USERDATA_STORAGE_PATH + userDataPath + ".ser", this, true);
    }

    public boolean getPreferresDarkTheme() {
        return preferrsDarkTheme;
    }

    public Model getModel() {
        return model;
    }

    public void initAfterSerialization(Model m) {
        this.model = m;
        filter.setModel(m);
        filter.setUser(this);
        filter.setDefaultTypeSettings(m.getDefaultTypeSettingsClone());
        cart.setUser(this);
    }

    public void purchaseCart() {
        if (history == null) {
            System.out.println("ups");
        }
        for (Order order : cart.getOrders()) {
            history.add(order);
        }
        cart.empty();
    }

    public boolean getLoadGuestCart() {
        return importGuestCartOnLogin;
    }
}
