package lib;

public class Intervall {
    private double numberA = 1;
    private double numberB = 0;

    public double getUpper() {
        return (numberA > numberB) ? numberA : numberB;
    }

    public double getLower() {
        return (numberA < numberB) ? numberA : numberB;
    }

    public void setA(double nr) {
        numberA = nr;
    }

    public void setB(double nr) {
        numberB = nr;
    }
}
