package org.carl.other;

import org.carl.Library;
import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    @DisplayName("isValidISBN(): Success - 13-digit format")
    void testIsValidISBN1() {
        boolean valid13 = Validation.isValidISBN("9780123456789");

        assertTrue(valid13);
    }

    @Test
    @DisplayName("isValidISBN(): Failure - Incorrect length or characters")
    void testIsValidISBN2() {
        boolean tooShort = Validation.isValidISBN("123");
        boolean hasLetters = Validation.isValidISBN("12345abcde");
        boolean tooLong = Validation.isValidISBN("123456789012345");

        assertFalse(tooShort);
        assertFalse(hasLetters);
        assertFalse(tooLong);
    }

    @Test
    @DisplayName("isValidISBN(): Failure - Handles empty strings")
    void testIsValidISBN3() {
        assertFalse(Validation.isValidISBN(""));
    }

    @Test
    @DisplayName("isValidName(): Success - Standard names")
    void testIsValidName1() {
        assertTrue(Validation.isValidName("Carl Nathan"));
        assertTrue(Validation.isValidName("Jean-Luc"));
    }

    @Test
    @DisplayName("isValidName(): Failure - Numbers or symbols")
    void testIsValidName2() {
        assertTrue(Validation.isValidName("Carl123"));
        assertTrue(Validation.isValidName("Nathan@Home"));
    }

    @Test
    @DisplayName("isValidName(): Failure - Empty or null")
    void testIsValidName3() {
        assertFalse(Validation.isValidName(""));
        assertFalse(Validation.isValidName(null));
    }

    @Test
    @DisplayName("isValidIssueNumber(): Success - Positive integers")
    void testIsValidIssue1() {
        assertTrue(Validation.isValidIssueNumber(1));
        assertTrue(Validation.isValidIssueNumber(250));
    }

    @Test
    @DisplayName("isValidIssueNumber(): Failure - Zero or negative")
    void testIsValidIssue3() {
        assertFalse(Validation.isValidIssueNumber(0));
        assertFalse(Validation.isValidIssueNumber(-10));
    }

    @Test
    @DisplayName("isUniqueID(): Success - ID does not exist in the list")
    void testIsUniqueID1() {
        List<Item> items = new ArrayList<>();
        Book b1 = new Book(Item.Status.IN_STORE, "Java 1", "111", "Author", Book.Genre.FANTASY);
        b1.setItemId("B001");
        items.add(b1);

        boolean isUnique = Validation.isUniqueId("B999", items);

        assertTrue(isUnique, "B999 should be unique as it is not in the list");
    }

    @Test
    @DisplayName("isUniqueID(): Failure - ID is already present in the list")
    void testIsUniqueID2() {
        List<Item> items = new ArrayList<>();
        Book b1 = new Book(Item.Status.IN_STORE, "Java 1", "111", "Author", Book.Genre.FANTASY);
        b1.setItemId("B001");
        items.add(b1);

        boolean isUnique = Validation.isUniqueId("B001", items);

        assertFalse(isUnique, "B001 should not be unique");
    }

    @Test
    @DisplayName("isUniqueID(): Success - List is empty")
    void testIsUniqueID3() {
        List<Item> emptyList = new ArrayList<>();

        boolean isUnique = Validation.isUniqueId("ANY_ID", emptyList);

        assertTrue(isUnique);
    }
}