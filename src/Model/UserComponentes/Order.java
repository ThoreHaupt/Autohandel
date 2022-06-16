package Model.UserComponentes;

import lib.Event.ChangeToCartEvent;
import lib.technicalComponents.Product;

public class Order {
    private int amount;
    Product product;
    private int ID;

    public Order(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public double getOrderValue() {
        return product.getPrice() * amount;
    }

    public int getID() {
        return ID;
    }
}
