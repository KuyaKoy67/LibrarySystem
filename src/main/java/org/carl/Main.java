package org.carl;


import org.carl.other.LibraryException;


public class Main {
    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        library.loadData();

        while (true) {
            System.out.println("\n" + "WELCOME TO THE LIBRARY MANAGEMENT SYSTEM\n"
            + "Please select an option: \n" +
                    "1. Search | 2. Borrow | 3. Return | 4. Exit");
            String choice = scanner.nextLine();

            if (choice.equals("4")) break;

            switch (choice) {
                case "1" -> runSearch();
                case "2" -> runBorrow();
                case "3" -> runReturn();
            }
        }
    }
    
    private static void runSearch() {
        System.out.print("Keyword: ");
        String query = scanner.nextLine();
        List<Item> found = library.searchStream(query);

        for (Item it : found) {
            System.out.println(it.getItemId() + ": " + it.getTitle());
        }
    }

    private static void runBorrow() {
        User user = selectUser();
        Item item = selectItem();

        if (user != null && item != null) {
            try {
                user.borrowItem(item);
                System.out.println("Success!");
            } catch (LibraryException e) {
                System.out.println("Failed: " + e.getMessage());
            }
        }
    }

    private static void runReturn() {
        User user = selectUser();
        if (user == null || user.getBorrowedItems().isEmpty()) return;

        Item toReturn = user.getBorrowedItems().get(0);
        try {
            user.returnItem(toReturn);
            System.out.println("Returned: " + toReturn.getTitle());
        } catch (LibraryException e) {
            System.out.println(e.getMessage());
        }
    }

    private static User selectUser() {
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();
        return library.getUsers().get(name); // Assumes keys are names in your Map
    }

    private static Item selectItem() {
        System.out.print("Enter Item Title: ");
        String title = scanner.nextLine();
        return library.getItems().stream()
                .filter(i -> i.getTitle().equalsIgnoreCase(title))
                .findFirst().orElse(null);
    }
}