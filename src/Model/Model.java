package Model;

import java.util.ArrayList;

import Model.ModelComponentes.Car;
import Model.UserComponentes.User;

public class Model {
    Car[] currentOptions;
    ArrayList<Car> allAvaliableObjects;
    User user = new User();

    public User getLoggedUser() {
        return user;
    }

}
