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
}
