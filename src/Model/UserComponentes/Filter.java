package Model.UserComponentes;

public class Filter {
    User owner;

    double maxSpending = 50000;
    int minPrice = 0;
    int maxPrice = 50000;
    boolean upwardPriceLimit = true;

    boolean electric;
    boolean ice;

    String searchParameterString = "";

    UserBrandSettings[] brands;

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

}
