package Model.UserComponentes;

import lib.Intervall;

public class Filter {
    User owner;

    double maxSpending;
    Intervall spendingRange = new Intervall();
    boolean electric;
    boolean ice;

    UserBrandSettings[] brands;

    public User getUser() {
        return owner;
    }

    public UserBrandSettings[] getBrands() {
        return brands;
    }

    public void getBrandsFromModel() {

    }

}
