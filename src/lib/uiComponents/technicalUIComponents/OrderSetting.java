package lib.uiComponents.technicalUIComponents;

public class OrderSetting {
    String type;
    boolean upwards;

    /**
     * @param type The type after which to sort
     * @param upwards the direction of sorting
     */
    public OrderSetting(String type, boolean upwards) {
        this.type = type;
        this.upwards = upwards;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the upwards
     */
    public boolean isUpwards() {
        return upwards;
    }

    @Override
    public String toString() {
        return type + " " + upwards;
    }
}
