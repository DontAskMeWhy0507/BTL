package org.example.demo6.Classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.demo6.HelloApplication;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.demo6.Controller.GeneralController.changescene;

public class DBUltis implements Database{

    public boolean signUp(String id, String username, String password, String email) {
        String url = "jdbc:sqlite:database/LibraryMain";

        try (Connection conn = DriverManager.getConnection(url)) {
            // check if the username already exists
            if (findQuery("SELECT * FROM Users WHERE username = '" + username + "'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists");
                alert.show();
                return false;
            }
            // check if the email already exists
            if (findQuery("SELECT * FROM Users WHERE email = '" + email + "'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Email already exists");
                alert.show();
                return false;
            }
            // check if the id already exists
            if (findQuery("SELECT * FROM Users WHERE id = '" + id + "'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ID already exists");
                alert.show();
                return false;
            }

            String sql = "INSERT INTO Users(id, username, password, email, date_of_birth, avatar, role, last_access, streak, longest_streak) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, LocalDate.now().toString());
            pstmt.setString(6, "/Image/Avatar/Gekko.png");
            pstmt.setString(7, "User");
            pstmt.setString(8, LocalDate.now().toString());
            pstmt.setInt(9, 0);
            pstmt.setInt(10, 0);

            pstmt.executeUpdate();
            System.out.println("User signed up successfully.");

        } catch (SQLException e) {
            System.out.println("Error signing up: " + e.getMessage());
        }
        return true;

    }


