package Model.UserComponentes;

import GUI.cartPage.CartEntry;
import Model.ModelComponentes.Product;

public class Order {
    private int amount;
    Product product;
    private int ID;
    private transient Cart cart;

    public Order(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        /* 
        Ja, ist nicht richtig sicher, aber keine Lust jetzt nen System zu bauen, 
        dass Speichert welche OrderID schon vergeben wurde. Weil das muss dann ja speicherbar sein usw, 
        könnte man theoreisch in StartupProperties, aber nein
        */
        this.ID = (int) (Math.random() * 899999 + 100000);
    }

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

    public void orderAmountChanged(CartEntry entry, int newAmount) {
        amount = newAmount;
        cart.notifyChange();
    }

    public void deleteOrder() {
        cart.deleteOrder(this);
    }
}
