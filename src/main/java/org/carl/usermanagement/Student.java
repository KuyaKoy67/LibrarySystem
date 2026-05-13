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
     * borrows item as a student
     * @param item the item to be borrowed
     * @throws LibraryException the library exception
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
     * returns the item as a student
     * @param item the item to be returned
     * @throws LibraryException the library exception
     */
    @Override
    public void returnItem(Item item) throws LibraryException {
        if (!this.borrowedItems.contains(item)) {
            throw new LibraryException("Operation failed: This item is not in your borrowed list.");
        }

        item.setStatus(Item.Status.IN_STORE);
        borrowedItems.remove(item);
    }

    /**
     * converts information to CSV format
     * @return a string of information in CSV format
     */
    @Override
    public String toCSV() {
        return userId + "," + name + ",STUDENT," + gender + "," +
                getBorrowedIdsAsString();
    }
}