    public User logIn(String username, String password) {
        Connection connection = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;
        User loggedInUser = null;  // Initialize the User object to return

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database//LibraryMain");

            psCheckUserExist = connection.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?");
            psCheckUserExist.setString(1, username);
            psCheckUserExist.setString(2, password);
            rs = psCheckUserExist.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User does not exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your username or password is incorrect");
                alert.show();
            } else {
                while (rs.next()) {
                    String retrievedPassword = rs.getString("password");
                    String role = rs.getString("role");
                    if (retrievedPassword.equals(password)) {
                        // Check the role of the user
                        if (role.equals("Admin")) {
                            loggedInUser = new Admin(rs.getInt("ID"),
                                    rs.getString("USERNAME"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("EMAIL"),
                                    LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                                    rs.getString("AVATAR"),
                                    rs.getString("ROLE"),
                                    new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK")));
                        } else if (role.equals("User")) {
                            loggedInUser = new User(rs.getInt("ID"),
                                    rs.getString("USERNAME"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("EMAIL"),
                                    LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                                    rs.getString("AVATAR"),
                                    rs.getString("ROLE"),
                                    new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"),rs.getInt("LONGEST_STREAK")));
                        }
                    } else {
                        System.out.println("Password is incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Your username or password is incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try {
                    psCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return loggedInUser;  // Return the logged in user (Admin or User)
    }

    public void saveBookToDatabase(Book book) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn

        // Câu lệnh SQL không bao gồm book_id
        String sql = "INSERT INTO Books(isbn, title, author, publisher, published_date, language, category, description, cover_image_path, audio_path,quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());              // Mã ISBN
            pstmt.setString(2, book.getTitle());             // Tên sách
            pstmt.setString(3, book.getAuthor());            // Tác giả
            pstmt.setString(4, book.getPublisher());         // Nhà xuất bản
            pstmt.setString(5, book.getPublishedDate().toString());
            pstmt.setString(6, book.getLanguage());          // Ngôn ngữ
            pstmt.setString(7, book.getCategory());          // Thể loại
            pstmt.setString(8, book.getDescription());       // Mô tả
            pstmt.setString(9, book.getCoverImagePath());    // Đường dẫn đến ảnh bìa
            pstmt.setString(10, book.getAudioPath());        // Đường dẫn đến file audio
            pstmt.setInt(11, book.getQuantity());            // Số lượng sách

            pstmt.executeUpdate();
            System.out.println("Book saved to database.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        }
    }

        // Method to fetch all books from the database
    public ArrayList<Book> getBooksFromDatabase() {
        String url = "jdbc:sqlite:database/LibraryMain"; // Adjust the path to your SQLite file
        String sql = "SELECT * FROM Books"; // SQL query to fetch all books

        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        LocalDate.parse(rs.getString("published_date")),
                        rs.getString("book_path"),
                        rs.getString("cover_image_path"),
                        rs.getString("audio_path"),
                        rs.getInt("quantity"));
                // Create and add the Book object to the list
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    public void updateUserInDatabase(User user) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn

        // Câu lệnh SQL không bao gồm book_id
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, " +
                "avatar = ?, last_access = ?, date_of_birth = ?, streak = ?, longest_streak = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());  // Tên người dùng
            pstmt.setString(2, user.getPassword());  // Mật khẩu
            pstmt.setString(3, user.getEmail());     // Email
            pstmt.setString(4, user.getPathToProfilePicture());    // Đường dẫn đến ảnh đại diện
            pstmt.setString(5, user.getStreak().getLastAccess().toString()); // Last access
            pstmt.setString(6, user.getDateOfBirth().toString()); // Ngày sinh
            pstmt.setInt(7, user.getStreak().getStreak()); // Streak
            pstmt.setInt(8, user.getStreak().getLongestStreak()); // Longest streak
            pstmt.setInt(9, user.getId()); // ID người dùng

            pstmt.executeUpdate();
           // System.out.println("User updated in database.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        }
    }

    public List<Book> searchBook (String search) {
        List<Book> books = new ArrayList<>();
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR language LIKE ? OR publisher LIKE ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + search + "%");
            pstmt.setString(2, "%" + search + "%");
            pstmt.setString(3, "%" + search + "%");
            pstmt.setString(4, "%" + search + "%");
            pstmt.setString(5, "%" + search + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        LocalDate.parse(rs.getString("published_date")),
                        rs.getString("book_path"),
                        rs.getString("cover_image_path"),
                        rs.getString("audio_path"),
                        rs.getInt("quantity"));
                // Create and add the Book object to the list
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    public void BorrowBook(Transaction transaction) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Path to your SQLite database file

        // SQL statements
        String sqlInsert = "INSERT INTO BookTransaction(user_id, book_id, date_borrowed, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Books SET quantity = quantity - 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            // Set parameters for the INSERT statement
            pstmtInsert.setInt(1, transaction.getUser().getId()); // Assuming `User` has a method `getId()`
            pstmtInsert.setString(2, transaction.getBook().getIsbn()); // Assuming `Book` has a method `getId()`
            pstmtInsert.setString(3, transaction.getDateBorrowed().toString()); // Convert LocalDate to String
            pstmtInsert.setString(4, transaction.getDueDate().toString()); // Convert LocalDate to String
            pstmtInsert.setString(5, transaction.getStatus().name()); // Enum status as String

            // Execute the INSERT statement
            pstmtInsert.executeUpdate();

            // Set parameters for the UPDATE statement
            pstmtUpdate.setString(1, transaction.getBook().getIsbn()); // Assuming `Book` has a method `getIsbn()`

            // Execute the UPDATE statement
            pstmtUpdate.executeUpdate();

            System.out.println("Transaction saved to database and book quantity updated.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    public Transaction getTransaction(User user, Book book) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = "SELECT * FROM BookTransaction WHERE user_id = ? AND book_id = ? AND status = 'Borrowed'";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            pstmt.setString(2, book.getIsbn());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LocalDate dateReturned = null;
                String dateReturnedStr = rs.getString("date_returned");
                if (dateReturnedStr != null) {
                    dateReturned = LocalDate.parse(dateReturnedStr);
                }
                return new Transaction(rs.getInt("id"),
                        user,
                        book,
                        LocalDate.parse(rs.getString("date_borrowed")),
                        LocalDate.parse(rs.getString("due_date")),
                        dateReturned,
                        Transaction.status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transaction from database: " + e.getMessage());
        }

        return null;
    }

    public void returnBook(Transaction transaction) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Path to your SQLite file

        // SQL statements
        String sqlUpdateTransaction = "UPDATE BookTransaction SET date_returned = ?, status = ? WHERE id = ?";
        String sqlUpdateBook = "UPDATE Books SET quantity = quantity + 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmtUpdateTransaction = conn.prepareStatement(sqlUpdateTransaction);
             PreparedStatement pstmtUpdateBook = conn.prepareStatement(sqlUpdateBook)) {

            // Update the transaction
            pstmtUpdateTransaction.setString(1, transaction.getDateReturned().toString());
            pstmtUpdateTransaction.setString(2, transaction.getStatus().name());
            pstmtUpdateTransaction.setInt(3, transaction.getId());
            pstmtUpdateTransaction.executeUpdate();

            // Update the book quantity
            pstmtUpdateBook.setString(1, transaction.getBook().getIsbn());
            pstmtUpdateBook.executeUpdate();

            System.out.println("Transaction updated in database and book quantity updated.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        }
    }

    public boolean findQuery(String query) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = query;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return false;
    }

    public void loadQuery(String query) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = query;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = "SELECT * FROM Users";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        LocalDate.parse(rs.getString("date_of_birth")),
                        rs.getString("avatar"),
                        rs.getString("role"),
                        new Streak(LocalDate.parse(rs.getString("last_access")), rs.getInt("streak"), rs.getInt("longest_streak")));
                // Create and add the User object to the list
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users from database: " + e.getMessage());
        }

        return users;
    }

    public void saveReviewToDatabase(Review review, Book currentBook) {
        String url = "jdbc:sqlite:database/LibraryMain";
        String selectQuery = "SELECT id FROM Reviews WHERE userId = ? AND isbn = ?";
        String insertQuery = "INSERT INTO Reviews (comment, rating, userId, isbn) VALUES (?, ?, ?, ?)";
        String updateQuery = "UPDATE Reviews SET comment = ?, rating = ? WHERE userId = ? AND isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Check if a review by the same user for the same book already exists
            selectStmt.setInt(1, review.getUser().getId());
            selectStmt.setString(2, currentBook.getIsbn());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Update the existing review
                updateStmt.setString(1, review.getComment());
                updateStmt.setInt(2, review.getRating());
                updateStmt.setInt(3, review.getUser().getId());
                updateStmt.setString(4, currentBook.getIsbn());
                updateStmt.executeUpdate();
            } else {
                // Insert a new review
                insertStmt.setString(1, review.getComment());
                insertStmt.setInt(2, review.getRating());
                insertStmt.setInt(3, review.getUser().getId());
                insertStmt.setString(4, currentBook.getIsbn());
                insertStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewsForBook(Book book) {
        List<Review> reviews = new ArrayList<>();
        String url = "jdbc:sqlite:database/LibraryMain";
        String query = "SELECT * FROM Reviews WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getIsbn());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = getUserById(rs.getInt("userId")); // Assuming you have a method to get User by ID
                Review review = new Review(
                        rs.getString("comment"),
                        rs.getInt("rating"),
                        user,
                        book
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    private User getUserById(int userId) {
        String url = "jdbc:sqlite:database/LibraryMain";
        String query = "SELECT * FROM Users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        LocalDate.parse(rs.getString("date_of_birth")),
                        rs.getString("avatar"),
                        rs.getString("role"),
                        new Streak(LocalDate.parse(rs.getString("last_access")), rs.getInt("streak"), rs.getInt("longest_streak"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getAverageRatingForBook(Book book) {
        String url = "jdbc:sqlite:database/LibraryMain";
        String query = "SELECT AVG(rating) AS avg_rating FROM Reviews WHERE isbn = ?";
        double avgRating = 0.0;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getIsbn());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgRating;
    }

    public static List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comments WHERE post_id = ? ORDER BY timestamp ASC";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/LibraryMain");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, postId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getObject("reply_to_comment_id", Integer.class), // Có thể null
                        rs.getString("isbn"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    public static List<Comment> getRepliesByCommentId(int commentId) {
        List<Comment> replies = new ArrayList<>();
        String query = "SELECT * FROM Comments WHERE reply_to_comment_id = ? ORDER BY timestamp ASC";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/LibraryMain");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, commentId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                replies.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getObject("reply_to_comment_id", Integer.class),
                        rs.getString("isbn"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return replies;
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM Reviews";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:your_database.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String comment = rs.getString("comment");
                int rating = rs.getInt("rating");
                int userId = rs.getInt("userId");
                String isbn = rs.getString("isbn");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                Review review = new Review(comment, rating, userId, isbn, timestamp);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}