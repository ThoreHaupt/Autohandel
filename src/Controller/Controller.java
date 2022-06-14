package Controller;

import GUI.UIController;
import GUI.shopPage.ShopGalleryEntry;
import LocalizationLogic.LocalizationController;
import Model.Model;
import Model.ModelComponentes.Car;
import Model.UserComponentes.Filter;
import Model.UserComponentes.User;

public class Controller {

    public LocalizationController lc;
    private UIController uiController;
    private Model model;

    public Controller() {
        lc = new LocalizationController();
        model = new Model();
        this.uiController = new UIController(this);
    }

    public void openSearchQuerey(String string) {
        System.out.println("searching with" + string);
    }

    public String[] searchDatabase(String string, int i) {
        return new String[] { "a", "b" };
    }

    public void setLanguage(Object selectedItem) {
    }

    public String[] getLanuguageImageArray() {
        String[] s = new String[] { "resources/GUI_images/IconUS_transparent.png",
                "resources/GUI_images/IconGer_transparent.png" };
        return s;
    }

    public UIController getUIController() {
        return uiController;
    }

    public void addToCart(Car car) {
    }

    public Car[] getOptions(Filter filter) {
        return new Car[] { new Car("a"), new Car("asdada"), new Car("asdawdawd"), new Car("asd"), new Car(""),
                new Car("Eadsdawdasdawd<afsdufhlysiufhliyushfliysuhfliyushflidhfgluiyhfl") };

    }

    public User getUser() {
        return new User();
    }

    public LocalizationController getLanguageController() {
        return lc;
    }
}
