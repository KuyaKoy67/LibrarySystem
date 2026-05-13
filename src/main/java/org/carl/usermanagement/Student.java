package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Student extends User {
    private int borrowingLimit;

    public Student(String name, Gender gender) {
        super(name, gender);
        this.borrowingLimit = Constants.MAX_BOOKS_STUDENT;
    }

    /**
     * method that borrows an item for a student. It has two conditions: it must
     * be unique (different ids) and the item must be a book
     * @param item the item to be borrowed
     * @return the boolean value that shows the
     */
    @Override
    public boolean borrowItem(Item item) {
        if (item.getStatus() == Item.Status.IN_STORE && borrowedItems.size() > this.borrowingLimit) {
            if (this.borrowedItems.contains(item) && !(item instanceof Book)) {
                System.out.println("Item cannot be borrowed. Check if it is already in " +
                        "your student's list, or if it is a book");
                return false;
            }
            item.setStatus(Item.Status.BORROWED);
            borrowedItems.add(item);
            return true;

        } else {
            System.out.println("Item has already been borrowed");
            return false;
        }
    }

    /**
     * returns an item of a student. This method checks if the item is borrowed already
     * and if the item to be returned is in the student's list of borrowed items
     * @param item the item to be returned
     * @return the boolean value to indicate if the operation was successful
     */
    @Override
    public boolean returnItem(Item item) {
        if (item.getStatus() == Item.Status.BORROWED) {
            if (!this.borrowedItems.contains(item)) {
                System.out.println("Item cannot be returned. It is not in the student's" +
                        "list");
                return false;
            }
            item.setStatus(Item.Status.IN_STORE);
            borrowedItems.remove(item);
            return true;

        } else {
            System.out.println("Item is not borrowed.");
            return false;
        }
    }

    @Override
    public String toCSV() {
        return userId + "," + name + ",STUDENT," + gender + "," +
                getBorrowedIdsAsString();
    }
}
