package Controller;

public class Controller {

    public LocalizationController lc;

    public Controller() {
        lc = new LocalizationController();
    }

    public void openSearchQuerey(String string) {
        System.out.println("searching with" + string);
    }
    // start Modell
    // init GUI

    public String[] searchDatabase(String string, int i) {
        return new String[] { "a", "b" };
    }
}
