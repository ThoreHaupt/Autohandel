package lib.Other;

import java.util.ArrayList;
import java.util.List;

public class StringTools {
    public static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c))
                return false;
        }
        return true;
    }

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

    public static boolean checkEmptyArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals("")) {
                return false;
            }
        }
        return true;
    }
}
