package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
public class Book extends Item {
    private String ISBN;
    private String title;
    private String author;
    private Genre genre;

    public Book(String itemId, Status status, String ISBN, String title, String author, Genre genre) {
        super(itemId, status, title);
        this.ISBN = ISBN;
        this.author = author;
        this.genre = genre;
    }

    public enum Genre {
        ROMANCE, FANTASY, MYSTERY, SELF_HELP
    }
}
