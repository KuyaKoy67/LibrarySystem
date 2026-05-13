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
    private String title;
    private String director;
    private int durationMinutes;

    public DVD(String itemId, Status status, String title, String director, int durationMinutes) {
        super(itemId, status, title);
        this.director = director;
        this.durationMinutes = durationMinutes;
    }
}
