package org.carl;

import lombok.*;
import org.carl.itemmanagement.Item;
import org.carl.usermanagement.User;

import java.util.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Library {
    private List<Item> items;
    private Map<String, User> users;

    public Library(List<Item> items, Map<String, User> users) {
        this.items = new ArrayList<>();
        this.users = new TreeMap<>();
    }

    /**
     * adds an item in the library
     * @param item the new item to be added
     * @return the boolean value which indicates if the operation is successful
     */
    public boolean addItem(Item item) {
        if (items.contains(item)) {
            return false;
        }

        items.add(item);
        item.setStatus(Item.Status.IN_STORE);
        return true;
    }

    /**
     * removes an item in the library
     * @param item the new item to be removed
     * @return the boolean value which indicates if the operation is successful
     */
    public boolean removeItem(Item item) {
        if (!items.contains(item)) {
            return false;
        }

        items.remove(item);
        item.setStatus(null);
        return true;
    }
}
