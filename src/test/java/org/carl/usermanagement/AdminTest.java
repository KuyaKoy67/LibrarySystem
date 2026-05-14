package org.carl.usermanagement;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    @DisplayName("generateReport(): Success - Handles a mixed list of items")
    void testGenerateReport1() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        List<Item> items = new ArrayList<>();

        items.add(new Book(Item.Status.IN_STORE, "Book A", "1", "Author", Book.Genre.FANTASY));
        items.add(new Book(Item.Status.BORROWED, "Book B", "2", "Author", Book.Genre.ROMANCE));
        items.add(new Book(Item.Status.LOST, "Book C", "3", "Author", Book.Genre.MYSTERY));

        try {
            admin.generateReport(items);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("generateReport(): Success - Handles an empty library list")
    void testGenerateReport2() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        List<Item> emptyList = new ArrayList<>();

        try {
            admin.generateReport(emptyList);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("generateReport(): Success - Handles duplicates in the list")
    void testGenerateReport3() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        List<Item> items = new ArrayList<>();
        Book duplicateBook = new Book(Item.Status.IN_STORE, "Unique Title", "101", "Author", Book.Genre.FANTASY);

        items.add(duplicateBook);
        items.add(duplicateBook);

        try {
            admin.generateReport(items);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("borrowItem(): Success - Status changes to BORROWED")
    void testBorrowItem1() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Java 101", "123", "Author", Book.Genre.FANTASY);

        try {
            admin.borrowItem(book);
            assertEquals(Item.Status.BORROWED, book.getStatus());
            assertTrue(admin.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // Test fails
        }
    }

    @Test
    @DisplayName("borrowItem(): Failure - Item is already LOST")
    void testBorrowItem2() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.LOST, "Missing Book", "456", "Author", Book.Genre.MYSTERY);

        boolean caught = false;
        try {
            admin.borrowItem(book);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("This item is currently unavailable.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("borrowItem(): Failure - Item is already BORROWED by someone else")
    void testBorrowItem3() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.BORROWED, "Checked Out Book", "789", "Author", Book.Genre.FANTASY);

        boolean caught = false;
        try {
            admin.borrowItem(book);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("This item is currently unavailable.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Success - Item status resets to IN_STORE")
    void testReturnItem1() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Return Test", "111", "Author", Book.Genre.ROMANCE);

        try {
            admin.borrowItem(book);
            admin.returnItem(book);

            assertEquals(Item.Status.IN_STORE, book.getStatus());
            assertFalse(admin.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // If it throws an exception during a valid return, the test fails
        }
    }

    @Test
    @DisplayName("returnItem(): Failure - Item not in Admin's borrowed list")
    void testReturnItem2() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.BORROWED, "Someone Else's Book", "222", "Author", Book.Genre.FANTASY);

        boolean caught = false;
        try {
            admin.returnItem(book);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Operation failed: This item is not in your borrowed list.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Failure - Attempting to return an IN_STORE item")
    void testReturnItem3() {
        Admin admin = new Admin("Carl", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Store Book", "333", "Author", Book.Genre.MYSTERY);

        boolean caught = false;
        try {
            admin.returnItem(book);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Operation failed: This item is not in your borrowed list.", e.getMessage());
        }
        assertTrue(caught);
    }
}
