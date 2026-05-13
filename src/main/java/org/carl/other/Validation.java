package org.carl.other;

import org.carl.itemmanagement.Item;

import java.util.List;

public class Validation {
    /**
     * checks if the ISBN's length is valid
     * @param isbn the isbn value
     * @return the boolean value
     */
    public static boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}"); // Must be 13 digits
    }

    /**
     * checks if a name doesn't only have numbers or symbols
     * @param name the name
     * @return the boolean value
     */
    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            if (Character.isLetter(name.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if an issue number is positive
     * @param issueNumber the issue number
     * @return the boolean value
     */
    public static boolean isValidIssueNumber(int issueNumber) {
        return issueNumber > 0;
    }

    /**
     * this checks if the id already exists in your list to prevent duplicates
     * @param id the id
     * @param items the list of items
     * @return the boolean value
     */
    public static boolean isUniqueId(String id, List<Item> items) {
        if (id == null || items == null) return false;

        for (Item item : items) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                return false;
            }
        }
        return true;
    }
}
