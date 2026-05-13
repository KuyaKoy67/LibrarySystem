package org.carl.other;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.usermanagement.User;

public class TypeConverter {

    /**
     * helper method that checks the gender of a person and converts its string to
     * User.Gender
     * @param gender the string gender
     * @return the User.Gender value
     */
    public static User.Gender convertGender(String gender) {
        if (gender == null) {
            return null;
        }

        return switch (gender.toUpperCase()) {
            case "MALE" -> User.Gender.MALE;
            case "FEMALE" -> User.Gender.FEMALE;
            default -> null;
        };
    }

    /**
     * helper method that checks the status of the item and converts the string to Item.Status
     * @param status the string status
     * @return the Item.Status value
     */
    public static Item.Status convertStatus(String status) {
        if (status == null) {
            return null;
        }

        return switch (status.toUpperCase()) {
            case "BORROWED" -> Item.Status.BORROWED;
            case "IN_STORE" -> Item.Status.IN_STORE;
            case "LOST" -> Item.Status.LOST;
            default -> null;
        };
    }

    /**
     * helper method that checks the genre of the item and converts the string to Book.Genre
     * @param genre the string genre
     * @return the Book.Genre value
     */
    public static Book.Genre convertGenre(String genre) {
        if (genre == null) {
            return null;
        }

        return switch (genre.toUpperCase()) {
            case "ROMANCE" -> Book.Genre.ROMANCE;
            case "FANTASY" -> Book.Genre.FANTASY;
            case "MYSTERY" -> Book.Genre.MYSTERY;
            case "SELF_HELP" -> Book.Genre.SELF_HELP;
            default -> null;
        };
    }
}
