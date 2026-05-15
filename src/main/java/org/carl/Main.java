package org.carl;


import org.carl.other.LibraryException;


public class Main {
    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        library.loadData();

        while (true) {
            System.out.println("\n" + "WELCOME TO THE LIBRARY MANAGEMENT SYSTEM\n"
            + "Please select an option: \n" +
                    "1. Search | 2. Borrow | 3. Return | 4. Exit");
            String choice = scanner.nextLine();

            if (choice.equals("4")) break;

            switch (choice) {
                case "1" -> runSearch();
                case "2" -> runBorrow();
                case "3" -> runReturn();
            }
        }
    }

        /*
        TESTING
        FINALIZING PNG
        REPORT
         */
    }
}