package com.pbo.fareru.minpro.pbo.controller;

import java.util.Scanner;

/**

 * @author Muhammad Fahriel (2509116050)
 */
public class InputValidator {

    public static String bacaString(Scanner scanner, String prompt) {
        return bacaString(scanner, prompt, false);
    }

    public static String bacaString(Scanner scanner, String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input == null) {
                input = "";
            }
            String trimmed = input.trim();
            if (allowEmpty || !trimmed.isEmpty()) {
                return trimmed;
            }
            System.out.println("[Error] Input tidak boleh kosong atau hanya berisi spasi!");
        }
    }

    public static int bacaInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Input harus berupa angka bulat!");
            }
        }
    }

    public static int bacaInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int nilai = bacaInt(scanner, prompt);
            if (nilai >= min && nilai <= max) {
                return nilai;
            }
            System.out.printf("[Error] Nilai harus berada dalam rentang %d sampai %d!%n", min, max);
        }
    }

    public static double bacaDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Input harus berupa bilangan angka!");
            }
        }
    }

    public static double bacaDouble(Scanner scanner, String prompt, double min) {
        while (true) {
            double nilai = bacaDouble(scanner, prompt);
            if (nilai >= min) {
                return nilai;
            }
            System.out.printf("[Error] Nilai minimal adalah %,.2f!%n", min);
        }
    }

    public static boolean bacaKonfirmasi(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("ya")) {
                return true;
            }
            if (input.equals("n") || input.equals("tidak")) {
                return false;
            }
            System.out.println("[Error] Ketik 'y' untuk ya atau 'n' untuk tidak!");
        }
    }

    public static void tekanEnter(Scanner scanner) {
        System.out.print("\nTekan ENTER untuk melanjutkan...");
        scanner.nextLine();
    }
}
