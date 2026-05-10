package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Magazine extends Item {
    private String title;
    private int issueNumber;
    private String publisher;

    public Magazine(String itemId, Status status, String title, int issueNumber, String publisher) {
        super(itemId, status);
        this.title = title;
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
