package Model.UserComponentes;

import Model.Model;
import lib.Event.FilterChangeEvent;
import lib.uiComponents.technicalUIComponents.Intervall;

public class Filter {
    transient User owner;
    transient Model model;

    double maxSpending = 50000;
    int minPrice = 0;
    int maxPrice = 50000;
    boolean upwardPriceLimit = true;

    boolean electric;
    boolean ice;

    String searchParameterString = "";

    UserBrandSettings[] brands;

    public Filter(User owner) {
        this.owner = owner;
        brands = new UserBrandSettings[0];
        this.model = owner.getModel();
    }

    public User getUser() {
        return owner;
    }

    /**
     * @param maxSpending the maxSpending to set
     */
    public void setMaxSpending(double maxSpending) {
        this.maxSpending = maxSpending;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;

    }

    /**
     * @param upwardPriceLimit the upwardPriceLimit to set
     */
    public void setUpwardPriceLimit(boolean upwardPriceLimit) {
        this.upwardPriceLimit = upwardPriceLimit;
    }

    /**
     * @param electric the electric to set
     */
    public void setElectric(boolean electric) {
        this.electric = electric;
    }

    /**
     * @param ice the ice to set
     */
    public void setIce(boolean ice) {
        this.ice = ice;
    }

    public UserBrandSettings[] getBrands() {
        return brands;
    }

    public void getBrandsFromModel() {

    }

    public double getMaximumBudget() {
        return 0;
    }

    private void fireChangeToFilterEvent(FilterChangeEvent event) {
        model.fireFilterChangeEvent(new FilterChangeEvent(this));
    }

    public Object setICE(int stateChange) {
        return null;
    }

    public Object setElectro(boolean b) {
        return null;
    }

    public Object setFilterMaxSpending(boolean selected) {
        return null;
    }

    public Object setSpendingRange(Intervall intervall) {
        return null;
    }

}
