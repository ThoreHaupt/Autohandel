package Model.UserComponentes;

import java.util.ArrayList;

import Model.ModelComponentes.Car;

public class User {
    private String email = "";
    private String username = "GUEST";
    private String password = "";
    private int id = 0;
    private ArrayList<Order> history;
    private String first_name = "";
    private String last_name = "";
    private Filter filter = new Filter();

    private Cart cart = new Cart();

    public Filter getFilter() {
        return filter;
    }

    public String getUserFirstName() {
        return first_name;
    }

    public String getUserLastName() {
        return last_name;
    }

    public Cart getCart() {
        return cart;
    }
}
