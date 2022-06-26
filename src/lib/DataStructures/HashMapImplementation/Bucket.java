package lib.DataStructures.HashMapImplementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Bucket<K, V> implements Iterable<HashNode<K, V>>, Serializable {
    THashMap<K, V> map;
    LinkedHashNode<K, V> head = null;
    TreeHashNode<K, V> root = null;
    int size = 0;
    boolean tree = false;

    private final int TREEIFY_THRESHOLD = 9;
    private final int UNTREEIFY_THRESHOLD = 7;

    public Bucket(THashMap<K, V> map) {
        this.map = map;
    }

    public V get(int hash, K key) {
        if (head == null) {
            return null;
        }
        if (tree) {
            return root.get(hash, key);
        }
        LinkedHashNode<K, V> currentNode = (LinkedHashNode<K, V>) head;
        while (currentNode.hash != hash) {
            currentNode = currentNode.next;
            if (currentNode == null)
                return null;
        }
        return currentNode.value;
    }

    /**
     */
    public boolean add(int hash, K key, V value) {
        if (tree) {
            if (root == null)
                throw new RuntimeException("There is a tree, but its empty");
            TreeHashNode<K, V> newNode = new TreeHashNode<K, V>(this, hash, key, value);
            if (root.add(newNode)) {
                size++;
                return true;
            } else {
                return false;
            }
        }

        LinkedHashNode<K, V> newNode = new LinkedHashNode<K, V>(this, hash, key, value);

        if (head == null) {
            head = newNode;
            size++;
            return true;
        }

        if (head.hash == hash) {
            head.value = value;
            return false;
        }

        LinkedHashNode<K, V> current = head;
        while (current.next != null) {
            if (current.hash == hash) {
                current.value = value;
                return false;
            }
            current = current.next;
        }
        current.next = newNode;
        size++;
        checkTREEIFY();
        return true;
    }

    public boolean remove(int hash, K key) {
        if ((!tree && head == null) || (tree && root == null)) {
            // throw new KeyNotFoundException();
            System.out.println("Tried to remove an node in an empty bucket");
            return false;
        }
        if (tree) {
            if (root.remove(hash)) {
                size--;
                checkUNTREEIFY();
                return true;
            }
            return false;
        }
        if (head.hash == hash) {
            head = ((LinkedHashNode<K, V>) head).next;
            size--;
            return true;
        }
        LinkedHashNode<K, V> current = (LinkedHashNode<K, V>) head;
        while (current.next != null) {
            if (current.next.hash == hash) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        System.out.println("couldnt remove Node");
        return false;
    }

    private void checkUNTREEIFY() {
        if (size <= UNTREEIFY_THRESHOLD) {
            tree = false;
            UNTREEIFY();
        }
    }

    private void checkTREEIFY() {
        if (size >= TREEIFY_THRESHOLD) {
            tree = true;
            TREEIFY();
        }
    }

    private void TREEIFY() {
        // System.out.println("treeify");
        LinkedHashNode<K, V> current = head.next;
        root = new TreeHashNode<K, V>(this, head.hash, head.key, head.value);
        while (current != null) {
            root.add(new TreeHashNode<K, V>(this, current.hash, current.key, current.value));
            current = current.next;
        }
        head = null;
    }

    private void UNTREEIFY() {
        // System.out.println("untreeify");
        size = 0;
        HashNode<K, V> node;
        Iterator<HashNode<K, V>> iterator = preIterator(true);
        while (iterator.hasNext()) {
            node = iterator.next();
            add(node.hash, node.key, node.value);
        }

    }

    public LinkedHashNode<K, V> getNodeByHash(int hash) {
        LinkedHashNode<K, V> current = (LinkedHashNode<K, V>) head;
        while (current.next != null) {
            if (current.hash == hash) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return preIterator(tree);
    }

    public Iterator<HashNode<K, V>> preIterator(boolean ISTREE) {
        Iterator<HashNode<K, V>> iterator;

        if (ISTREE) {
            iterator = new Iterator<HashNode<K, V>>() {
                ArrayList<HashNode<K, V>> nodeStack = new ArrayList<HashNode<K, V>>();
                private TreeHashNode<K, V> current = root;
                private TreeHashNode<K, V> target;
                private int next = 1;
                private int nextloop;
                private boolean loop = true;
                private int c = 0;

                @Override
                public boolean hasNext() {

                    if (root == null)
                        return false;

                    if (c < size())
                        return true;

                    if (!nodeStack.isEmpty())
                        System.out.println("Der Tree hat nicht alle elemente Wiedergegeben");
                    return (false);
                }

                @Override
                public HashNode<K, V> next() {
                    while (loop) {

                        if (next == 1) {
                            while (current.left != null) {
                                nodeStack.add(current);
                                current = current.left;

                            }
                            loop = false;
                            nextloop = 2;

                        } else if (next == 2) {
                            if (current.right != null) {
                                current = current.right;
                                nextloop = 1;
                            } else {
                                nextloop = 3;
                            }

                        } else if (next == 3) {
                            if (nodeStack.isEmpty()) {
                                nextloop = 2;
                                continue;
                            }
                            target = (TreeHashNode<K, V>) nodeStack.get(nodeStack.size() - 1);
                            while (current != target) {
                                current = current.parent;
                            }
                            nodeStack.remove(nodeStack.size() - 1);
                            nextloop = 2;
                            loop = false;
                        }
                        next = nextloop;

                    }
                    c++;
                    loop = true;
                    return current;

                }

            };
        } else {
            iterator = new Iterator<HashNode<K, V>>() {

                private LinkedHashNode<K, V> currentNode = (LinkedHashNode<K, V>) head;

                @Override
                public boolean hasNext() {
                    if (currentNode != null)
                        return true;
                    return false;
                }

                @Override
                public HashNode<K, V> next() {
                    LinkedHashNode<K, V> outputNode = currentNode;
                    if (currentNode != null)
                        currentNode = currentNode.next;
                    return outputNode;
                }

            };
        }
        return iterator;
    }

    public int size() {
        return size;
    }

}
