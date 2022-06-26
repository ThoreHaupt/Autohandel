package Model.UserComponentes;

import java.io.Serializable;

import Model.Model;
import Model.ModelComponentes.Product;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.FilterChangeEvent;
import lib.uiComponents.technicalUIComponents.SpendingrangeIntervall;

public class Filter implements Serializable {
    transient User owner;
    transient Model model;
    transient THashMap<String, THashMap<String, TypeMutation>> typeSettings;
    public final static THashMap<String, String> typeBrandTitleTable = new THashMap<>();

    static {
        initLookUpTable();
    }

    SpendingrangeIntervall spendingRange = new SpendingrangeIntervall(0, 60000);

    int maximumBudget = -1;

    boolean upwardPriceLimit = true;
    boolean filterNonAffortable = true;

    boolean electric = true;
    boolean ice = true;

    String searchParameterString = "";

    public Filter(User owner) {
        this.owner = owner;
        this.model = owner.getModel();

    }

    /**
     * this method gets the default type settings from the model
     */
    public void pullTypeSettings() {
        this.typeSettings = model.getDefaultTypeSettingsClone();
        for (THashMap<String, TypeMutation> componentClass : typeSettings) {
            for (TypeMutation typeMutation : componentClass) {
                typeMutation.setFilter(this);
            }
        }
    }

    /**
     * This initializes the look up table for the Type selection in the filter
     */
    private static void initLookUpTable() {
        typeBrandTitleTable.put(Product.BRAND, "Brand: ");
        typeBrandTitleTable.put(Product.TYPE, "Type: ");
    }

    /**
     * returns the user of this cart
     * @return
     */
    public User getUser() {
        return owner;
    }

    public void setMaxBudget(int i) {
        maximumBudget = i;
        model.fireFilterChangeEvent(new FilterChangeEvent(this));
    }

    public int getMaximumBudget() {
        return maximumBudget;
    }

    private void fireChangeToFilterEvent(FilterChangeEvent event) {
        model.fireFilterChangeEvent(new FilterChangeEvent(this));
    }

    public void setFilterMaxSpending(boolean selected) {
        filterNonAffortable = selected;
        fireChangeToFilterEvent(new FilterChangeEvent(this));
    }

    /**
     * Sets the range of allowed Items to an intervall
     * @param intervall
     */
    public void setSpendingRange(SpendingrangeIntervall intervall) {
        if (intervall.getUpper() == -1) {
            upwardPriceLimit = false;
        } else {
            upwardPriceLimit = true;
        }
        this.spendingRange = intervall;
        fireChangeToFilterEvent(new FilterChangeEvent(this));
    }

    public boolean getFilterNonAffortable() {
        return filterNonAffortable;
    }

    /**
     * 
     * @return
     */
    public SpendingrangeIntervall getSpendingIntervall() {
        return this.spendingRange;
    }

    public void setModel(Model m) {
        this.model = m;
    }

    public void setUser(User user) {
        this.owner = user;
    }

    public String getTitleByType(String type) {
        return typeBrandTitleTable.get(type);
    }

    /**
     * gets the type settings for a type
     * @param type
     * @return
     */
    public THashMap<String, TypeMutation> getTypeMutations(String type) {
        return typeSettings.get(type);
    }

    /**
     * returns true, if the product is allowed passes "through" this filter. Otherwise this will be false
     * @param product
     * @return
     */
    public boolean isEgliable(Product product) {
        double price = product.getPrice();
        if (filterNonAffortable && !model.canAffort(price)) {
            return false;
        }
        if (!spendingRange.isInRange(product.getPrice())) {
            return false;
        }
        String type = product.getType();
        if (type == null) {
            return true;
        }
        if (!typeSettings.get(Product.TYPE).get(type).isSelected()) {
            return false;
        }
        String brand = product.getBrand(); // the brad this product has
        if (brand != null && !typeSettings.get(Product.BRAND).get(brand).isSelected()) {
            return false;
        }
        return true;
    }

    public void setDefaultTypeSettings(THashMap<String, THashMap<String, TypeMutation>> defaultTypeSettings) {
        this.typeSettings = defaultTypeSettings;
    }

    public void fireFilterChange() {
        model.fireFilterChangeEvent(new FilterChangeEvent(this));
    }
}
