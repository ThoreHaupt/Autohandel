package Model.ModelComponentes;

import java.util.HashMap;

public class Car {
    HashMap<String, CarComponentes> carComponentes = new HashMap<>();
    String imagePath = "resources/GUI_images/no_ImageImage.png";
    double price = 696969.430D;
    Double age = null;
    String describtion;

    public Car(String describtionText) {
        describtion = describtionText;
    }

    public String getDescribtionTitle() {
        return "null";
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getShortInformationText() {
        return describtion;
    }

    public String getPriceString() {
        return "" + price;
    }

    public String getExtensiveInformationText() {
        return "es git momentan noch keine Textfunktion... \n \n \n aber das ist kein Problem.";
    }
}
