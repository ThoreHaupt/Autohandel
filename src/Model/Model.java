package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.EventListenerList;

import Controller.Controller;
import GUI.shopPage.ProductPage;
import Model.ModelComponentes.Component;
import Model.ModelComponentes.Product;
import Model.UserComponentes.Cart;
import Model.UserComponentes.Filter;
import Model.UserComponentes.Order;
import Model.UserComponentes.TypeMutation;
import Model.UserComponentes.User;
import Model.UserComponentes.UserAuthKey;
import lib.DataStructures.HashMapImplementation.KeyValuePair;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartEvent;
import lib.Event.ChangeToCartListener;
import lib.Event.FilterChangeEvent;
import lib.Event.FilterChangeListener;
import lib.Event.NewUserLoginEvent;
import lib.Event.NewUserLoginListener;
import lib.Event.PurchaseEvent;
import lib.Event.PurchaseEventListener;
import lib.Other.StringTools;
import lib.fileHandling.FileLoader;
import lib.uiComponents.LoadingProgressbarFrame;

public class Model {

    Controller controller;

    ArrayList<Product> allAvaliableProducts = new ArrayList<>();

    HashMap<String, UserAuthKey> userAuthKeys;
    boolean lastAuthenticationSucess = false;

    public String sortFor = "";

    THashMap<String, THashMap<String, TypeMutation>> defaultTypeSettings;

    // immer da
    User guest;

    // usually guest, but you can log in
    User currentUser = guest;

    protected transient EventListenerList UserLoginChangeListenerList = new EventListenerList();
    protected transient EventListenerList cartChangeEventListenerList = new EventListenerList();
    protected transient EventListenerList filterChangeEventListenerList = new EventListenerList();
    protected transient EventListenerList purchaseEventListenerList = new EventListenerList();

    /**
     * 
     */
    public Model(Controller controller) {
        this.controller = controller;
        guest = new User(this);
        currentUser = guest;
        loadExistingUserNames();
        loadGivenDatabase();
        loadCostumDatabase(new File("Data/commons/ElectricCarSpreadSheet.csv"));
        guest.getFilter().pullTypeSettings();
    }

    public User getLoggedUser() {
        return currentUser;
    }

    public Product[] getCurrentProductOptions(Filter filter) {
        ArrayList<Product> egliableProducts = new ArrayList<>();
        Filter currentFilter = getCurrentUser().getFilter();
        for (int i = 0; i < allAvaliableProducts.size(); i++) {
            if (currentFilter.isEgliable(allAvaliableProducts.get(i))) {
                egliableProducts.add(allAvaliableProducts.get(i));
            }
        }

        return egliableProducts.toArray(new Product[egliableProducts.size()]);
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
        user.initAfterSerialization(this);
        setUser(user);
    }

    private void setUser(User user) {
        boolean oldWasGuest = currentUser.isGuest();
        currentUser = user;
        if (currentUser.getLoadGuestCart())
            loadGuestCartIntoCurrentUser();
        fireNewUserLoginEvent(new NewUserLoginEvent(this, user, oldWasGuest));
    }

    /**
     * When logging in a new User, there a couple of components that need to be changed, like the set Filters for example,
     * or the current Cart. Thus it makes sense to have an event that fires when that happens
     * @param event 
     */
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

    /**
     * creates a new User and then logs this user in as the current user. 
     * Since you can only reach the sign up Page over the log in page, it is not nessesary to log the current user out.
     * for a user it is nessesary to have a username and a password. So if one of those is not given, this will return an
     * error
     * @param dataMap the HashMap that contains information on the new User. It is only 
     * @return null if sucessfull, the errormessage if there was a problem
     */
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
        User newUser = new User(this, dataMap.get(User.USERNAME), dataMap.get(User.PASSWORD1));
        setUser(newUser);

