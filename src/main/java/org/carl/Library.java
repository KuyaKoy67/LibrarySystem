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
}
