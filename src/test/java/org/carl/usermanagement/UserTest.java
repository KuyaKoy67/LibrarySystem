package org.carl.usermanagement;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("getBorrowedIdsAsString(): Multiple items -> Semicolon separated")
    void testGetBorrowedIdsAsString1() {
        User student = new Student("Nathan", User.Gender.MALE);
        Book b1 = new Book(Item.Status.IN_STORE, "Java 1", "123", "Author A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Java 2", "456", "Author B", Book.Genre.FANTASY);

        b1.setItemId("0001");
        b2.setItemId("0002");

        student.getBorrowedItems().add(b1);
        student.getBorrowedItems().add(b2);

        assertEquals("0001;0002", student.getBorrowedIdsAsString());
    }

    @Test
    @DisplayName("getBorrowedIdsAsString(): No items -> Empty string")
    void testGetBorrowedIdsAsString2() {
        User student = new Student("Nathan", User.Gender.MALE);
        assertEquals("", student.getBorrowedIdsAsString());
    }
}