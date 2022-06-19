package Model.UserComponentes;

import java.io.Serializable;

import Model.ModelComponentes.Brand;

public class UserBrandSettings implements Serializable {

    boolean filterActive;
    Brand brand;

    public String getName() {
        return brand.getName();
    }

    public void setCurrentStatus(boolean selected) {
        filterActive = selected;
    }

}
