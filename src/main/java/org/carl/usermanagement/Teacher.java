package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    private int borrowingLimit;

    public Teacher(String userId, String name, List<Item> borrowedItems, Gender gender) {
        super(userId, name, borrowedItems, gender);
        this.borrowingLimit = Constants.MAX_ITEMS_TEACHER;
    }

    /**
     * borrows an item as a teacher.
     * @param item the item to be borrowed
     * @return the boolean value that determines if the item has been successfully
     * borrowed
     */
    @Override
    public boolean borrowItem(Item item) {
        if (this.borrowedItems.contains(item) && item.getStatus() == Item.Status.IN_STORE) {
            if (this.borrowingLimit <= this.borrowedItems.size()) {
                System.out.println("This item has already been borrowed, or the limit has been surpassed");
                return false;
            }
        } else {
            item.setStatus(Item.Status.BORROWED);
            borrowedItems.add(item);
        }
        return true;
    }

    /**
     * returns an item as a teacher.
     * @param item the item to be returned
     * @return the boolean value that determines if the item has been successfully
     * returned
     */
    @Override
    public boolean returnItem(Item item) {
        if (!this.borrowedItems.contains(item) && item.getStatus() == Item.Status.BORROWED) {
            System.out.println("This item was not borrowed");
            return false;
        }

        item.setStatus(Item.Status.IN_STORE);
        borrowedItems.remove(item);
        return true;
    }
}
