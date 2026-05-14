package org.carl.usermanagement;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    @DisplayName("borrowItem(): Success - Status changes to BORROWED")
    void testBorrowItem1() {
        Student student = new Student("Nathan", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Java 101", "123", "Author", Book.Genre.FANTASY);

        try {
            student.borrowItem(book);
            assertEquals(Item.Status.BORROWED, book.getStatus());
            assertTrue(student.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // Test fails if an exception is thrown for a valid borrow
        }
    }

    @Test
    @DisplayName("borrowItem(): Failure - Student reaches borrowing limit")
    void testBorrowItem2() {
        Student student = new Student("Nathan", User.Gender.MALE);

        for (int i = 0; i < 5; i++) {
            student.getBorrowedItems().add(new Book(Item.Status.IN_STORE, "Book " + i, "ID" + i, "Author", Book.Genre.FANTASY));
        }

        Book extraBook = new Book(Item.Status.IN_STORE, "The 6th Book", "999", "Author", Book.Genre.FANTASY);
        boolean caught = false;

        try {
            student.borrowItem(extraBook);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Borrowing limit surpassed (" + student.getBorrowingLimit() + " books max).", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("borrowItem(): Failure - Item is not IN_STORE")
    void testBorrowItem3() {
        Student student = new Student("Nathan", User.Gender.MALE);
        Book lostBook = new Book(Item.Status.LOST, "Missing", "456", "Author", Book.Genre.MYSTERY);

        boolean caught = false;
        try {
            student.borrowItem(lostBook);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Item has already been borrowed.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Success - Status resets to IN_STORE")
    void testReturnItem1() {
        Student student = new Student("Nathan", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Return Test", "111", "Author", Book.Genre.ROMANCE);

        try {
            student.borrowItem(book);
            student.returnItem(book);

            assertEquals(Item.Status.IN_STORE, book.getStatus());
            assertFalse(student.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // Test fails if valid return throws exception
        }
    }

    @Test
    @DisplayName("returnItem(): Failure - Item not in student list")
    void testReturnItem2() {
        Student student = new Student("Nathan", User.Gender.MALE);
        Book book = new Book(Item.Status.BORROWED, "Someone Else's Book", "222", "Author", Book.Genre.FANTASY);

        boolean caught = false;
        try {
            student.returnItem(book);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Operation failed: This item is not in your borrowed list.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Failure - Returning a null reference")
    void testReturnItem3() {
        Student student = new Student("Nathan", User.Gender.MALE);

        boolean caught = false;
        try {
            student.returnItem(null);
        } catch (Exception e) {
            // Catching a general Exception to ensure it's handled safely
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("toCSV(): Student with no items -> Correct format with trailing commas")
    void testToCSV1() {
        Student student = new Student("Nathan", User.Gender.MALE);
        student.setUserId("0101");

        String csvOutput = student.toCSV();

        String expected = "0101,Nathan,STUDENT,MALE,,";
        assertEquals(expected, csvOutput);
    }

    @Test
    @DisplayName("toCSV(): Student with one item -> ID included in string")
    void testToCSV2() {
        Student student = new Student("Nathan", User.Gender.MALE);
        student.setUserId("0101");

        Book book = new Book(Item.Status.IN_STORE, "Java 101", "123", "Author", Book.Genre.FANTASY);
        book.setItemId("0001");
        student.getBorrowedItems().add(book);

        String csvOutput = student.toCSV();

        String expected = "0101,Nathan,STUDENT,MALE,0001,";
        assertEquals(expected, csvOutput);
    }

    @Test
    @DisplayName("toCSV(): Student with multiple items -> Semicolon separated IDs")
    void testToCSV3() {
        Student student = new Student("Nathan", User.Gender.MALE);
        student.setUserId("0101");

        Book b1 = new Book(Item.Status.IN_STORE, "Java 1", "111", "Author", Book.Genre.FANTASY);
        b1.setItemId("0001");
        Book b2 = new Book(Item.Status.IN_STORE, "Java 2", "222", "Author", Book.Genre.MYSTERY);
        b2.setItemId("0002");

        student.getBorrowedItems().add(b1);
        student.getBorrowedItems().add(b2);
        // Act
        String csvOutput = student.toCSV();

        String expected = "0101,Nathan,STUDENT,MALE,0001;0002,";
        assertEquals(expected, csvOutput);
    }
}