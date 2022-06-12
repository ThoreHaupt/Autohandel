package Model.UserComponentes;

import java.util.ArrayList;

import Model.ModelComponentes.Car;

public class User {
    private String email;
    private String username;
    private String password;
    private int id;
    private ArrayList<Car> cart;
    private ArrayList<Car> history;
    private String first_name;
    private String last_name;
    private Filter filter;

    public Filter getFilter() {
        return null;
    }

    public String getUserName() {
        return null;
    }
}
