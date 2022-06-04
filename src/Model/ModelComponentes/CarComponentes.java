package Model.ModelComponentes;

public class CarComponentes implements Comparable<CarComponentes> {
    private String type;
    private String value;
    private boolean isNumeric;
    private double num_value;

    @Override
    public int compareTo(CarComponentes o) {
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
