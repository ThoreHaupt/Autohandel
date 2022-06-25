package Model.ModelComponentes;

import java.util.Comparator;
import javax.swing.ImageIcon;

import GUI.shopPage.ProductPage;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Other.ImageTools;

public class Product {

    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String DESCRIBTION = "describtion";
    public static final String TYPE = "type";
    public static final String RANGE = "range";
    public static final String BRAND = "brand";
    public static final String IMAGE = "image";

    public static final String[] sortableComponentAttributes = new String[] { TITLE, PRICE, RANGE, BRAND };
    public static final String[] numericAttributes = new String[] { PRICE, RANGE };
    public static final String[] stringAttributes = new String[] { TITLE, BRAND };

    THashMap<String, Component> dataMap;
    ProductDescribtion describtion;
    ImageIcon image;

    ProductPage productPage;

    public Product(String string) {
        dataMap = new THashMap<>();
    }

    public Product(THashMap<String, Component> dataMap) {
        this.dataMap = dataMap;
        loadImage();
        if (!dataMap.containsKey(PRICE)) {
            System.out.println("Error, tried to add product without price");
        }
        if (!dataMap.containsKey(TYPE)) {
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

    public ImageIcon getImage() {
        return this.image;
    }

    public void loadImage() {
        String path;
        if (!dataMap.containsKey(IMAGE))
            path = ImageTools.defaultNoImagePath;
        else {
            //path = dataMap.get(IMAGE).getValue();
            path = ImageTools.defaultNoImagePath;
        }
        this.image = ImageTools.getIconFromAnyLocation(path);
    }

    public String getTitleString() {
        Component titleComponent = dataMap.get(TITLE);
        if (titleComponent != null) {
            return titleComponent.getValue();
        } else {
            return "Title missing";
        }
    }

    public String getDescribtionTitle() {
        return getTitleString();
    }

    public String getShortInformationText() {
        Component titleComponent = dataMap.get(DESCRIBTION);
        if (titleComponent != null) {
            return titleComponent.getValue();
        } else {
            return "Product.";
        }
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

    public String getType() {
        return null;
    }

    public String getBrand() {
        return null;
    }

    public THashMap<String, Component> getDataMap() {
        return dataMap;
    }

}
