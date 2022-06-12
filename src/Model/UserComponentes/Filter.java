package Model.UserComponentes;

import lib.Intervall;

public class Filter {
    User owner;

    double maxSpending;
    Intervall spendingRange = new Intervall();
    boolean electric;
    boolean ice;

    public User getUser() {
        return owner;
    }

}
