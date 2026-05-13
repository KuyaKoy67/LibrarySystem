package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;
import org.carl.other.LibraryException;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    private int borrowingLimit;

    public Teacher(String name, Gender gender) {
        super(name, gender);
        this.borrowingLimit = Constants.MAX_ITEMS_TEACHER;
    }

    /**
     * borrows an item as a teacher.
     * @param item the item to be borrowed
     */
    @Override
    public void borrowItem(Item item) throws LibraryException {
        if (item.getStatus() != Item.Status.IN_STORE) {
            throw new LibraryException("This item is currently unavailable.");
        }

        if (this.borrowedItems.size() >= this.borrowingLimit) {
            throw new LibraryException("Teacher borrowing limit reached.");
        }

        item.setStatus(Item.Status.BORROWED);
        borrowedItems.add(item);
    }

    /**
     * returns an item as a teacher.
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

    /**
     * converts information to CSV format
     * @return a string of information in CSV format
     */
    @Override
    public String toCSV() {
        return userId + "," + name + ",TEACHER," + gender + "," +
                getBorrowedIdsAsString();
    }
}
