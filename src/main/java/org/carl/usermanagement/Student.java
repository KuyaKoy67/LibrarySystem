package org.carl.usermanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.itemmanagement.Item;
import org.carl.other.Constants;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Student extends User {
    private int borrowingLimit;

    public Student(String userId, String name, List<Item> borrowedItems, Gender gender) {
        super(userId, name, borrowedItems, gender);
        this.borrowingLimit = Constants.MAX_BOOKS_STUDENT;
    }
}
