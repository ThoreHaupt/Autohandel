package lib.DataStructures.HashMapImplementation;

public class LinkedHashNode<K, V> extends HashNode<K, V> {

    protected LinkedHashNode<K, V> next;

    public LinkedHashNode(Bucket<K, V> bucket, int hash, K key, V value) {
        super(bucket, hash, key, value);
    }

}
