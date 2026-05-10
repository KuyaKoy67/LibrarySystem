package org.carl.usermanagement;

import org.carl.itemmanagement.Item;

import java.util.List;

public abstract class User {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    private static int nextId = 1;
}
