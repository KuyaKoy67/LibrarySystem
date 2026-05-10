package org.carl.itemmanagement;

public abstract class Item {
    protected String itemId;
    protected Status status;

    private static int nextId = 1;
}
