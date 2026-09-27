import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static final String FILE_NAME = "library.txt";

    private static final ArrayList<Library> books = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        loadBooks();

        while (true) {

            System.out.println();
            System.out.println("================================");
            System.out.println("     LIBRARY MANAGEMENT SYSTEM");
            System.out.println("================================");

            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Save and Exit");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    addBook(scanner);
                    break;

                case 2:
                    viewBooks();
                    break;

                case 3:
                    issueBook(scanner);
                    break;

                case 4:
                    returnBook(scanner);
                    break;

                case 5:
                    searchBook(scanner);
                    break;

                case 6:
                    saveBooks();

                    System.out.println("Data saved successfully.");
                    System.out.println("Thank you for using the Library Management System.");

                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook(Scanner scanner) {

        System.out.print("Enter book name: ");
        String name = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        if (name.isBlank() || author.isBlank()) {

            System.out.println("Book name and author cannot be empty.");
            return;
        }

        Library book = new Library(name, author);

        books.add(book);

        saveBooks();

        System.out.println("Book added successfully.");
    }

    private static void viewBooks() {

        if (books.isEmpty()) {

            System.out.println("No books found.");

            return;
        }

        System.out.println();
        System.out.println("------------- BOOK LIST -------------");

        for (int i = 0; i < books.size(); i++) {

            System.out.println(
                    (i + 1) + ". " + books.get(i)
            );
        }
    }

    private static void issueBook(Scanner scanner) {

        if (books.isEmpty()) {

            System.out.println("No books available.");

            return;
        }

        viewBooks();

        System.out.print("Enter book number to issue: ");

        try {

            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index >= 0 && index < books.size()) {

                books.get(index).issueBook();

                saveBooks();

            } else {

                System.out.println("Invalid book number.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid number.");
        }
    }

    private static void returnBook(Scanner scanner) {

        if (books.isEmpty()) {

            System.out.println("No books available.");

            return;
        }

        viewBooks();

        System.out.print("Enter book number to return: ");

        try {

            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index >= 0 && index < books.size()) {

                books.get(index).returnBook();

                saveBooks();

            } else {

                System.out.println("Invalid book number.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid number.");
        }
    }

    private static void searchBook(Scanner scanner) {

        System.out.print("Enter book name to search: ");

        String search = scanner.nextLine().toLowerCase();

        boolean found = false;

        for (Library book : books) {

            if (book.getName().toLowerCase().contains(search)) {

                System.out.println(book);

                found = true;
            }
        }

        if (!found) {

            System.out.println("Book not found.");
        }
    }

    private static void saveBooks() {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(FILE_NAME)
                        )
        ) {

            for (Library book : books) {

                writer.write(
                        book.getName().replace("|", "/")
                );

                writer.write("|");

                writer.write(
                        book.getAuthor().replace("|", "/")
                );

                writer.write("|");

                writer.write(
                        String.valueOf(book.isIssued())
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving data: " + e.getMessage()
            );
        }
    }

    private static void loadBooks() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(FILE_NAME)
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 3) {

                    Library book =
                            new Library(data[0], data[1]);

                    if (Boolean.parseBoolean(data[2])) {

                        book.issueBookSilently();
                    }

                    books.add(book);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading data: " + e.getMessage()
            );
        }
    }
}
