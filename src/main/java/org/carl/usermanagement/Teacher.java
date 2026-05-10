package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    private int borrowingLimit;

    public Teacher(String userId, String name, List<Item> borrowedItems, Gender gender) {
        super(userId, name, borrowedItems, gender);
        this.borrowingLimit = Constants.MAX_ITEMS_TEACHER;
    }
}
