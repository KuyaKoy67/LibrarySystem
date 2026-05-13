package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public abstract class Item {
    protected String itemId;
    protected Status status;
    protected String title;

    private static int nextId = 1;

    public Item(Status status, String title) {
        this.itemId = String.format("%04d", nextId++);
        this.status = status;
        this.title = title;
    }

    public static class ById implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return i1.getItemId().compareTo(i2.getItemId());
        }
    }

    public static class ByTitle implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return i1.getTitle().compareToIgnoreCase(i2.getTitle());
        }
    }

    public enum Status {
        BORROWED, IN_STORE, LOST
    }

    public abstract String toCSV();
}
