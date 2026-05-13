package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Validation;

import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public abstract class User {
    protected String userId;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    private static int nextId = 1;

    public User(String userId, String name, List<Item> borrowedItems, Gender gender) {
        this.userId = String.format("%04d", nextId++);
        this.name = Validation.isValidName(name) ? name : null;
        this.borrowedItems = borrowedItems;
        this.gender = gender;
    }

    public enum Gender {
        MALE, FEMALE
    }

    public abstract boolean borrowItem(Item item);

    public abstract boolean returnItem(Item item);

    public abstract Item searchItem(String query);



}
