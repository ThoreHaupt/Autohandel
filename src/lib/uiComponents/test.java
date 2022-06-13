package lib.uiComponents;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        int zahl = Integer.parseInt(s);
        System.out.println(zahl);
        scanner.close();
    }
}
