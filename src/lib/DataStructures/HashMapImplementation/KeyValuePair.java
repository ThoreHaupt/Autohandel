package lib.DataStructures.HashMapImplementation;

public class KeyValuePair<K, V> {
    private K key;
    private V value;

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }

    /**
     * @param key
     * @param value
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
