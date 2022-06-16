package lib.technicalComponents;

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
            l.add((char) (Math.random() * 100 + 70));
        }
        char[] arr = new char[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = l.get(i);
        }
        return new String(arr);
    }
}
