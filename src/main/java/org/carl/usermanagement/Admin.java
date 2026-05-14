package org.carl.usermanagement;

import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.carl.other.Reportable;

import java.util.List;

public class Admin extends User implements Reportable {

    public Admin(String name, Gender gender) {
        super(name, gender);
    }

    /**
     * generates a report (exclusively for admins) that shows every item and its status
     * @param items the list of items that will be checked
     */
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

    /**
     * borrows an item as an admin.
     * @param item the item to be borrowed
     */
    @Override
    public void borrowItem(Item item) throws LibraryException {
        if (item.getStatus() != Item.Status.IN_STORE) {
            throw new LibraryException("This item is currently unavailable.");
        }

        item.setStatus(Item.Status.BORROWED);
        borrowedItems.add(item);
    }

    /**
     * returned an item as an admin.
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
        return userId + "," + name + ",ADMIN," + gender + "," +
                getBorrowedIdsAsString() + ",";
    }
}
