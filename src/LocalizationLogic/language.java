package LocalizationLogic;

public enum Language {
    ENGLISH(0),
    GERMAN(1);

    private int index;

    private Language(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
