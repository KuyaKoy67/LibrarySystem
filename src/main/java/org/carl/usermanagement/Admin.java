package org.carl.usermanagement;

import org.carl.other.Reportable;

public class Admin implements Reportable {

    @Override
    public void generateReport(List<Item> items) {
        System.out.println("LIBRARY STATUS REPORT\n");

        System.out.println("BORROWED ITEMS:");
        List<Item> borrowedItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.BORROWED)
                .toList();
        System.out.println(borrowedItems);

        System.out.println("\nIN STORE ITEMS:");
        List<Item> inStoreItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.IN_STORE)
                .toList();
        System.out.println(inStoreItems);

        System.out.println("\nLOST ITEMS:");
        List<Item> lostItems = items.stream().
                filter((item) -> item.getStatus() == Item.Status.LOST)
                .toList();
        System.out.println(lostItems);
    }
}
