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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.demo6.Controller.GeneralController.changescene;

public class DBUltis implements Database{

    public void signUp(ActionEvent event, String id, String username, String password, String email) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        PreparedStatement psCheckIDExist = null;
        PreparedStatement psCheckEmailExist = null;
        ResultSet rsUsername = null;
        ResultSet rsID = null;
        ResultSet rsEmail = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database//LibraryMain");

            psCheckUserExist = connection.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            psCheckUserExist.setString(1, username);
            rsUsername = psCheckUserExist.executeQuery();

            psCheckIDExist = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            psCheckIDExist.setString(1, id);
            rsID = psCheckIDExist.executeQuery();

            psCheckEmailExist = connection.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
            psCheckEmailExist.setString(1, email);
            rsEmail = psCheckEmailExist.executeQuery();
            if (rsUsername.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User already exists");
                alert.show();
            } else if (rsID.isBeforeFirst()) {
                System.out.println("ID already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this ID");
                alert.show();
            } else if (rsEmail.isBeforeFirst()) {
                System.out.println("Email already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this email");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO USERS (ID, USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, id);
                psInsert.setString(2, username);
                psInsert.setString(3, password);
                psInsert.setString(4, email);
                psInsert.executeUpdate();
                System.out.println("User created");
                changescene(event, "/View/LoginScene/Login.fxml", "Login!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rsID != null) {
                try {
                    rsID.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rsUsername != null) {
                try {
                    rsUsername.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckIDExist != null) {
                try {
                    psCheckIDExist.close();
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
            if (psInsert != null) {
                try {
                    psInsert.close();
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
    }

    public User logIn(ActionEvent event, String username, String password) {
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
                            // Get this user from database and return Admin object
                            Admin admin = new Admin(rs.getString("USERNAME"),
                                    rs.getInt("ID"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("email"),
                                    rs.getString("AVATAR"));
                            loggedInUser = admin;  // Assign the Admin object to loggedInUser
                        } else if (role.equals("User")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Login");
                            alert.setContentText("Logging in as User...");
                            alert.show();
                            // Change to the user scene
                            changescene(event, "/View/UserScene/MainSceneUser.fxml", "User Home");
                            alert.close();

                            // You could initialize the User object similarly to Admin if needed
                            loggedInUser = new User(rs.getString("USERNAME"),
                                    rs.getInt("ID"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("email"),
                                    rs.getString("AVATAR"));
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


    public Date stringToDate(String dateStr) throws ParseException {
        // Check the length of the date string to determine the format
        SimpleDateFormat formatter;

        if (dateStr.matches("\\d{4}")) { // Only year, format: yyyy
            formatter = new SimpleDateFormat("yyyy");
            return new Date(formatter.parse(dateStr).getTime());

        } else if (dateStr.matches("\\d{4}-\\d{2}")) { // Year and month, format: yyyy-MM
            formatter = new SimpleDateFormat("yyyy-MM");
            return new Date(formatter.parse(dateStr).getTime());

        } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) { // Full date, format: yyyy-MM-dd
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            return new Date(formatter.parse(dateStr).getTime());

        } else {
            throw new ParseException("Unrecognized date format: " + dateStr, 0);
        }
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

            // Convert and set the published date
            Date publishedDate = stringToDate(book.getPublishedDate());
            pstmt.setDate(5, publishedDate);                // Ngày xuất bản

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
        } catch (ParseException e) {
            System.out.println("Date parsing error: " + e.getMessage());
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
                // Extract data from each row in the ResultSet
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String publishedDate = rs.getString("published_date"); // stored as String
                String language = rs.getString("language");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");

                String bookPath = rs.getString("book_path");
                String coverImagePath = rs.getString("cover_image_path");
                String audioPath = rs.getString("audio_path");


                // Create and add the Book object to the list
                Book book = new Book(isbn, title, author, publisher, publishedDate, language, category, description,bookPath,coverImagePath, audioPath,quantity);
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
        String sql = "UPDATE USERS SET username = ?, password = ?, email = ?, avatar = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());              // Mã ISBN
            pstmt.setString(2, user.getPassword());             // Tên sách
            pstmt.setString(3, user.getEmail());            // Tác giả
            pstmt.setString(4, user.getPathToProfilePicture());         // Nhà xuất bản
            pstmt.setInt(5, user.getId());                // Ngày xuất bản

            pstmt.executeUpdate();
            System.out.println("User updated in database.");
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
                // Extract data from each row in the ResultSet
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String publishedDate = rs.getString("published_date"); // stored as String
                String language = rs.getString("language");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");

                String bookPath = rs.getString("book_path");
                String coverImagePath = rs.getString("cover_image_path");
                String audioPath = rs.getString("audio_path");

                // Create and add the Book object to the list
                Book book = new Book(isbn, title, author, publisher, publishedDate, language, category, description,bookPath,coverImagePath, audioPath,quantity);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    public void BorrowBook(Transaction transaction) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn

        // Câu lệnh SQL không bao gồm book_id
        String sqlInsert = "INSERT INTO BookTransaction(user_id, book_id, date_borrowed, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Books SET quantity = quantity - 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            // Insert the transaction
            pstmtInsert.setInt(1, transaction.getUser().getId());              // Mã người dùng
            pstmtInsert.setString(2, transaction.getBook().getIsbn());         // Mã sách
            pstmtInsert.setDate(3, Date.valueOf(transaction.getDateBorrowed().toLocalDate()));  // Ngày mượn
            pstmtInsert.setDate(4, Date.valueOf(transaction.getDueDate().toLocalDate()));       // Hạn trả
            pstmtInsert.setString(5, transaction.getStatus().name());
            pstmtInsert.executeUpdate();

            // Update the book quantity
            pstmtUpdate.setString(1, transaction.getBook().getIsbn());
            pstmtUpdate.executeUpdate();

            System.out.println("Transaction saved to database and book quantity updated.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        }
    }

    public Transaction getTransaction(User user, Book book) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = "SELECT * FROM BookTransaction WHERE user_id = ? AND book_id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            pstmt.setString(2, book.getIsbn());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Extract data from the ResultSet
                int id = rs.getInt("id");
                LocalDateTime dateBorrowed = rs.getTimestamp("date_borrowed").toLocalDateTime();
                LocalDateTime dueDate = rs.getTimestamp("due_date").toLocalDateTime();
                LocalDateTime dateReturned = rs.getTimestamp("date_returned") != null ? rs.getTimestamp("date_returned").toLocalDateTime() : null;
                Transaction.status status = Transaction.status.valueOf(rs.getString("status"));

                // Create and return the Transaction object
                return new Transaction(id, user, book, dateBorrowed, dueDate, dateReturned, status);
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
            pstmtUpdateTransaction.setTimestamp(1, Timestamp.valueOf(transaction.getDateReturned()));  // Return date
            pstmtUpdateTransaction.setString(2, transaction.getStatus().name());  // Status
            pstmtUpdateTransaction.setInt(3, transaction.getId());  // Transaction ID
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
}