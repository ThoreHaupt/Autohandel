package Model.ModelComponentes;

public class Component implements Comparable<Component> {
    private String type;
    private String value;
    private boolean isNumeric;
    private double num_value;
    private boolean undefined = true;

    public Component(String string) {
    }

    public Component(String ComponentType, String value) {

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
