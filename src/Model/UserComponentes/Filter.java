package Model.UserComponentes;

import Model.Model;
import lib.Event.FilterChangeEvent;
import lib.uiComponents.technicalUIComponents.SpendingrangeIntervall;

public class Filter {
    transient User owner;
    transient Model model;

    SpendingrangeIntervall spendingRange;

    int maximumBudget = -1;

    boolean upwardPriceLimit = true;
    boolean filterNonAffortable = true;

    boolean electric = true;
    boolean ice = true;

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

    public UserBrandSettings[] getBrands() {
        return brands;
    }

    public void getBrandsFromModel() {

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

}
