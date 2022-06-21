package lib.DataStructures.HashMapImplementation;

import java.io.Serializable;
import java.util.Iterator;

public class THashMap<K, V> implements Iterable<V>, Serializable {

    private Bucket<K, V>[] buckets;
    private int currentExponentSize;;
    private float loadFactor;
    private int size = 0;
    private int starterArraySize;

    public THashMap() {
        this((int) Math.pow(2, 4), 0.75f);
    }

    public THashMap(float loadFactor) {
        this((int) Math.pow(2, 4), loadFactor);
    }

    @SuppressWarnings("unchecked")
    public THashMap(int starterArraySize, float loadFactor) {
        this.buckets = new Bucket[starterArraySize];
        this.starterArraySize = starterArraySize;
        this.currentExponentSize = (int) (Math.log(starterArraySize) / Math.log(2));
        this.loadFactor = loadFactor;

    }

    public void put(K key, V value) {
        if (key == null)
            new RuntimeException("can't put a null as key for entry");
        int hash = calculateHash(key);
        if (put(hash, key, value, buckets, buckets.length)) {
            size++;
            if (checkForRehash()) {
                rehash(1);
            }
        }
    }

    private boolean put(int hash, K key, V value, Bucket<K, V>[] bucketArray, int bucketArraySize) {
        int bucket = calculateBucketIndex(hash, bucketArraySize);
        return addNodeToBucket(bucketArray, bucket, hash, key, value);
    }

    public V get(K key) {
        int hash = calculateHash(key);
        int bucketIndex = calculateBucketIndex(hash, buckets.length);
        if ((buckets[bucketIndex]) == null)
            return null;
        V value = buckets[bucketIndex].get(hash, key);
        if (value == null) {
            return null;
        }
        return value;
    }

    public void remove(K key) {
        int hash = calculateHash(key);
        int bucketIndex = calculateBucketIndex(hash, buckets.length);
        if (buckets[bucketIndex] == null) {
            System.out.println("PROBLEM");
            return;
        }
        if (buckets[bucketIndex].remove(hash, key)) {
            size--;
            /*
             * if (buckets[bucketIndex].size() == 0)
             * buckets[bucketIndex] = null;
             */
        }
    }

    private boolean addNodeToBucket(Bucket<K, V>[] bucketArray, int bucketIndex, int hash, K key, V value) {
        if (bucketArray[bucketIndex] == null) {
            bucketArray[bucketIndex] = new Bucket<K, V>(this);
        }
        // returns true, if a new Element is added and not replaced
        return bucketArray[bucketIndex].add(hash, key, value);
    }

    private boolean checkForRehash() {
        double freshhold = (double) size / (double) buckets.length;
        if (freshhold > loadFactor) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void rehash(int exponentChange) {
        currentExponentSize += exponentChange;
        int tempBucketArraySize = (int) Math.pow(2, currentExponentSize);
        Bucket<K, V>[] bucketsTemp = new Bucket[tempBucketArraySize];
        for (Bucket<K, V> bucket : buckets) {
            if (bucket == null || (bucket.head == null && bucket.root == null))
                continue;
            for (HashNode<K, V> node : bucket) {
                put(node.hash, node.key, node.value, bucketsTemp, tempBucketArraySize);
            }
        }
        this.buckets = bucketsTemp;
    }

    private int calculateHash(K key) {

        // return System.identityHashCode(key);

        return Math.abs(key.hashCode());

    }

    private int calculateBucketIndex(int hash, int bucketArraySize) {
        return (hash % (bucketArraySize - 1));
    }

    private int getLastUsedBucketIndex() {
        int last = -1;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null && buckets[i].size() > 0)
                last = i;
        }

        return last;
    }

    private int getNextUsedBucketIndex(int start) {
        int i = start + 1;
        while (buckets[i] == null || (buckets[i].head == null && buckets[i].root == null)) {
            i++;
            if (i > buckets.length - 1)
                return buckets.length - 1;
        }
        return i;
    }

    @Override
    public Iterator<V> iterator() {
        THashMap<K, V> map = this;
        Iterator<V> iterator = new Iterator<V>() {

            int currentBucket = getNextUsedBucketIndex(-1);
            int lastUsedBucketIndex = getLastUsedBucketIndex();
            Bucket<K, V> firstBucket = (buckets[currentBucket] != null) ? buckets[currentBucket]
                    : new Bucket<K, V>(map);
            Iterator<HashNode<K, V>> currentInterator = firstBucket.iterator();

            @Override
            public boolean hasNext() {

                if (currentBucket < lastUsedBucketIndex && currentBucket != -1) {
                    return true;
                }
                if (currentBucket == -1) {
                    return false;
                }
                return currentInterator.hasNext();
            }

            @Override
            public V next() {

                if (!currentInterator.hasNext()) {
                    currentBucket = getNextUsedBucketIndex(currentBucket);
                    currentInterator = buckets[currentBucket].iterator();
                }
                return currentInterator.next().value;
            }

        };
        return iterator;
    }

    public class KeyValuePairObj implements Iterable<KeyValuePair<K, V>> {

        @Override
        public Iterator<KeyValuePair<K, V>> iterator() {
            return getKeyValuePair();
        }
    }

    public KeyValuePairObj asKeyValuePair() {
        KeyValuePairObj obj = new KeyValuePairObj();
        return obj;
    }

    public Iterator<KeyValuePair<K, V>> getKeyValuePair() {
        THashMap<K, V> map = this;
        Iterator<KeyValuePair<K, V>> iterator = new Iterator<KeyValuePair<K, V>>() {

            int currentBucket = getNextUsedBucketIndex(-1);
            int lastUsedBucketIndex = getLastUsedBucketIndex();
            Bucket<K, V> firstBucket = (buckets[currentBucket] != null) ? buckets[currentBucket]
                    : new Bucket<K, V>(map);
            Iterator<HashNode<K, V>> currentInterator = firstBucket.iterator();

            @Override
            public boolean hasNext() {

                if (currentBucket < lastUsedBucketIndex && currentBucket != -1) {
                    return true;
                }
                if (currentBucket == -1) {
                    return false;
                }
                return currentInterator.hasNext();
            }

            @Override
            public KeyValuePair<K, V> next() {

                if (!currentInterator.hasNext()) {
                    currentBucket = getNextUsedBucketIndex(currentBucket);
                    currentInterator = buckets[currentBucket].iterator();
                }
                HashNode<K, V> node = currentInterator.next();
                return new KeyValuePair<K, V>(node.key, node.value);
            }
        };
        return iterator;
    }

    public int size() {
        return size;
    }

    public void testIntegrity() {
        int c = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null)
                continue;
            int z = 0;
            for (HashNode<K, V> node : buckets[i]) {
                c++;
                z++;
                if (calculateBucketIndex(calculateHash(node.key), buckets.length) != i) {
                    System.out
                            .println("A Element is not in the correct bucket: (" + node.key + ", " + node.value + ")");
                }
            }
            if (z != buckets[i].size())
                System.out.println("In Bucket " + i + " fehlen " + (buckets[i].size() - z) + " Elemente");
            z = 0;
        }
        System.out.println("gemessene Anzahl der Elemente in dieser HashMap: " + c);
    }

    public boolean containsKey(K key) {
        return (get(key) == null) ? false : true;
    }

    @SuppressWarnings("unchecked")
    public void empty() {
        this.buckets = new Bucket[starterArraySize];
        this.size = 0;
        this.currentExponentSize = (int) (Math.log(starterArraySize) / Math.log(2));
    }

}
