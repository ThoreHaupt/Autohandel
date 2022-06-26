package lib.DataStructures.HashMapImplementation;

import java.io.Serializable;
import java.util.Iterator;

/**
 * I didnt know how to iterate well over a HashMap and I wanted to implement my own anyway, so here it is:
 * It does archtiectually reaseemble the actual THashMap from the official java Library, but is much slower obviously.
 * The difference is not as bad as one might think tho.
 */
public class THashMap<K, V> implements Iterable<V>, Serializable {

    private Bucket<K, V>[] buckets;
    private int currentExponentSize;;
    private float loadFactor;
    private int size = 0;
    private int starterArraySize;

    /**
     * Constructs an empty HashMap with the initial BucketArray of the length 2^4 and a load factor of 0.75
     */
    public THashMap() {
        this((int) Math.pow(2, 4), 0.75f);
    }

    /**
     * Constructs an empty HashMap with the initial BucketArray of the length 2^4 and a coustum loadFactor
     * @param loadFactor
     */
    public THashMap(float loadFactor) {
        this((int) Math.pow(2, 4), loadFactor);
    }

    /**
     * Constructs an empty HashMap with an custom initial BucketArray.length and a coustum loadFactor
     * @param starterArraySize
     * @param loadFactor
     */
    @SuppressWarnings("unchecked")
    public THashMap(int starterArraySize, float loadFactor) {
        this.buckets = new Bucket[starterArraySize];
        this.starterArraySize = starterArraySize;
        this.currentExponentSize = (int) (Math.log(starterArraySize) / Math.log(2));
        this.loadFactor = loadFactor;

    }

    /**
     * puts an Object into the HashMap
     * @param key
     * @param value
     */
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

    /**
     * gets an Object from the HashMap
     * @param key
     * @return
     */
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

    /**
     * removes an Object from the HashMap
     * @param key
     */
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

    /**
     * Checks weather the size of the bucketArray is still big enough for the amount of Items.
     * if that is not the case this will return false and trigger a rehash
     * this is determined by the loadFactor
     */
    private boolean checkForRehash() {
        double freshhold = (double) size / (double) buckets.length;
        if (freshhold > loadFactor) {
            return true;
        }
        return false;
    }

    /**
     * creates a bigger Array and then sorts every KeyValuePair into the new BucketArray
     * @param exponentChange
     */
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

    /**
     * Berechnet normalerweise über Object.hashCode den Hash eines Objektes. Wenn es sich jedoch um einen String handelt,
     * muss man besonders aufpassen, da die unsichtbaren Charaktere einem wirklich zu schaffe machen können, wenn man mit 
     * Files arbeitet
     * 
     * @param key
     * @return
     */
    private int calculateHash(K key) {

        // return System.identityHashCode(key);
        if (key instanceof String) {
            // int i = 0;
            // es ist absolut disgusting, dass ees einfach unsichtbare Buchstaben gibt, die einem nicht angezeigt werden. Danke StackOverflow für eine Lösung
            String k_string = ((String) key).replaceAll("\\p{C}", "");
            /* String asciiEncodedString = new String(k_string.getBytes(), StandardCharsets.US_ASCII);
            for (char c : asciiEncodedString.toCharArray()) {
                i += Math.pow(Math.E, (c - 35) / 4.0);
                i = i >> 5 % 31;
            } */
            return Math.abs(k_string.hashCode());
        }

        return Math.abs(key.hashCode());

    }

    /**
     * calculates the Index of the bucket this has has to be put into
     * @param hash
     * @param bucketArraySize
     * @return
     */
    private int calculateBucketIndex(int hash, int bucketArraySize) {
        return (hash % (bucketArraySize - 1));
    }

    /**
     * calculates the last bucket used in the map, for the iterator
     * @return
     */
    private int getLastUsedBucketIndex() {
        int last = -1;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null && buckets[i].size() > 0)
                last = i;
        }

        return last;
    }

    /**
     * gets the next Bucket in the Array
     * @param start
     * @return
     */
    private int getNextUsedBucketIndex(int start) {
        int i = start + 1;
        while (buckets[i] == null || (buckets[i].head == null && buckets[i].root == null)) {
            i++;
            if (i > buckets.length - 1)
                return buckets.length - 1;
        }
        return i;
    }

    /**
     * Iterates over all values in this Array
     */
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

    /**
     * Iterates over all Key, Value Pairs of this Array
     */
    public class KeyValuePairObj implements Iterable<KeyValuePair<K, V>> {

        @Override
        public Iterator<KeyValuePair<K, V>> iterator() {
            return getKeyValuePair();
        }
    }

    /**
     * The iterator, that can iterate over Key, Value Pairs
     */
    public KeyValuePairObj asKeyValuePair() {
        KeyValuePairObj obj = new KeyValuePairObj();
        return obj;
    }

    /**
     * The Iterator that iterates over the Key and Value Pairs
     * @return
     */
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

    /**
     * returns the amount of Objects in the HashMap
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Tests if the HashMap is functioning and if all Elements are in the correct Buckets
     */
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

    /**
     * returns true if the Key is in this HashMap
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        return (get(key) == null) ? false : true;
    }

    /**
     * removes all Elements from the HashMap
     */
    @SuppressWarnings("unchecked")
    public void empty() {
        this.buckets = new Bucket[starterArraySize];
        this.size = 0;
        this.currentExponentSize = (int) (Math.log(starterArraySize) / Math.log(2));
    }

}
