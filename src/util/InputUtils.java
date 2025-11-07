package util;

import java.util.Locale;
import java.util.Scanner;

public class InputUtils {
    private final Scanner scanner;

    public InputUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptString(String label, boolean required) {
        while (true) {
            System.out.print(label + ": ");
            String line = scanner.nextLine();
            if (!required) return line == null ? "" : line.trim();
            if (line != null && !line.trim().isBlank()) return line.trim();
            System.out.println("Please enter a value.");
        }
    }

    public int promptInt(String label, int min, int max) {
        while (true) {
            System.out.print(label + " (" + min + "-" + max + "): ");
            String line = scanner.nextLine();
            try {
                int value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public double promptDouble(String label, double min, double max) {
        while (true) {
            System.out.print(label + " (" + min + "-" + max + "): ");
            String line = scanner.nextLine();
            try {
                double value = Double.parseDouble(line.trim());
                if (value < min || value > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public boolean promptBoolean(String label) {
        while (true) {
            System.out.print(label + " (y/n): ");
            String line = scanner.nextLine();
            if (line == null) continue;
            String v = line.trim().toLowerCase(Locale.ROOT);
            if (v.equals("y") || v.equals("yes")) return true;
            if (v.equals("n") || v.equals("no")) return false;
            System.out.println("Please answer with 'y' or 'n'.");
        }
    }
}


