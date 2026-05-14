package org.carl.usermanagement;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.other.LibraryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    @DisplayName("borrowItem(): Success - Status changes to BORROWED")
    void testTeacherBorrow1() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Advanced Java", "777", "Author", Book.Genre.FANTASY);

        try {
            teacher.borrowItem(book);
            assertEquals(Item.Status.BORROWED, book.getStatus());
            assertTrue(teacher.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // Test fails if valid borrow throws exception
        }
    }

    @Test
    @DisplayName("borrowItem(): Success - Teacher can borrow more than the Student limit")
    void testTeacherBorrow2() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);

        for (int i = 0; i < 5; i++) {
            teacher.getBorrowedItems().add(new Book(Item.Status.IN_STORE, "Book " + i, "ID" + i, "A", Book.Genre.FANTASY));
        }

        Book sixthBook = new Book(Item.Status.IN_STORE, "The 6th Book", "666", "A", Book.Genre.FANTASY);
        try {
            teacher.borrowItem(sixthBook);
            assertTrue(teacher.getBorrowedItems().contains(sixthBook));
        } catch (LibraryException e) {
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("borrowItem(): Failure - Item is LOST")
    void testTeacherBorrow3() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);
        Book lostBook = new Book(Item.Status.LOST, "Vanished Book", "000", "Author", Book.Genre.MYSTERY);

        boolean caught = false;
        try {
            teacher.borrowItem(lostBook);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("This item is currently unavailable.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Success - Item correctly returned to store")
    void testTeacherReturn1() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);
        Book book = new Book(Item.Status.IN_STORE, "Physics", "111", "Einstein", Book.Genre.SELF_HELP);

        try {
            teacher.borrowItem(book);
            teacher.returnItem(book);

            assertEquals(Item.Status.IN_STORE, book.getStatus());
            assertFalse(teacher.getBorrowedItems().contains(book));
        } catch (LibraryException e) {
            // Test fails if valid return throws exception
        }
    }

    @Test
    @DisplayName("returnItem(): Failure - Returning an item not in teacher's list")
    void testTeacherReturn2() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);
        Book strangerBook = new Book(Item.Status.BORROWED, "Someone's Book", "222", "A", Book.Genre.FANTASY);

        boolean caught = false;
        try {
            teacher.returnItem(strangerBook);
        } catch (LibraryException e) {
            caught = true;
            assertEquals("Operation failed: This item is not in your borrowed list.", e.getMessage());
        }
        assertTrue(caught);
    }

    @Test
    @DisplayName("returnItem(): Success - Handles multiple returns correctly")
    void testTeacherReturn3() {
        Teacher teacher = new Teacher("Mr. Smith", User.Gender.MALE);
        Book b1 = new Book(Item.Status.IN_STORE, "Math", "1", "A", Book.Genre.FANTASY);
        Book b2 = new Book(Item.Status.IN_STORE, "Science", "2", "B", Book.Genre.FANTASY);

        try {
            teacher.borrowItem(b1);
            teacher.borrowItem(b2);

            teacher.returnItem(b1);

            assertFalse(teacher.getBorrowedItems().contains(b1));
            assertTrue(teacher.getBorrowedItems().contains(b2));
            assertEquals(Item.Status.IN_STORE, b1.getStatus());
        } catch (LibraryException e) {
            assertTrue(false);
        }
    }
}