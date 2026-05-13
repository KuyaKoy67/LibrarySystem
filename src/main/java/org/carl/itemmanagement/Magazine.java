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

    public Magazine(Status status, String title, int issueNumber, String publisher) {
        super(status, title);
        this.issueNumber = Validation.isValidIssueNumber(issueNumber) ? issueNumber : -1;
        this.publisher = publisher;
    }

    /**
     * converts information to CSV format
     * @return a string of information in CSV format
     */
    @Override
    public String toCSV() {
        return getItemId() + "," + getStatus() + ",MAGAZINE," + getTitle() + "," + issueNumber + "," + publisher;
    }
}
