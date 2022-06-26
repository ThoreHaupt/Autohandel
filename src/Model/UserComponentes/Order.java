package Model.UserComponentes;

import java.io.Serializable;
import java.util.Comparator;

import GUI.userPage.cartPage.CartEntry;
import Model.ModelComponentes.Product;

public class Order implements Serializable {
    private int amount;
    Product product;
    private int ID;
    private transient Cart cart;

    public Order(Cart cart, Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.cart = cart;
        /* 
        Ja, ist nicht richtig sicher, aber keine Lust jetzt nen System zu bauen, 
        dass Speichert welche OrderID schon vergeben wurde. Weil das muss dann ja speicherbar sein usw, 
        könnte man theoreisch in StartupProperties, aber nein
        */
        this.ID = (int) (Math.random() * 899999 + 100000);
    }

    /**
     * calculates and returns the order value
     * @return
     */
    public double getOrderValue() {
        return product.getPrice() * amount;
    }

    public int getID() {
        return ID;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Then the order amount changes, the cart needs to be told that it now has a different value and so on
     * @param entry
     * @param newAmount
     */
    public void orderAmountChanged(CartEntry entry, int newAmount) {
        amount = newAmount;
        cart.notifyChange();
    }

    public void deleteOrder() {
        cart.deleteOrder(this);
    }

    @Override
    public String toString() {
        String s = product.getTitleString() + ": " + amount + " x";
        return s;
    }

    /**
     * Compares two products, by providing the type and the direction of Sorting.
     * @param type
     * @return
     */
    public static Comparator<Order> getComperator(String type, boolean sortUpwards) {
        Comparator<Order> comperator = new Comparator<Order>() {

            @Override
            public int compare(Order o1, Order o2) {
                Product po1 = o1.getProduct();
                Product po2 = o2.getProduct();

                return Product.getComperator(type, sortUpwards).compare(po1, po2);
            }
        };
        return comperator;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
