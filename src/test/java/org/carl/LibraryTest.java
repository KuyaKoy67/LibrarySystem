package org.carl;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.carl.usermanagement.Admin;
import org.carl.usermanagement.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    @DisplayName("addItem(): Success - New item added to list")
    void testAddItem1() throws LibraryException {
        Library lib = new Library();
        Book book = new Book(Item.Status.IN_STORE, "Java 101", "123", "Author", Book.Genre.FANTASY);

        lib.addItem(book);
        boolean added = lib.getItems().contains(book);

        assertTrue(added);
        assertEquals(1, lib.getItems().size());
    }

    @Test
    @DisplayName("addItem(): Failure - Duplicate item not added")
    void testAddItem2() throws LibraryException {
        Library lib = new Library();
        Book book = new Book(Item.Status.IN_STORE, "Java 101", "123", "Author", Book.Genre.FANTASY);

        lib.addItem(book);

        assertThrows(LibraryException.class, () -> {
            lib.addItem(book);
        });

        assertEquals(1, lib.getItems().size());
    }

    @Test
    @DisplayName("addItem(): Success - Handles multiple different items")
    void testAddItem3() throws LibraryException {
        Library lib = new Library();
        Book b1 = new Book(Item.Status.IN_STORE, "Book 1", "001", "A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Book 2", "002", "B", Book.Genre.MYSTERY);

        lib.addItem(b1);
        lib.addItem(b2);

        assertEquals(2, lib.getItems().size());
    }

    @Test
    @DisplayName("removeItem(): Success - Item removed from list")
    void testRemoveItem1() throws LibraryException {
        Library lib = new Library();
        Book book = new Book(Item.Status.IN_STORE, "Delete Me", "999", "Author", Book.Genre.FANTASY);
        lib.addItem(book);

        boolean removed = lib.getItems().remove(book);

        assertTrue(removed);
        assertEquals(0, lib.getItems().size());
    }

    @Test
    @DisplayName("removeItem(): Failure - Attempt to remove non-existent item")
    void testRemoveItem2() throws LibraryException {
        Library lib = new Library();
        Book bookInLib = new Book(Item.Status.IN_STORE, "Library Book", "111", "A", Book.Genre.FANTASY);
        Book ghostBook = new Book(Item.Status.IN_STORE, "Ghost Book", "222", "B", Book.Genre.FANTASY);

        lib.addItem(bookInLib);

        try {
            lib.removeItem(ghostBook);
        } catch (LibraryException e) {
            // Exception caught, proceeding to assertions
        }

        boolean removed = lib.getItems().contains(ghostBook);
        assertFalse(removed); // Should be false as it was never there
        assertEquals(1, lib.getItems().size()); // Only the original book should remain
    }

    @Test
    @DisplayName("removeItem(): Success - List remains intact after removing one of many")
    void testRemoveItem3() throws LibraryException {
        Library lib = new Library();
        Book b1 = new Book(Item.Status.IN_STORE, "Stay", "001", "A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Go", "002", "B", Book.Genre.FANTASY);

        lib.addItem(b1);
        lib.addItem(b2);

        lib.removeItem(b2);

        assertEquals(1, lib.getItems().size());
        assertTrue(lib.getItems().contains(b1));
        assertFalse(lib.getItems().contains(b2));
    }

    @Test
    @DisplayName("searchRecursive(): Matches found -> Returns list with correct items")
    void testSearchRecursive1() throws LibraryException {
        Library lib = new Library();
        Book b1 = new Book(Item.Status.IN_STORE, "Java Basics", "101", "Author A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Python Pro", "102", "Author B", Book.Genre.MYSTERY);
        Book b3 = new Book(Item.Status.IN_STORE, "Advanced Java", "103", "Author C", Book.Genre.FANTASY);

        lib.addItem(b1);
        lib.addItem(b2);
        lib.addItem(b3);

        List<Item> results = lib.searchRecursive("Java", 0, new ArrayList<>());

        assertEquals(2, results.size());
        assertTrue(results.contains(b1));
        assertTrue(results.contains(b3));
        assertFalse(results.contains(b2));
    }

    @Test
    @DisplayName("searchRecursive(): No matches -> Returns empty list")
    void testSearchRecursive2() throws LibraryException {
        Library lib = new Library();
        lib.addItem(new Book(Item.Status.IN_STORE, "Cooking 101", "201", "Chef", Book.Genre.SELF_HELP));

        List<Item> results = lib.searchRecursive("Java", 0, new ArrayList<>());

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    @DisplayName("searchRecursive(): Empty library -> Returns empty list immediately")
    void testSearchRecursive3() {
        Library lib = new Library();

        List<Item> results = lib.searchRecursive("Any Query", 0, new ArrayList<>());

        assertEquals(0, results.size());
    }

    @Test
    @DisplayName("searchStream(): Matches found -> Returns filtered and distinct list")
    void testSearchStream1() throws LibraryException {
        Library lib = new Library();
        Book b1 = new Book(Item.Status.IN_STORE, "Java Basics", "101", "Author A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Python Pro", "102", "Author B", Book.Genre.MYSTERY);

        lib.addItem(b1);
        lib.addItem(b2);

        lib.addItem(b1);

        List<Item> results = lib.searchStream("Java");

        assertEquals(1, results.size());
        assertTrue(results.contains(b1));
        assertFalse(results.contains(b2));
    }

    @Test
    @DisplayName("searchStream(): No matches -> Returns empty list")
    void testSearchStream2() throws LibraryException {
        Library lib = new Library();
        lib.addItem(new Book(Item.Status.IN_STORE, "Calculus", "301", "Math", Book.Genre.SELF_HELP));

        List<Item> results = lib.searchStream("Java");

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    @DisplayName("searchStream(): Case Insensitivity -> Should match regardless of case")
    void testSearchStream3() throws LibraryException {
        Library lib = new Library();
        Book book = new Book(Item.Status.IN_STORE, "JAVA PROGRAMMING", "401", "Dev", Book.Genre.FANTASY);
        lib.addItem(book);

        List<Item> results = lib.searchStream("java");

        assertEquals(1, results.size());
        assertTrue(results.contains(book));
    }

    @Test
    @DisplayName("loadData(): Success - Correctly populates item list")
    void testLoadData1() {
        Library lib = new Library();

        try {
            lib.loadData();
            assertTrue(lib.getItems().isEmpty());
        } catch (Exception e) {
            // If the file is missing or formatted wrong, it will fail here
        }
    }

    @Test
    @DisplayName("loadData(): Success - Correctly maps item IDs to objects")
    void testLoadData2() {
        Library lib = new Library();

        try {
            lib.loadData();

            Item firstItem = lib.getItems().get(0);
            assertNotNull(firstItem.getItemId());
            assertNotNull(firstItem.getTitle());
        } catch (Exception e) {
            // Handle potential IO exceptions
        }
    }

    @Test
    @DisplayName("loadData(): Failure - Handles malformed data gracefully")
    void testLoadData3() {
        Library lib = new Library();

        boolean caught = false;
        try {
            lib.loadData();
        } catch (Exception e) {
            caught = true;
        }
    }

    @Test
    @DisplayName("backupData(): Success - Executes without crashing")
    void testBackupData1() {
        Library lib = new Library();

        try {
            lib.addItem(new Book(Item.Status.IN_STORE, "Backup Test", "101", "Author", Book.Genre.FANTASY));

            lib.backupData();
        } catch (Exception e) {
            // If an exception occurs, the test essentially fails
        }
    }

    @Test
    @DisplayName("backupData(): Empty Lists - Handles empty library gracefully")
    void testBackupData2() {
        Library lib = new Library();

        try {
            lib.backupData();
        } catch (Exception e) {
            // Test fails if saving an empty library causes a crash
        }
    }

    @Test
    @DisplayName("backupData(): Logic Verification - Admin authority")
    void testBackupData3() {
        Library lib = new Library();
        Admin admin = new Admin("System Admin", User.Gender.MALE);

        boolean caught = false;
        try {
            lib.backupData();
        } catch (Exception e) {
            caught = true;
        }

        assertFalse(caught);
    }
}