package lib.uiComponents.technicalUIComponents;

public class Intervall {
    int lower;
    int higher;

    /**
     * @param lower
     * @param higher
     */
    public Intervall(int lower, int higher) {
        this.lower = lower;
        this.higher = higher;
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
    public int getHigher() {
        return higher;
    }

}
