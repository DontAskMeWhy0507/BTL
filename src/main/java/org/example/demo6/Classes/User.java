package org.example.demo6.Classes;
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

    // Constructor for initializing the User object
    public User(int id, String username, String password, String email, LocalDate dateOfBirth ,String profilePicture, String role, Streak streak) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
        this.dateOfBirth = dateOfBirth;

        // Initialize lists to avoid NullPointerException
        this.booksBorrowed = new ArrayList<>();
        this.booksReturned = new ArrayList<>();
        this.reviews = new ArrayList<>();

        // Initialize streak (can be null if not passed)
        this.streak = streak;
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

    // books
    

    public void borrowBook(Book book) {
        booksBorrowed.add(book);
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
