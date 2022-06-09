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

    public void setLanguage(Object selectedItem) {
    }

    public String[] getLanuguageImageArray() {
        String[] s = new String[] { "resources/GUI_images/IconUS_transparent.png",
                "resources/GUI_images/IconGer_transparent.png" };
        return s;
    }
}
