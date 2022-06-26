package Controller;

import java.io.File;

import javax.swing.JOptionPane;

import GUI.UIController;
import LocalizationLogic.LocalizationController;
import LocalizationLogic.Language;
import Model.Model;
import Model.ModelComponentes.Product;
import Model.UserComponentes.Filter;
import Model.UserComponentes.Order;
import Model.UserComponentes.User;
import Model.UserComponentes.UserAuthKey;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartListener;
import lib.Event.NewUserLoginListener;
import lib.Event.PurchaseEventListener;
import lib.Other.SupportingCalculations;
import lib.fileHandling.FileLoader;
import lib.fileHandling.FileSaver;
import lib.uiComponents.technicalUIComponents.OrderSetting;

public class Controller {

    // this variable name is short, because it is a really long name and gets called in row quite often
    public LocalizationController lc;
    private UIController uiController;
    private Model model;

    //Shut of the image import from the internet
    //======================HERE=========================
    public static final boolean downloadImages = false;
    //===================================================

    private StartupProperties startProperties;

    private static final String startupPropertyPath = "Data/commons/startupInfo.ser";

    /**
     * Initializes all important other controllers, which then intialize all other elements
     */
    public Controller() {
        this.lc = new LocalizationController(this);
        model = new Model(this);
        this.uiController = new UIController(this);
    }

    public void openSearchQuerey(String string) {
        System.out.println("searching with" + string);
    }

    public String[] searchDatabase(String string, int i) {
        return new String[] { "a", "b" };
    }

    public void setLanguage(int selectedItem) {
        lc.setLanguage(Language.values()[selectedItem]);
    }

    public UIController getUIController() {
        return uiController;
    }

    public void addToCart(Order oder) {
    }

    public Product[] getFilteredProducts(Filter filter) {
        return model.getCurrentProductOptions(filter);
    }

