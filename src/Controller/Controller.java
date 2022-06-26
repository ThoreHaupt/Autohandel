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
import lib.fileHandling.FileSaver;
import lib.uiComponents.technicalUIComponents.OrderSetting;

public class Controller {

    public LocalizationController lc;
    private UIController uiController;
    private Model model;

    public Controller() {
        this.lc = new LocalizationController();
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

    public User getUser() {
        return model.getLoggedUser();
    }

    public LocalizationController getLocalizationController() {
        return lc;
    }

    public String getCurrentFreeBudget() {
        Filter filter = getCurrentUser().getFilter();

        double currentMaxBudget = filter.getMaximumBudget();
        double currentSpending = getCurrentCartValue();
        return (currentMaxBudget >= 0) ? SupportingCalculations.round(currentMaxBudget - currentSpending, 2) + ""
                : "unlimited";
    }

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

        uiController.closeWindow();
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
