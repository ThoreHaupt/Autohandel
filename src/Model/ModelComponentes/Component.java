package Model.ModelComponentes;

import java.util.Arrays;

import lib.Other.StringTools;

public class Component implements Comparable<Component> {
    private String type;
    private String value;
    private boolean isNumeric;
    private double num_value;

    public Component(String string) {
    }

    public Component(String ComponentType, String value) {
        this.type = ComponentType;
        this.value = value;
        isNumeric = Arrays.stream(Product.numericAttributes).anyMatch(ComponentType::equals);
        if (isNumeric)
            this.num_value = StringTools.getNumbersFromString(value);
    }

    @Override
    public int compareTo(Component o) {
        if (!this.type.equals(o.getType()))
            return 0;
        if (isNumeric)
            return Double.compare(num_value, o.getNum_value());
        else
            return value.compareTo(o.getValue());
    }

    private Object getType() {
        return this.type;
    }

    /**
     * @return the num_value
     */
    public double getNum_value() {
        return num_value;
    }

    public String getValue() {
        return this.value;
    }

}
