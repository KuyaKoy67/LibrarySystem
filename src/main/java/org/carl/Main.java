package org.carl;


import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.carl.usermanagement.User;

import java.util.List;
import java.util.Scanner;


public class Main {
    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        library.loadData();
        System.out.println("WELCOME TO THE LIBRARY MANAGEMENT SYSTEM\n");
        while (true) {
            System.out.println("Please select an option: \n" +
                    "1. Search | 2. Borrow | 3. Return | 4. Exit");
            String choice = scanner.nextLine();

            if (choice.equals("4")) break;

            switch (choice) {
                case "1" -> runSearch();
                case "2" -> runBorrow();
                case "3" -> runReturn();
            }
        }

        System.out.println("Thank you! Come again!");
    }

    /**
     * helper method that, when called, searches the item that is being looked for
     */
    private static void runSearch() {
        System.out.print("Keyword: ");
        String query = scanner.nextLine();
        List<Item> found = library.searchStream(query);

        if (found.isEmpty()) {
            System.out.println("Not found... try again?");
        }

        for (Item it : found) {
            System.out.println(it.getItemId() + ": " + it.getTitle());
        }
    }

    /**
     * helper method that, when called, borrows an item
     */
    private static void runBorrow() {
        User user = selectUser();
        Item item = selectItem();

        if (user != null && item != null) {
            try {
                user.borrowItem(item);
                System.out.println("Success!\n");
            } catch (LibraryException e) {
                System.out.println("Failed: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * helper method that, when called, returns an item
     */
    private static void runReturn() {
        User user = selectUser();
        if (user == null || user.getBorrowedItems().isEmpty()) return;

        Item toReturn = user.getBorrowedItems().get(0);
        try {
            user.returnItem(toReturn);
            System.out.println("Returned: " + toReturn.getTitle());
        } catch (LibraryException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Chooses a user
     * @return the selected user
     */
    private static User selectUser() {
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine().trim();
        for (User u : library.getUsers().values()) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        System.out.println("User '" + name + "' not found.");
        return null;
    }

    /**
     * Chooses an item
     * @return the item
     */
    private static Item selectItem() {
        System.out.print("Enter Item Title: ");
        String title = scanner.nextLine().trim();
        for (Item it : library.getItems()) {
            if (it.getTitle().equalsIgnoreCase(title)) {
                return it;
            }
        }
        System.out.println("Item '" + title + "' not found.");
        return null;
    }
}