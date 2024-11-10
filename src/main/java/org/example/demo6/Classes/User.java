package org.example.demo6.Classes;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int id;
    private String password;
    private String email;
    private String dateOfBirth;
    private String profilePicture;
    private String role;

    private List <Book> booksBorrowed;  // Danh sách các sách mà người dùng đã mượn trong All
    private List <Book> booksReturned;  // Danh sách các sách mà người dùng đã trả trong finished
    private List <Review> reviews;      // Danh sách các đánh giá của người dùng
 
    public User(int id, String username, String password, String email, String dateOfBirth, String profilePicture, String role) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePicture = profilePicture;
        this.role = role;
        this.booksBorrowed = new ArrayList<>();
        this.booksReturned  = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }


    public User(String username, int id, String password, String email, String pathToProfilePicture) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.profilePicture = pathToProfilePicture;
        this.booksBorrowed = new ArrayList<>();
        this.booksReturned  = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

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

    // books
    

    public void account() {
        // change password
        // change email
        // change username
        // change profile picture
    }

    public void settings () {
        //  view history books borrowed, books returned, comments, ratings.
        //  change preferences
    }

}
