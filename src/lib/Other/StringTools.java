package lib.Other;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A collection of different Methods to work with Strings
 */
public class StringTools {
    public static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c))
                return false;
        }
        return true;
    }

    /**
     * generates a random String of Characters in a certain ASCii range
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        ArrayList<Character> l = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            l.add((char) (Math.random() * 25 + 65));
        }
        char[] arr = new char[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = l.get(i);
        }
        String newString = new String(arr);
        System.out.println(newString);
        return newString;
    }

    /**
     * returns true if the Array only has empty Strings
     * @param arr
     * @return
     */
    public static boolean checkEmptyArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns a String only contraining numeric digits of a String
     * @param value
     * @return
     */
    public static int getNumbersFromString(String value) {

        String s = value.chars().filter(c -> Character.isDigit(c))
                .mapToObj(i -> Character.valueOf((char) i)).map(Object::toString).collect(Collectors.joining());
        /* value.chars().filter(c -> Character.isDigit(c)).forEachOrdered(ch -> sb.append((char) ch));
        String s = sb.toString(); */
        if (s != "")
            return Integer.parseInt(s);
        else
            return -1;
    }
}
