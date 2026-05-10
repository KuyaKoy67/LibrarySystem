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

    private static int nextId = 1;

    public Item(String itemId, Status status) {
        this.itemId = String.format("%04d", nextId++);
        this.status = status;
    }

    public enum Status {
        BORROWED, IN_STORE, LOST
    }
}
