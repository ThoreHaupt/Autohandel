package lib.DataStructures.HashMapImplementation;

public class HashNode<K, V> {
    int hash;
    Bucket<K, V> bucket;
    K key;
    V value;

    public HashNode(Bucket<K, V> bucket, int hash, K key, V value) {
        this.hash = hash;
        this.value = value;
        this.bucket = bucket;
        this.key = key;
    }

    public void overrideThisNode(TreeHashNode<K, V> node) {
        hash = node.hash;
        key = node.key;
        value = node.value;
    }
}
