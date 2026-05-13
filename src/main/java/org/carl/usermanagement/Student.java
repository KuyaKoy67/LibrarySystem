package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;
import org.carl.other.LibraryException;


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
    public void borrowItem(Item item) throws LibraryException {
        if (item.getStatus() != Item.Status.IN_STORE) {
            throw new LibraryException("Item has already been borrowed.");
        }

        if (this.borrowedItems.size() >= this.borrowingLimit) {
            throw new LibraryException("Borrowing limit surpassed (" + borrowingLimit + " books max).");
        }

        if (!(item instanceof Book)) {
            throw new LibraryException("Students are only allowed to borrow Books.");
        }

        item.setStatus(Item.Status.BORROWED);
        borrowedItems.add(item);
    }

    /**
     * returns an item of a student. This method checks if the item is borrowed already
     * and if the item to be returned is in the student's list of borrowed items
     * @param item the item to be returned
     */
    @Override
    public void returnItem(Item item) throws LibraryException {
        if (!this.borrowedItems.contains(item)) {
            throw new LibraryException("Operation failed: This item is not in your borrowed list.");
        }

        item.setStatus(Item.Status.IN_STORE);
        borrowedItems.remove(item);
    }

    @Override
    public String toCSV() {
        return userId + "," + name + ",STUDENT," + gender + "," +
                getBorrowedIdsAsString();
    }
}
