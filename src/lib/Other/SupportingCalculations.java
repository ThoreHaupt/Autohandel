package lib.Other;

public class SupportingCalculations {
    public static double round(double d, int i) {
        return (double) ((int) (d * Math.pow(10, i))) / Math.pow(10, i);
    }
}