    /**
     * reads startupproperties from Data, which got it from a file storing /
     * serializable object which is saved
     * 
     */
    private void loadStartUpInfoFile() {
        File f = new File(startupPropertyPath);
        StartupProperties s = null;
        if (f.exists())
            try {
                s = (StartupProperties) FileLoader.loadSerializedObject(f);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        else {
            startProperties = new StartupProperties();
            return;
        }
        startProperties = s;
    }

    /**
     * sets the data to be used on next init
     */
    public void setStartUpFile() {
        startProperties.setLanguage(lc.getCurrentLanguage());
        startProperties.setLightTheme(uiController.isLightTheme());
        FileSaver.safeSerializableObject(startupPropertyPath, startProperties, false);
    }

    /**
     * gets the start up Properties, if they have not been deserialized yet, the method calls the loadStartUpInfoFile()
     * @return
     */
    public StartupProperties getStartUpInfoFile() {
        if (startProperties == null) {
            loadStartUpInfoFile();
        }
        return startProperties;
    }

    public User getUser() {
        return model.getLoggedUser();
    }

    public LocalizationController getLocalizationController() {
        return lc;
    }

    /**
     * calcualtes the String that represents the current budget left over
     */
    public String getCurrentFreeBudget() {
        Filter filter = getCurrentUser().getFilter();

        double currentMaxBudget = filter.getMaximumBudget();
        double currentSpending = getCurrentCartValue();
        return (currentMaxBudget >= 0) ? SupportingCalculations.round(currentMaxBudget - currentSpending, 2) + ""
                : "unlimited";
    }

    /**
     * returns if the current Free budget is positive( calculates if you overspend)
     * @return
     */
    public boolean isCurrentFreeBudgetPositive() {
        Filter filter = getCurrentUser().getFilter();

        double currentMaxBudget = filter.getMaximumBudget();
        double currentSpending = getCurrentCartValue();
        if (currentMaxBudget < 0 || (currentMaxBudget - currentSpending) > 0)
            return true;
        return false;
    }

    public double getCurrentCartValue() {
        return getCurrentUser().getCart().getTotalPrice();
    }

    public User getCurrentUser() {
        return model.getLoggedUser();
    }

    public boolean isCurrentUserGuest() {
        return model.isCurrentUserGuest();
    }

    public void addToOrder(Product product, int amount) {
        model.createOrder(product, amount);
        System.out.println("new Order: " + amount + " x " + product.getDescribtionTitle());
    }

    /**
     * When the UserProfileButton gets pressed, this directs the Call to the correct page based on wether or not the current user is guest
     */
    public void UserProfileButtonRequest() {
        if (isCurrentUserGuest()) {
            uiController.setWindowContent(UIController.LOGIN_PAGE);
        } else {
            uiController.setWindowContent(UIController.USERPROFILE_PAGE);
        }
    }

    public void addNewUserLoginListener(NewUserLoginListener EventListener) {
        model.addNewUserLoginListener(EventListener);
    }

    /**
     * takes the username and password and passes it to the modell, which returns the key to the userdata if the user exists.
     * if the user doesn't exist, there will be a hint that this user doesnt exist.
     * if the password is wrong the "password is wrong" message will appear.
     * @param username username
     * @param password password
     */
    public void attemptLogin(String username, String password) {
        if (username == null || username.equals("")) {
            uiController.displayDeniedLoginMessage("");
            return;
        }
        UserAuthKey key = model.authenticateLogin(username, password);
        if (key == null) {
            uiController.displayDeniedLoginMessage("This user does not exist!");
            return;
        }
        if (!key.checkPassword(password)) {
            uiController.displayDeniedLoginMessage("Wrong Password. Try again!");
            return;
        }
        model.logInUser(key, password);
        lc.setLanguage(model.getLoggedUser().getPreferredLanguage());
        uiController.setDarkTheme(model.getLoggedUser().getPreferresDarkTheme());
        uiController.regenerateUserDefinedPanels();
        uiController.setWindowContent(UIController.MAINSTORE_PAGE);
    }

    /**
     * this method handels the shutdown process
     */
    public void intiShutDownSequence() {

        if (model.getCurrentUser().getCart().size() > 0) {
            // if guest, make a popup, that lets you safe a file
            String msg = "If you are loged in as a guest"
                    + (isCurrentUserGuest() ? "(you are)" : "you aren't)")
                    + ", you might want to export your cart before quitting. Otherwise its contents may be lost."
                    + " Do you want to continue?";
            int shutdownPermission = JOptionPane.showConfirmDialog(uiController.getWindow(), lc.s(msg), "waring",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            switch (shutdownPermission) {
                case 0:
                    break;
                case 1:
                case 2:
                    return;
                default:
                    break;
            }
        }

        // log out user properly before shutdown

        if (!isCurrentUserGuest()) {
            model.getCurrentUser().logOff();
        }

        setStartUpFile();

        uiController.closeWindow();
        // der AWT Event Handler fängt sich immer in einer loop und idk warum. Deswegen sysexit
        System.exit(0);
    }

    /**
     * takes all the input needed to create a new User. 
     * if there are not all nessesary inputs, this will return a Error message
     * @param datamap a HashMap with Data to set the UserInformation. 
     * @return the errormessage or null, when the User Creation was successfull
     */
    public String signUpAttempt(THashMap<String, String> dataMap) {
        String s = model.createNewUser(dataMap);
        if (s == null) {
            uiController.setWindowContent(UIController.MAINSTORE_PAGE);
        }
        return s;
    }

    public String[] getLanguageStringArray() {
        return LocalizationController.getLanguageStringArray();
    }

    public void setLanguage(Language language) {
        lc.setLanguage(language);
    }

    public Language getCurrentLanguage() {
        return lc.getCurrentLanguage();
    }

    /**
     * passing that along, because it makes sense, that you could do that
     * @param listener
     */
    public void addChangeToCartListener(ChangeToCartListener listener) {
        model.addChangeToCartListener(listener);
    }

    public void removeAddToCartListener(ChangeToCartListener listener) {
        model.removeChangeToCartListener(listener);
    }

    public Model getModel() {
        return model;
    }

    public void buyCart() {
        model.purchaseCart();
        uiController.setWindowContent(UIController.THANK_YOU_4_PUCHASE);
    }

    /**
     * this handels the Export of the cart to the given File f
     * @param f
     */
    public void exportCurrentCart(File f) {
        String[] array = model.getCurrentClassStringArr();
        FileSaver.saveFile(f, array);
    }

    public double getCurrentBudget() {
        return model.getCurrentUser().getFilter().getMaximumBudget();
    }

    public void addPurchaseEventListener(PurchaseEventListener l) {
        model.addPurchaseEventListener(l);
    }

    public void logOffCurrentUser() {
        model.logOffCurrentUser();
        uiController.setWindowContent(UIController.MAINSTORE_PAGE);
    }

    public OrderSetting getDefaultOrderSetting() {
        return new OrderSetting(Product.TITLE, true);
    }

}
