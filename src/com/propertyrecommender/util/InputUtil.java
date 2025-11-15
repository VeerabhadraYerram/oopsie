package com.propertyrecommender.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt, int defaultValue) {
        try {
            String s = readLine(prompt);
            if (s.isEmpty()) return defaultValue;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer. Using default " + defaultValue);
            return defaultValue;
        }
    }

    public static double readDouble(String prompt, double defaultValue) {
        try {
            String s = readLine(prompt);
            if (s.isEmpty()) return defaultValue;
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Using default " + defaultValue);
            return defaultValue;
        }
    }
}
