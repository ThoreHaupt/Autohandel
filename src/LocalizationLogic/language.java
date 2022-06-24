package LocalizationLogic;

public enum Language {
    ENGLISH(0, "English", "resources/GUI_images/IconUS_transparent.png)"),
    GERMAN(1, "German", "resources/GUI_images/IconGer_transparent.png");

    private int index;
    private String normal;
    private String imagePath;

    private Language(int index, String normal, String imagePath) {
        this.index = index;
        this.normal = normal;
        this.imagePath = imagePath;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return normal;
    }

    public String getImagePath() {
        return imagePath;
    }

    public static Language getByIndex(int selectedIndex) {
        for (Language l : values()) {
            if (l.getIndex() == selectedIndex) {
                return l;
            }
        }
        return null;
    }

    public static String[] getLanuguageImageArray() {
        String[] arr = new String[Language.values().length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Language.getByIndex(i).getImagePath();
        }
        return arr;
    }
}
