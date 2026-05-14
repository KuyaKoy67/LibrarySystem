package org.carl.itemmanagement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.carl.other.Validation;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
public class Book extends Item {
    private String ISBN;
    private String author;
    private Genre genre;

    public Book(Status status, String title, String ISBN, String author, Genre genre) {
        super(status, title);
        this.ISBN = Validation.isValidISBN(ISBN) ? ISBN : null;
        this.author = author;
        this.genre = genre;
    }

    /**
     * converts information to CSV format
     * @return a string of information in CSV format
     */
    @Override
    public String toCSV() {
        return itemId + "," + status + ",BOOK," + ISBN + "," + title + "," +
                author + "," + genre;
    }

    public enum Genre {
        ROMANCE, FANTASY, MYSTERY, SELF_HELP
    }
}
