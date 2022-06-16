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
}
