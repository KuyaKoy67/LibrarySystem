package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public abstract class Item {
    protected String itemId;
    protected Status status;
    protected String title;

    private static int nextId = 1;

    public Item(String itemId, Status status, String title) {
        this.itemId = String.format("%04d", nextId++);
        this.status = status;
        this.title = title;
    }

    public enum Status {
        BORROWED, IN_STORE, LOST
    }
}
