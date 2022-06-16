package lib.DataStructures.HashMapImplementation;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String keyname) {
        super("This key is not in this HashMap: " + keyname);
    }
}
