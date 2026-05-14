package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DVD extends Item {
    private String director;
    private int durationMinutes;

    public DVD(Status status, String title, String director, int durationMinutes) {
        super(status, title);
        this.director = director;
        this.durationMinutes = durationMinutes;
    }

    /**
     * converts information to CSV format
     * @return a string of information in CSV format
     */
    @Override
    public String toCSV() {
        return itemId + "," + status + ",DVD," + title + "," +
                director + "," + durationMinutes;
    }
}
