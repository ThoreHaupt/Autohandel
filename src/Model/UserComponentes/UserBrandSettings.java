package Model.UserComponentes;

import Model.ModelComponentes.Brand;

public class UserBrandSettings {

    boolean filterActive;
    Brand brand;

    public String getName() {
        return brand.getName();
    }

    public void setCurrentStatus(boolean selected) {
        filterActive = selected;
    }

}
