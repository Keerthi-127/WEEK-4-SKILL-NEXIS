public class Library {

    private String name;
    private String author;
    private boolean issued;

    public Library(String name, String author) {
        this.name = name;
        this.author = author;
        this.issued = false;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public void issueBook() {

        if (!issued) {
            issued = true;
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Book is already issued.");
        }
    }

    public void returnBook() {

        if (issued) {
            issued = false;
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book is already available.");
        }
    }

    public void issueBookSilently() {
        issued = true;
    }

    @Override
    public String toString() {

        String status;

        if (issued) {
            status = "Issued";
        } else {
            status = "Available";
        }

        return "Book: " + name
                + " | Author: " + author
                + " | Status: " + status;
    }
}
