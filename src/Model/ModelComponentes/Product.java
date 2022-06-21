package Model.ModelComponentes;

import GUI.shopPage.ProductPage;
import lib.DataStructures.HashMapImplementation.THashMap;

public class Product {
    double price = 420.69;

    THashMap<String, Component> dataMap = new THashMap<>();
    ProductDescribtion describtion;

    ProductPage productPage;

    public Product(String string) {
    }

    public double getPrice() {
        return price;
    }

    public String getImageString() {
        return "resources/GUI_images/no_ImageImage.png";
    }

    public String getTitleString() {
        return "Random Product";
    }

    public String getDescribtionTitle() {
        return "I am a very nice Product, shut the fuck up,... please";
    }

    public String getShortInformationText() {
        return "Who even are you?";
    }

    public String getPriceString() {
        return "" + price;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public void setPrdoductPage(ProductPage productPage) {
        this.productPage = productPage;
    }

}
