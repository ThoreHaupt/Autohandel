package Model.ModelComponentes;

import java.io.Serializable;
import java.util.HashMap;

import Controller.Controller;

public class Car extends Product implements Serializable {
    HashMap<String, Component> carComponentes = new HashMap<>();
    String imagePath = "resources/GUI_images/no_ImageImage.png";
    Double age = null;
    String describtion;

    public Car(String describtionText) {

        describtion = describtionText;
    }

    public String getDescribtionTitle() {
        return "AutoTitel";
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
