package Model.UserComponentes;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Model;
import Model.ModelComponentes.Product;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Event.ChangeToCartEvent;
import lib.Other.SupportingCalculations;

/**
 * The cart stores all the orders placed by the user and can be stored or exported
 */
public class Cart implements Serializable {
    THashMap<Integer, Order> contents;
    transient User user;
    transient Model model;

    /**
     * creates a new Cart, used on User Creation, because on login the user will desirialize his own cart from a file
     * @param model
     * @param user
     */
    public Cart(Model model, User user) {
        this.model = model;
        this.user = user;
        contents = new THashMap<>();
    }

    /**
     * This adds an Order to the cart and then fires the Event
     * @param p
     */
    public void addToCart(Order p) {
        if (checkForAffort(p)) {
            // ask if to overwrite setting -> ignore maximum Constraint
            return;
        }
        contents.put(p.getID(), p);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    /**
     * returns wether or not the amount can be afforted with the current given budget and the value of the already selected Items
     * in the cart
     * @param p
     * @return
     */
    private boolean checkForAffort(Order p) {
        Filter userFilter = user.getFilter();
        if (userFilter.getMaximumBudget() == -1) {
            return true;
        }
        if (userFilter.getMaximumBudget() >= getTotalPrice() + p.getOrderValue()) {
            return true;
        }
        return false;
    }

    public void removeFromCart(Order p) {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public double getTotalPrice() {
        return SupportingCalculations.round(calculatePrice(), 2);
    }

    /**
     * fires the Change to cart event in model. I think it would be better if the Event was stored here, but on the other hand with changeing 
     * Users, and changing carts, you would have to always create new references to the cart and that seemes kind of counterproductive
     * @param event
     */
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

    /**
     * deletes an order from the cart
     * @param order
     */
    public void deleteOrder(Order order) {
        contents.remove(order.getID());
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    /**
     * gets All orders in an Array
     * @return
     */
    public Order[] getOrders() {
        ArrayList<Order> tempList = new ArrayList<>();
        contents.forEach(e -> tempList.add(e));

        return tempList.toArray(new Order[contents.size()]);
    }

    /**
     * adds an order to the cart by taking amount and product seperately
     * @param product
     * @param amount
     */
    public void addOrder(Product product, int amount) {
        Order newOrder = new Order(this, product, amount);
        contents.put(newOrder.getID(), newOrder);
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void notifyChange() {
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void empty() {
        contents.empty();
        fireChangeToCartEvent(new ChangeToCartEvent(this));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int size() {
        return contents.size();
    }

    public void initAfterSerialization(Model m) {
        this.model = m;
        this.user = model.getCurrentUser();
        for (Order order : contents) {
            order.setCart(this);
        }
    }

    public Model getModel() {
        return this.model;
    }

}
