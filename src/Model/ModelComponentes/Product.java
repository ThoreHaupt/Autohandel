package Model.ModelComponentes;

import java.util.Comparator;

import GUI.shopPage.ProductPage;
import lib.DataStructures.HashMapImplementation.THashMap;

public class Product {

    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String DESCRIBTION = "describtion";
    public static final String TYPE = "type";
    public static final String RANGE = "range";
    public static final String BRAND = "brand";

    String[] sortableComponentAttributes = new String[] { TITLE, PRICE, RANGE, BRAND };

    THashMap<String, Component> dataMap = new THashMap<>();
    ProductDescribtion describtion;

    ProductPage productPage;

    public Product(String string) {

    }

    public Product(THashMap<String, Component> dataMap) {
        this.dataMap = dataMap;
        if (!dataMap.containsKey("Price")) {
            System.out.println("Error, tried to add product without price");
        }
        if (!dataMap.containsKey("Type")) {
            System.out.println("Error, tried to add product without pyte");
        }
    }

    public double getPrice() {
        Component priceComponent = dataMap.get(PRICE);
        if (priceComponent == null) {
            return -1;
        } else {
            return priceComponent.getNum_value();
        }
    }

    public String getImageString() {
        return "resources/GUI_images/no_ImageImage.png";
    }

    public String getTitleString() {
        return dataMap.get(TITLE).getValue();
    }

    public String getDescribtionTitle() {
        return "I am a very nice Product, shut the fuck up,... please";
    }

    public String getShortInformationText() {
        return "Who even are you?";
    }

    public String getPriceString() {
        Component priceComponent = dataMap.get(PRICE);
        if (priceComponent == null) {
            return "unknown price";
        } else {
            return priceComponent.getNum_value() + " €";
        }
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public void setPrdoductPage(ProductPage productPage) {
        this.productPage = productPage;
    }

    public boolean hasPrice() {
        return dataMap.containsKey(PRICE);
    }

    /**
     * Compares two products, by providing the type and the direction of Sorting.
     * @param type
     * @return
     */
    public static Comparator<Product> getComperator(String type, boolean sortUpwards) {
        Comparator<Product> comperator = new Comparator<Product>() {

            @Override
            public int compare(Product o1, Product o2) {

                return 0;
            }

        };

        return comperator;
    }

}