        return null;
    }

    /**
     * checks if a given Username exists. Needed for SignUp, because it would be bad to have the same username twice
     * @param username the username
     * @return true if the user already exists
     */
    public boolean doesUserExist(String username) {
        if (userAuthKeys.containsKey(username)) {
            return true;
        }
        return false;
    }

    /**
     * get Method for current User
     * @return currentUser
     */
    public User getCurrentUser() {
        // Ich habe keine Ahnung warum, aber wenn man diesen Kommentar für die Methode benutzt, bekommt man 
        // einen Java.lang.VerifyError beim Initialisieren von Model. Wenn jemand weiß warum, ich würde es gerne Erfahren.
        /* return (currentUser == null) ? guest : currentUser; */
        return currentUser;
    }

    /**
     * passes along the Call to create a new Order in the current users cart.
     * @param product the product which is ordered
     * @param amount the amount
     */
    public void createOrder(Product product, int amount) {
        User user = getCurrentUser();
        user.getCart().addOrder(product, amount);
    }

    public void addChangeToCartListener(ChangeToCartListener listener) {
        cartChangeEventListenerList.add(ChangeToCartListener.class, listener);
    }

    public void removeChangeToCartListener(ChangeToCartListener listener) {
        cartChangeEventListenerList.remove(ChangeToCartListener.class, listener);
    }

    /**
     * This fires in the case that the contents of the current users cart change in any way. The purpose is for all
     * labels and texts to use this to update their information on the current Cart.
     * Theoratically this should propably be in Cart, but then I would have to reset all the Listeners everytime I change the User and this is
     * quite unpractical. It would propably work, but NO.
     * @param event The event that happend
     */
    public void fireChangeToCartEvent(ChangeToCartEvent event) {
        Object[] listeners = cartChangeEventListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ChangeToCartListener.class) {
                ((ChangeToCartListener) listeners[i + 1]).onChangeToCart(event);
            }
        }
    }

    public void loadGuestCartIntoCurrentUser() {
        Order[] guestOrders = guest.getCart().getOrders();
        for (Order order : guestOrders) {
            currentUser.getCart().addToCart(order);
        }
        guest.getCart().empty();
    }

    public void fireFilterChangeEvent(FilterChangeEvent event) {
        Object[] listeners = filterChangeEventListenerList.getListenerList();
        System.out.println("Filter changed apperantly");
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == FilterChangeListener.class) {
                ((FilterChangeListener) listeners[i + 1]).onFilterChange(event);
            }
        }
    }

    public void addFilterChangeListener(FilterChangeListener listener) {
        filterChangeEventListenerList.add(FilterChangeListener.class, listener);
    }

    public void removeFilterChangeListener(FilterChangeListener listener) {
        filterChangeEventListenerList.remove(FilterChangeListener.class, listener);
    }

    public String[] getSortingOptions() {
        return new String[] { "no Order", "price" };
    }

    public void addPurchaseEventListener(PurchaseEventListener l) {
        purchaseEventListenerList.add(PurchaseEventListener.class, l);
    }

    public void removePurchaseEventListener(PurchaseEventListener listener) {
        purchaseEventListenerList.remove(PurchaseEventListener.class, listener);
    }

    /**
     * When the user purchases the cart this event gets fired
     * @param event The event that happend
     */
    public void firePurchaseEvent(PurchaseEvent event) {
        Object[] listeners = purchaseEventListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == PurchaseEventListener.class) {
                ((PurchaseEventListener) listeners[i + 1]).onPurchaseEventCart(event);
            }
        }
    }

    public void purchaseCart() {
        currentUser.purchaseCart();
        firePurchaseEvent(new PurchaseEvent(this));
    }

    public void logOffCurrentUser() {
        currentUser.logOff();
        setUser(guest);
    }

    public void replaceUserAuthKey(String username, UserAuthKey key) {
        userAuthKeys.remove(username);
        userAuthKeys.put(username, key);
    }

    public String[] getCurrentClassStringArr() {
        Cart cart = currentUser.getCart();
        Order[] orders = cart.getOrders();

        String[] stringArr = new String[orders.length + 1];
        stringArr[0] = controller.lc.s("You selected and exported the following items:");
        for (int i = 0; i < stringArr.length; i++) {
            stringArr[i] = orders[i].toString();
        }

        return stringArr;
    }

    /**
     * loads the custom Datasets with cars 
    */
    public void loadCostumDatabase(File f) {
        loadProductsOfCustomData(f);
        extrapolateListOfComponentTypes();
    }

    /**
     * gets all Types and Brands from the Dataset and stores those into nested THashMaps as TypeMutation
     * This will be the default for every new Creation of a new filter
     */
    private void extrapolateListOfComponentTypes() {
        defaultTypeSettings = new THashMap<>();
        final String[] classifiedComponentTypes = { Product.TYPE, Product.BRAND };

        for (String componentType : classifiedComponentTypes) {
            THashMap<String, TypeMutation> innerMap = new THashMap<>();
            for (Product product : allAvaliableProducts) {
                Component typeComponent = product.getDataMap().get(componentType);
                if (typeComponent == null)
                    continue;
                String classification = typeComponent.getValue();
                if (classification == null)
                    continue;
                if (innerMap.containsKey(classification)) {
                    continue;
                }
                innerMap.put(classification, new TypeMutation(classification));
            }
            defaultTypeSettings.put(componentType, innerMap);
        }
    }

    public void loadGivenDatabase() {
        String[] dataArray = FileLoader.getallLinesFromFile(new File("Data/commons/infos.csv"));
        String[] splitededDataHeaders = { Product.TITLE, Product.PRICE, Product.DESCRIBTION };
        for (int i = 0; i < dataArray.length; i++) {
            THashMap<String, Component> map = new THashMap<>();
            String[] splitededData = dataArray[i].split(",");

            for (int j = 0; j < splitededDataHeaders.length; j++) {
                if (!splitededData[j].equals(""))
                    map.put(splitededDataHeaders[j], new Component(splitededDataHeaders[j], splitededData[j]));
            }
            allAvaliableProducts.add(new Product(map));
        }
    }

    public void loadProductsOfCustomData(File f) {
        LoadingProgressbarFrame progressbar = new LoadingProgressbarFrame();
        String[] dataArray = FileLoader.getallLinesFromFile(f);
        String[] splitededDataHeaders = dataArray[0].split(";");

        for (int i = 1; i < dataArray.length; i++) {
            THashMap<String, Component> map = new THashMap<>();
            String[] splitededData = dataArray[i].split(";");
            if (StringTools.checkEmptyArray(splitededData)) {
                continue;
            }
            for (int j = 0; j < splitededData.length; j++) {
                if (!splitededData[j].equals(""))
                    map.put(splitededDataHeaders[j], new Component(splitededDataHeaders[j], splitededData[j]));
            }
            if (map.get(Product.IMAGE) != null) {
                progressbar.setText(map.get(Product.IMAGE).getValue());
            }
            allAvaliableProducts.add(new Product(map));
            progressbar.increment(1.0 / dataArray.length);
        }

        progressbar.close();
    }

    public boolean canAffort(double price) {
        double budget = controller.getCurrentBudget();
        double usedBudget = controller.getCurrentCartValue() + price;

        if (budget == -1) {
            return true;
        }
        if (budget > usedBudget) {
            return true;
        }
        return false;
    }

    public THashMap<String, THashMap<String, TypeMutation>> getDefaultTypeSettingsClone() {
        THashMap<String, THashMap<String, TypeMutation>> deepClone = new THashMap<>();

        for (KeyValuePair<String, THashMap<String, TypeMutation>> classifiableComponentPair : defaultTypeSettings
                .asKeyValuePair()) {
            THashMap<String, TypeMutation> innerMap = new THashMap<>();
            for (KeyValuePair<String, TypeMutation> typeTypeSettingPair : innerMap.asKeyValuePair()) {
                innerMap.put(new String(typeTypeSettingPair.key()), typeTypeSettingPair.value().deepClone());
            }
            deepClone.put(classifiableComponentPair.key(), innerMap);
        }

        return defaultTypeSettings;
    }
}
