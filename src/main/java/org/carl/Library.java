package org.carl;

import lombok.*;
import org.carl.itemmanagement.Book;
import org.carl.itemmanagement.DVD;
import org.carl.itemmanagement.Item;
import org.carl.itemmanagement.Magazine;
import org.carl.other.Constants;
import org.carl.other.LibraryException;
import org.carl.usermanagement.Admin;
import org.carl.usermanagement.Student;
import org.carl.usermanagement.Teacher;
import org.carl.usermanagement.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.carl.other.TypeConverter.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Library {
    private List<Item> items;
    private Map<String, User> users;

    public Library() {
        this.items = new ArrayList<>();
        this.users = new TreeMap<>();
    }

    /**
     * adds an item in the library
     * @param item the new item to be added
     */
    public void addItem(Item item) throws LibraryException {
        if (items.contains(item)) {
            throw new LibraryException("Item is already in library");
        }

        items.add(item);
        item.setStatus(Item.Status.IN_STORE);
        System.out.println("Item successfully added in library");
    }

    /**
     * removes an item in the library
     * @param item the new item to be removed
     */
    public void removeItem(Item item) throws LibraryException {
        if (!items.contains(item)) {
            throw new LibraryException("Item is not visible in the list");
        }

        items.remove(item);
        item.setStatus(null);
        System.out.println("Item successfully removed from library");
    }

    /**
     * This method searches an item using a query using recursion. It returns a list of items
     * that are being looked for
     * @param query the string query
     * @param index the int index
     * @param results the temporary results
     * @return the list of items looked for
     */
    public List<Item> searchRecursive(String query, int index, List<Item> results) {
        if (index >= this.items.size()) {
            return results;
        }

        Item current = this.items.get(index);
        String q = query.toLowerCase();

        boolean isMatching = current.getTitle().toLowerCase().contains(q);

        if (!isMatching && current instanceof Book) {
            isMatching = ((Book) current).getAuthor().toLowerCase().contains(q);
        } else if (!isMatching && current instanceof DVD) {
            isMatching = ((DVD) current).getDirector().toLowerCase().contains(q);
        }

        if (isMatching) {
            results.add(current);
        }

        return searchRecursive(query, index + 1, results);
    }

    /**
     * This method searches an item using a query using streams. It returns a list of items
     * that are being looked for
     * @param query the String query
     * @return the list of searched items
     */
    public List<Item> searchStream(String query) {
        if (query == null || query.isEmpty()) return new ArrayList<>();

        String q = query.toLowerCase().trim();

        return this.items.stream()
                .filter(item -> {
                    if (item.getTitle().toLowerCase().contains(q)) {
                        return true;
                    }

                    if (item instanceof Book) {
                        Book b = (Book) item;
                        return b.getAuthor().toLowerCase().contains(q) ||
                                b.getISBN().contains(q);
                    }

                    if (item instanceof Magazine) {
                        Magazine m = (Magazine) item;
                        return m.getPublisher().toLowerCase().contains(q);
                    }

                    if (item instanceof DVD) {
                        DVD d = (DVD) item;
                        return d.getDirector().toLowerCase().contains(q);
                    }

                    return false;
                })
                .distinct() // can we use distinct?
                .collect(Collectors.toList());
    }

    /**
     * loads the data coming from the items.csv and users.csv files
     */
    public void loadData() {
        File itemFile = new File(Constants.ITEMS_CSV_PATH);

        try (Scanner input = new Scanner(itemFile)) {
            while (input.hasNextLine()) {
                String row = input.nextLine();
                String[] infos = row.split(",");
                String id = infos[0];
                String statusSTR = infos[1];
                Item.Status status = convertStatus(statusSTR);

                String itemType = infos[2];

                String ISBN; String title; String author; String genre;
                int issueNumber; String publisher; String director; int durationMinutes;
                switch (itemType) {
                    case "BOOK" -> {
                        title = infos[3];
                        ISBN = infos[4];
                        author = infos[5];
                        genre = infos[6];
                        this.items.add(new Book(status, title, ISBN, author, convertGenre(genre)));
                    }
                    case "MAGAZINE" -> {
                        title = infos[3];
                        issueNumber = Integer.parseInt(infos[4]);
                        publisher = infos[5];
                        this.items.add(new Magazine(status, title, issueNumber, publisher));
                    }
                    case "DVD" -> {
                        title = infos[3];
                        director = infos[4];
                        durationMinutes = Integer.parseInt(infos[5]);
                        this.items.add(new DVD(status, title, director, durationMinutes));
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf(String.format("File %s does not exist", Constants.ITEMS_CSV_PATH));
        }

        File userFile = new File(Constants.USERS_CSV_PATH);

        try (Scanner input = new Scanner(userFile)) {
            while (input.hasNextLine()) {
                String row = input.nextLine();
                String[] infos = row.split(",");
                String userId = infos[0];
                String name = infos[1];
                String userType = infos[2];
                User.Gender gender = convertGender(infos[3]);
                String borrowedItemsIds = infos[4];

                User currentUser;
                switch (userType) {
                    case "STUDENT" -> currentUser = new Student(name, gender);
                    case "TEACHER" -> currentUser = new Teacher(name, gender);
                    case "ADMIN"   -> currentUser = new Admin(name, gender);
                    default        -> currentUser = null;
                }

                if (borrowedItemsIds != null && !borrowedItemsIds.isEmpty()) {
                    String[] idArray = borrowedItemsIds.split(";");

                    for (String itemId : idArray) {
                        Item foundItem = findItemById(itemId);

                        if (foundItem != null) {
                            currentUser.getBorrowedItems().add(foundItem);
                        }
                    }
                }

                this.users.put(userId, currentUser);
            }
        } catch (IOException e) {
            System.out.printf(String.format("File %s does not exist", Constants.USERS_CSV_PATH));
        }
    }

    /**
     * updates the files by adding all new information
     */
    public void backupData() {
        try {
            FileWriter itemWriter = new FileWriter(Constants.ITEMS_CSV_PATH);
            for (Item item : this.items) {
                itemWriter.write(item.toCSV() + "\n");
            }
            itemWriter.close();

            FileWriter userWriter = new FileWriter(Constants.USERS_CSV_PATH);
            for (User user : this.users.values()) {
                userWriter.write(user.toCSV() + "\n");
            }
            userWriter.close();

            System.out.println("Library data successfully backed up to CSV files.");

        } catch (IOException e) {
            System.out.println("Critical Error: Data backup failed. " + e.getMessage());
        }
    }

    /**
     * helper method that finds an item when it receives its id
     * @param id the id of an item
     * @return the item
     */
    private Item findItemById(String id) {
        for (Item item : this.items) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }
}
