package org.carl.other;

import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.Item;
import org.carl.usermanagement.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeConverterTest {

    @Test
    @DisplayName("ConvertGender(): MALE || male -> User.Gender.MALE")
    void testConvertGender1() {
        assertEquals(User.Gender.MALE, TypeConverter.convertGender("MALE"));
        assertEquals(User.Gender.MALE, TypeConverter.convertGender("male"));
    }

    @Test
    @DisplayName("ConvertGender(): FEMALE || FeMaLe -> User.Gender.FEMALE")
    void testConvertGender2() {
        assertEquals(User.Gender.FEMALE, TypeConverter.convertGender("FEMALE"));
        assertEquals(User.Gender.FEMALE, TypeConverter.convertGender("FeMaLe"));
    }

    @Test
    @DisplayName("ConvertGender(): null -> null")
    void testConvertGender3() {
        assertNull(TypeConverter.convertGender("OTHER"));
        assertNull(TypeConverter.convertGender(null));
        assertNull(TypeConverter.convertGender(""));
    }

    @Test
    @DisplayName("ConvertStatus(): Valid strings -> Enums")
    void testConvertStatus1() {
        assertEquals(Item.Status.BORROWED, TypeConverter.convertStatus("BORROWED"));
        assertEquals(Item.Status.IN_STORE, TypeConverter.convertStatus("IN_STORE"));
        assertEquals(Item.Status.LOST, TypeConverter.convertStatus("LOST"));
    }

    @Test
    @DisplayName("ConvertStatus(): Case-insensitive -> Enums")
    void testConvertStatus2() {
        assertEquals(Item.Status.BORROWED, TypeConverter.convertStatus("borrowed"));
        assertEquals(Item.Status.IN_STORE, TypeConverter.convertStatus("In_StOrE"));
        assertEquals(Item.Status.LOST, TypeConverter.convertStatus("lOsT"));
    }

    @Test
    @DisplayName("ConvertStatus(): null/invalid -> null")
    void testConvertStatus3() {
        assertNull(TypeConverter.convertStatus("BROKEN"));
        assertNull(TypeConverter.convertStatus(null));
        assertNull(TypeConverter.convertStatus(""));
    }


    @Test
    @DisplayName("ConvertGenre(): Valid strings -> Enums")
    void testConvertGenre1() {
        assertEquals(Book.Genre.ROMANCE, TypeConverter.convertGenre("ROMANCE"));
        assertEquals(Book.Genre.FANTASY, TypeConverter.convertGenre("FANTASY"));
        assertEquals(Book.Genre.MYSTERY, TypeConverter.convertGenre("MYSTERY"));
        assertEquals(Book.Genre.SELF_HELP, TypeConverter.convertGenre("SELF_HELP"));
    }

    @Test
    @DisplayName("ConvertGenre(): Case-insensitive -> Enums")
    void testConvertGenre2() {
        assertEquals(Book.Genre.ROMANCE, TypeConverter.convertGenre("romance"));
        assertEquals(Book.Genre.FANTASY, TypeConverter.convertGenre("FaNtAsY"));
        assertEquals(Book.Genre.SELF_HELP, TypeConverter.convertGenre("Self_Help"));
    }

    @Test
    @DisplayName("ConvertGenre(): null/invalid -> null")
    void testConvertGenre3() {
        assertNull(TypeConverter.convertGenre("HORROR"));
        assertNull(TypeConverter.convertGenre(null));
        assertNull(TypeConverter.convertGenre(""));
    }
}