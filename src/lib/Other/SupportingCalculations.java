package lib.Other;

public class SupportingCalculations {
    public static double round(double d, int i) {
        return (double) ((int) (d * Math.pow(10, i))) / Math.pow(10, i);
    }

    public static void main(String[] args) {
        System.out.println(round(1.1234123, 5));
    }

}
