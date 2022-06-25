package Model.UserComponentes;

import java.io.Serializable;

import Model.Model;
import Model.ModelComponentes.Product;
import Model.ModelComponentes.TypeMutation;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.FilterChangeEvent;
import lib.uiComponents.technicalUIComponents.SpendingrangeIntervall;

public class Filter implements Serializable {
    transient User owner;
    transient Model model;
    transient THashMap<String, THashMap<String, TypeMutation>> typeSettings = new THashMap<>();

    SpendingrangeIntervall spendingRange = new SpendingrangeIntervall(5000, 60000);

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

    public User getUser() {
        return owner;
    }

    public void getTypeMutationsFromModel() {

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

    public void setICE(boolean stateChange) {
        electric = stateChange;
        fireChangeToFilterEvent(new FilterChangeEvent(this));
    }

    public void setElectro(boolean b) {
        this.ice = b;
        fireChangeToFilterEvent(new FilterChangeEvent(this));
    }

    public void setFilterMaxSpending(boolean selected) {
        filterNonAffortable = selected;
        fireChangeToFilterEvent(new FilterChangeEvent(this));
    }

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

    public SpendingrangeIntervall getSpendingIntervall() {
        return this.spendingRange;
    }

    public boolean getICE() {
        return this.ice;
    }

    public boolean getElectro() {
        return this.electric;
    }

    public void setModel(Model m) {
        this.model = m;
    }

    public void setUser(User user) {
        this.owner = user;
    }

    public String getTitleByType(String type) {
        return null;
    }

    public TypeMutation[] getTypeMutations(String type) {
        return new TypeMutation[0];
    }

    public boolean isEgliable(Product product) {
        double price = product.getPrice();
        if (maximumBudget != -1) {
            if (filterNonAffortable) {
                if (price > maximumBudget)
                    return false;
            }
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
}
