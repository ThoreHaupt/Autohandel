package lib.uiComponents.technicalUIComponents;

import java.io.Serializable;

public class SpendingrangeIntervall implements Serializable {
    int lower;
    int upper;

    /**
     * @param lower
     * @param higher
     */
    public SpendingrangeIntervall(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * @return the lower
     */
    public int getLower() {
        return lower;
    }

    /**
     * @return the higher
     */
    public int getUpper() {
        return upper;
    }

    public boolean isInRange(double i) {
        if (lower == -1) {
            return true;
        }
        if (upper == -1) {
            return true;
        }
        if (lower <= i && i <= upper) {
            return true;
        }
        return false;
    }

}
