package Model.UserComponentes;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Model.Model;
import Model.ModelComponentes.Product;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartEvent;
import lib.Event.ChangeToCartListener;
import lib.Other.SupportingCalculations;

public class Cart implements Serializable {
    THashMap<Integer, Order> contents;
    User user;
    Model model;

    public Cart(Model model) {
        this.model = model;
        contents = new THashMap<>();
    }

    public void addToCart(Order p) {
        contents.put(p.getID(), p);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void removeFromCart(Order p) {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public double getTotalPrice() {
        return SupportingCalculations.round(calculatePrice(), 2);
    }

    public void fireChangeToCartEvent(ChangeToCartEvent event) {
        model.fireChangeToCartEvent(event);
    }

    /**
     * Sums up the Values of all Orders in the Basket
     * @return the Total value, not rounded
     */
    private double calculatePrice() {
        double sum = 0;
        for (Order order : contents) {
            sum += order.getOrderValue();
        }
        return sum;
    }

    public void deleteOrder(Order order) {
        contents.remove(order.getID());
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public Order[] getOrders() {
        ArrayList<Order> tempList = new ArrayList<>();
        contents.forEach(e -> tempList.add(e));

        return tempList.toArray(new Order[contents.size()]);
    }

    public void addOrder(Product product, int amount) {
        Order newOrder = new Order(product, amount);
        contents.put(newOrder.getID(), newOrder);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void notifyChange() {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

}
