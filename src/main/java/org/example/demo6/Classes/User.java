package org.example.demo6.Classes;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int id;
    private String password;
    private String email;
    private String profilePicture;
    private String role;
    private LocalDate dateOfBirth;
    private Streak streak;

    private List<Book> booksBorrowed;  // List of books the user has borrowed
    private List<Book> booksReturned;  // List of books the user has returned
    private List<Review> reviews;      // List of reviews written by the user

    private DBUltis dbUltis = new DBUltis();

    public User(int id, String username, String password, String email, LocalDate dateOfBirth ,String profilePicture, String role, Streak streak) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
        this.dateOfBirth = dateOfBirth;

        this.booksBorrowed = new ArrayList<>();
        this.booksReturned = new ArrayList<>();
        this.reviews = new ArrayList<>();

        this.streak = streak;
    }

    public User(int userId) {
        this.id = userId;
    }

    public User() {

    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPathToProfilePicture() { return profilePicture; }
    public void setPathToProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public List<Book> getBooksBorrowed() { return booksBorrowed; }
    public void addBookBorrowed(Book book) { booksBorrowed.add(book); }

    public List<Book> getBooksReturned() { return booksReturned; }
    public void addBookReturned(Book book) { booksReturned.add(book); }

    public List<Review> getReviews() { return reviews; }
    public void addReview(Review review) { reviews.add(review); }

    public int getIntStreak() {
        return streak.getStreak();
    }

    public LocalDate getLastLoginDate() {
        return streak.getLastAccess();
    }


    // mượn sách
    public void borrowBook(Book book) {
        boolean isInDataBase = dbUltis.findQuery("SELECT * FROM books WHERE isbn = '" + book.getIsbn() + "'");
        boolean isBorrowed = dbUltis.findQuery("SELECT * FROM BookTransaction WHERE book_id = '" + book.getIsbn() + "' AND user_id = " + this.getId() + " AND status = 'Borrowed'");
        if (book.getQuantity() <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book is out of stock");
            alert.setContentText("Sorry, this book is out of stock. Please come back later.");
            alert.showAndWait();
        } else if (!isInDataBase) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book not found");
            alert.setContentText("Sorry, this book is not in the database. Please contact the librarian.");
            alert.showAndWait();
        } else if (isBorrowed) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book already borrowed");
            alert.setContentText("Sorry, you have already borrowed this book. Please check again.");
            alert.showAndWait();
        } else {
            LocalDate dateBorrowed = LocalDate.now();
            LocalDate dueDate = dateBorrowed.plusDays(14);  // Mượn sách trong 14 ngày

            // Tạo một giao dịch mới
            Transaction transaction = new Transaction(this, book, dateBorrowed, dueDate);
            try {
                dbUltis.BorrowBook(transaction);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Book borrowed successfully");
                alert.setContentText("You have successfully borrowed the book " + book.getTitle() + ". Please return it within 14 days.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    // trả sách
    public void returnBook(Book Book) {
        // Tìm giao dịch mà người dùng đã mượn
        Transaction transaction = dbUltis.getTransaction(this, Book);
        if (transaction == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book not found");
            alert.setContentText("Sorry, you have not borrowed this book. Please check again.");
            alert.showAndWait();
        } else if (transaction.getStatus() == Transaction.status.Returned) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book already returned");
            alert.setContentText("Sorry, you have already returned this book. Please check again.");
            alert.showAndWait();
        } else {
            System.out.println(transaction.getId());
            transaction.returnBook(LocalDate.now());
            transaction.setStatus(Transaction.status.Returned);
            try {
                dbUltis.returnBook(transaction);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Book returned successfully");
                alert.setContentText("You have successfully returned the book " + Book.getTitle() + ". Thank you for using our library.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // tra sách
    public List<Book> searchBooksDatabase(String query) {
        return dbUltis.searchBook(query);
    }

    public List<Book> searchBooksApi(String query) throws IOException {
        return apiGoogleBooks.searchBooks1(query);
    }

    public Streak getStreak() {
        return streak;
    }

    public void setStreak(Streak streak) {
        this.streak = streak;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
