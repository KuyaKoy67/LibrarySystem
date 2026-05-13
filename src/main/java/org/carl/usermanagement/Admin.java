package org.carl.usermanagement;

import org.carl.itemmanagement.Item;
import org.carl.other.Reportable;

import java.util.List;

public class Admin extends User implements Reportable {

    public Admin(String userId, String name, Gender gender) {
        super(userId, name, gender);
    }

    @Override
    public void generateReport(List<Item> items) {
        System.out.println("LIBRARY STATUS REPORT\n");

        System.out.println("BORROWED ITEMS:");
        List<Item> borrowedItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.BORROWED)
                .distinct()
                .toList();
        System.out.println(borrowedItems);

        System.out.println("\nIN STORE ITEMS:");
        List<Item> inStoreItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.IN_STORE)
                .distinct()
                .toList();
        System.out.println(inStoreItems);

        System.out.println("\nLOST ITEMS:");
        List<Item> lostItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.LOST)
                .distinct()
                .toList();
        System.out.println(lostItems);
    }

    @Override
    public boolean borrowItem(Item item) {
        if (item.getStatus() == Item.Status.IN_STORE) {
            if (this.borrowedItems.contains(item)) {
                System.out.println("Item cannot be borrowed. Check if it is already in " +
                        "the admin's list");
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

    @Override
    public boolean returnItem(Item item) {
        if (item.getStatus() == Item.Status.BORROWED) {
            if (!this.borrowedItems.contains(item)) {
                System.out.println("Item cannot be returned. It is not in the admin's" +
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
        return userId + "," + name + ",ADMIN," + gender + "," +
                getBorrowedIdsAsString() + ",,";
    }
}
