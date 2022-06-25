package Model.ModelComponentes;

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

    // Different Types:

    public TypeMutation(String name) {
        this.name = name;
    }

    public Icon getName() {
        return null;
    }

    public void setCurrentStatus(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
