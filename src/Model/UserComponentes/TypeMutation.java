package Model.UserComponentes;

import java.io.Serializable;

import javax.swing.Icon;

/**
 * This class stores the setting to a permutation of a product - type, 
 * like for example EV being a permutation of TYPE
 * The other major case is the brand. Every Object (should) have a brand associated. VW -> Brand
 */
public class TypeMutation implements Serializable {

    String name;
    boolean selected = true;

    Filter filter;

    // Different Types:

    public TypeMutation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 
     * @param selected
     */
    public void setCurrentStatus(boolean selected) {
        boolean old = this.selected;
        this.selected = selected;
        if (old != selected) {
            filter.fireFilterChange();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public TypeMutation deepClone() {
        return new TypeMutation(this.name);
    }

}
