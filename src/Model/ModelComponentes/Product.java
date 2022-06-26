package Model.ModelComponentes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.ImageIcon;

import GUI.shopPage.ProductPage;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.Other.ImageTools;

public class Product implements Serializable {

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

    ArrayList<ImageIcon> imageInstances = new ArrayList<>();

    transient ProductPage productPage;

    public Product(String string) {
        dataMap = new THashMap<>();
    }

    /**
     * creates a new Product from the DataMap
     * @param dataMap
     */
    public Product(THashMap<String, Component> dataMap) {
        this.dataMap = dataMap;
        loadImage(0);
        loadImage(1);
        loadImage(2);
        if (!dataMap.containsKey(PRICE)) {
            System.out.println("Error, tried to add product without price");
        }
        if (!dataMap.containsKey(TYPE)) {
            System.out.println("Error, tried to add product without pyte");
        }
    }

    /**
     * returns the price from the Datamap and -1 if there is no price
     * @return
     */
    public double getPrice() {
        Component priceComponent = dataMap.get(PRICE);
        if (priceComponent == null) {
            return -1;
        } else {
            return priceComponent.getNum_value();
        }
    }

    /**
     * returns the image. Because it is hard to do a copy for an image and Image Icons share the same underlying image, 
     * it was nessesary to load the same image mutlible times and store each dynamically in an array List
     * @param i
     * @return
     */
    public ImageIcon getImage(int i) {
        if (imageInstances.size() < i) {
            loadImage(i);
            return imageInstances.get(i);
        }
        return imageInstances.get(i);
    }

    /**
     * loads the image either from files or from the internet
     * @param i
     */
    public void loadImage(int i) {
        String path;
        if (!dataMap.containsKey(IMAGE))
            path = ImageTools.defaultNoImagePath;
        else {
            //path = dataMap.get(IMAGE).getValue();
            path = ImageTools.defaultNoImagePath;
        }
        imageInstances.add(i, ImageTools.getIconFromAnyLocation(path));
    }

    /**
     * returns the title String
     * @return
     */
    public String getTitleString() {
        Component titleComponent = dataMap.get(TITLE);
        if (titleComponent != null) {
            return titleComponent.getValue();
        } else {
            return "Title missing";
        }
    }

    /**
     * returns the title right now, in the future, one might implement some changes here to customize everything
     * @return
     */
    public String getDescribtionTitle() {
        return getTitleString();
    }

    /**
     * returns a short information text, which is generated automatically, if the right Information is provided
     * @return
     */
    public String getShortInformationText() {
        Component titleComponent = dataMap.get(DESCRIBTION);
        if (titleComponent != null) {
            return titleComponent.getValue();
        } else {
            generateDescribtion();
            return getShortInformationText();
        }
    }

    /**
     * generates the Describtion String
     */
    private void generateDescribtion() {
        String newDescribtion = "This is the " + getDescribtionTitle() + ", made by " + getBrand() + ". ";

        if (dataMap.get(RANGE) != null) {
            newDescribtion += "It has an official range of up to: " + dataMap.get(RANGE).getNum_value() + " km.";
        }

        dataMap.put(DESCRIBTION, new Component(DESCRIBTION, newDescribtion));
    }

    /**
     * returns the String containing the price and the Euro Sign, but this is the right place to localize it in the future
     * @return
     */
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
                Component co1 = o1.getDataMap().get(type);
                Component co2 = o2.getDataMap().get(type);

                if (co1 == null && co2 == null) {
                    return 0;
                }
                if (co1 == null && co2 != null) {
                    return -1 * (sortUpwards ? 1 : -1);
                }
                if (co1 != null && co2 == null) {
                    return 1 * (sortUpwards ? 1 : -1);
                }
                return co1.compareTo(co2) * (sortUpwards ? 1 : -1);
            }
        };
        return comperator;
    }

    /**
     * every car has a type, like BEV, ICE usw
     * @return
     */
    public String getType() {
        Component typeComponent = dataMap.get(TYPE);
        if (typeComponent != null) {
            return typeComponent.getValue();
        } else {
            return null;
        }
    }

    /**
     * returns the brand of the vehicle, as long as it was in the database
     * @return
     */
    public String getBrand() {
        Component brandComponent = dataMap.get(BRAND);
        if (brandComponent != null) {
            return brandComponent.getValue();
        } else {
            return null;
        }
    }

    public THashMap<String, Component> getDataMap() {
        return dataMap;
    }

}
