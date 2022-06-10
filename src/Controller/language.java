package Controller;

public enum language {
    ENGLISH(0),
    GERMAN(0);

    private int index;

    private language(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
