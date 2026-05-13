package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.other.Validation;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Magazine extends Item {
    private String title;
    private int issueNumber;
    private String publisher;

    public Magazine(String itemId, Status status, String title, int issueNumber, String publisher) {
        super(itemId, status, title);
        this.issueNumber = Validation.isValidIssueNumber(issueNumber) ? issueNumber : -1;
        this.publisher = publisher;
    }
}
