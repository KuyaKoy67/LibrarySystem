package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Student extends User {
    private int borrowingLimit;

    public Student(String userId, String name, List<Item> borrowedItems, Gender gender) {
        super(userId, name, borrowedItems, gender);
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
        if (item.getStatus() == Item.Status.IN_STORE) {
            if (this.borrowedItems.contains(item) && !(item instanceof Book)) {
                System.out.println("Item cannot be borrowed. Check if it is already in " +
                        "your student's list, or if it is a book");
                return false;
            }
            borrowedItems.add(item);
            return true;
        } else {
            System.out.println("Item has already been borrowed");
            return false;
        }
    }
}
