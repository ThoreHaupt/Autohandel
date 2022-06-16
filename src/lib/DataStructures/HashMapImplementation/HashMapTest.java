package lib.DataStructures.HashMapImplementation;

import java.util.HashMap;
import java.util.Random;

public class HashMapTest {
    public static void main(String[] args) {
        THashMap<Integer, Integer> map = new THashMap<Integer, Integer>();
        HashMap<Integer, Integer> mapOff = new HashMap<Integer, Integer>();

        int length = 20;
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = i;
        }

        shuffleArray(array);

        long t1 = System.nanoTime();
        for (int i = 0; i < length; i++) {
            map.put(array[i], i);
        }
        System.out.println("map put:" + (System.nanoTime() - t1) / length);

        long t2 = System.nanoTime();
        for (int i = 0; i < length; i++) {
            mapOff.put(array[i], i);
        }
        System.out.println("mapOff put:" + (System.nanoTime() - t2) / length);

        long t3 = System.nanoTime();
        for (int i = 0; i < length; i++) {
            mapOff.get(i);
            Integer.toString(i);
        }
        System.out.println("mapOff get:" + (System.nanoTime() - t3) / length);

        long t4 = System.nanoTime();
        /*
         * for (KeyValuePair<Integer, Integer> KVPair : map.asKeyValuePair()) {
         * KVPair.toString();
         * }
         */
        for (int i = 0; i < length; i++) {
            map.get(i);
            Integer.toString(i);
        }

        System.out.println("map get:" + (System.nanoTime() - t4) / length);

        long t5 = System.nanoTime();
        for (int i = 0; i < length; i += 1) {
            map.remove(i);
        }
        System.out.println("map remove:" + (System.nanoTime() - t5) / length);

        long t6 = System.nanoTime();
        for (int i = 0; i < length; i += 1) {
            mapOff.remove(i);
        }
        System.out.println("mapOff remove:" + (System.nanoTime() - t6) / length);

        /*
         * for (Integer integer : map) {
         * l.add(integer);
         * }
         */

        /*
         * long t1 = System.nanoTime();
         * for (int i = 0; i < length; i++) {
         * map.get(i);
         * }
         * System.out.println((System.nanoTime() - t1) / 10000);
         */

        /*
         * long t2 = System.nanoTime();
         * for (int i = 0; i < 10000; i++) {
         * s.add(mapOff.get(i));
         * }
         * System.out.println((System.nanoTime() - t2) / 10000);
         */

        // System.out.println(l);
        /*
         * System.out.println(l.size());
         * System.out.println(s.size());
         */
    }

    private static void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
