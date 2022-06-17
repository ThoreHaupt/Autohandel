package LocalizationLogic;

public enum Language {
    ENGLISH(0, "English"),
    GERMAN(1, "German");

    private int index;
    private String normal;

    private Language(int index, String normal) {
        this.index = index;
        this.normal = normal;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return normal;
    }

    public static Language getByIndex(int selectedIndex) {
        for (Language l : values()) {
            if (l.getIndex() == selectedIndex) {
                return l;
            }
        }
        return null;
    }
}
