package lib.technicalComponents;

public class StringTools {
    public static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c))
                return false;
        }
        return true;
    }
}
