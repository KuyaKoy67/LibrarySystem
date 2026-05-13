package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.carl.other.Validation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public abstract class User {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    private static int nextId = 1;

    public User(String name, Gender gender) {
        this.userId = String.format("%04d", nextId++);
        this.name = Validation.isValidName(name) ? name : null;
        this.borrowedItems = new ArrayList<>();
        this.gender = gender;
    }

    public static class ById implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            return u1.getUserId().compareTo(u2.getUserId());
        }
    }

    public static class ByName implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            return u1.getName().compareToIgnoreCase(u2.getName());
        }
    }

    public abstract void borrowItem(Item item) throws LibraryException;

    public abstract void returnItem(Item item) throws LibraryException;

    public abstract String toCSV();

    /**
     * helper method converts the borrowed items ids as a string
     * @return the converted string
     */
    public String getBorrowedIdsAsString() {
        if (borrowedItems == null || borrowedItems.isEmpty()) return "";
        String ids = "";
        for (int i = 0; i < borrowedItems.size(); i++) {
            ids += borrowedItems.get(i).getItemId();
            if (i < borrowedItems.size() - 1) ids += ";";
        }
        return ids;
    }

    public enum Gender {
        MALE, FEMALE
    }

}
