package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.EventListenerList;

import GUI.UIController;
import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import Model.UserComponentes.User;
import Model.UserComponentes.UserAuthKey;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.NewUserLoginEvent;
import lib.Event.NewUserLoginListener;
import lib.fileHandling.FileLoader;
import lib.technicalComponents.Product;

public class Model {
    Car[] currentOptions;
    ArrayList<Product> allAvaliableObjects;

    HashMap<String, UserAuthKey> userAuthKeys;
    boolean lastAuthenticationSucess = false;

    // immer da
    User guest = new User();

    // usually guest, but you can log in
    User currentUser = guest;

    protected EventListenerList UserLoginChangeListenerList = new EventListenerList();

    /**
     * 
     */
    public Model() {
        loadExistingUserNames();
    }

    public User getLoggedUser() {
        return currentUser;
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

    public boolean lastAuthenticationDenied() {
        return !lastAuthenticationSucess;
    }

    public UserAuthKey authenticateLogin(String username, String password) {
        if (username.equals("")) {
            lastAuthenticationSucess = false;
            return null;
        }
        if (!userAuthKeys.containsKey(username)) {
            lastAuthenticationSucess = false;
            return null;
        }
        UserAuthKey key = userAuthKeys.get(username);
        if (key.checkPassword(password)) {
            lastAuthenticationSucess = true;
            return key;
        }
        lastAuthenticationSucess = false;
        return key;
    }

    private void loadExistingUserNames() {
        File folder = new File(UserAuthKey.authKeyFolderPath);
        userAuthKeys = new HashMap<>();
        if (folder.listFiles() == null) {

            return;
        }
        for (File file : folder.listFiles()) {
            // eigentlich nicht notwendig, weil es ja bekannt ist, welche Filetypen in dem Ordner sind, aber
            // sicher ist sicher
            if (file.isDirectory())
                continue;

            // load the file to an UserAuthKey
            try {
                UserAuthKey authkey = (UserAuthKey) FileLoader.loadSerializedObject(file);
                String name = file.getName();
                System.out.println(name);
                String userN = name.replace(".ser", "");
                System.out.println(userN);
                userAuthKeys.put(userN, authkey);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void logInUser(UserAuthKey key, String password) {
        String userDataFileName = key.getUserProfileDataFileName(password);
        String userDataFilePath = User.USERDATA_STORAGE_PATH + userDataFileName + ".ser";
        File userDataFile = new File(userDataFilePath);
        User user;
        try {
            user = (User) FileLoader.loadSerializedObject(userDataFile);
        } catch (ClassNotFoundException e) {
            System.out.println("User Profile Could not be found, Internal Error");
            e.printStackTrace();
            return;
        }
        setUser(user);
    }

    private void setUser(User user) {
        boolean oldWasGuest = currentUser.isGuest();
        currentUser = user;
        fireNewUserLoginEvent(new NewUserLoginEvent(this, user, oldWasGuest));
    }

    private void fireNewUserLoginEvent(NewUserLoginEvent event) {
        Object[] listeners = UserLoginChangeListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == NewUserLoginListener.class) {
                ((NewUserLoginListener) listeners[i + 1]).onUserLoginEvent(event);
            }
        }
    }

    public void addNewUserLoginListener(NewUserLoginListener listener) {
        UserLoginChangeListenerList.add(NewUserLoginListener.class, listener);
    }

    public void removeNewUserLoginListener(NewUserLoginListener listener) {
        UserLoginChangeListenerList.remove(NewUserLoginListener.class, listener);
    }

    public String createNewUser(THashMap<String, String> dataMap) {
        if (!dataMap.get(User.PASSWORD1).equals(dataMap.get(User.PASSWORD2))) {
            System.out.println("couldnt create User, because passwords dont match");
            return "Passwords do not match!";
        }
        if (dataMap.get(User.USERNAME).equals("")) {
            return "No Username!";
        }
        if (dataMap.get(User.PASSWORD1).equals("")) {
            return "No Password!";
        }
        if (doesUserExist(dataMap.get(User.USERNAME))) {
            System.out.println("couldnt create User bc User already exists");
            return "This user exists already";
        }
        User newUser = new User(dataMap.get(User.USERNAME), dataMap.get(User.PASSWORD1));
        setUser(newUser);
        return null;
    }

    public boolean doesUserExist(String username) {
        if (userAuthKeys.containsKey(username)) {
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
