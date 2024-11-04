package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBUltis {
    public static void changescene(ActionEvent event, String fxmlFile, String title) {
        Parent root;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource(fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public static void signUp(ActionEvent event, String id, String username, String password, String email) {
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
                changescene(event, "/View/Login.fxml", "Login!");
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

    public static void logIn(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;

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
                    if (retrievedPassword.equals(password)) {
                        if (username.equals("admin") && password.equals("admin123")) {
                            changescene(event, "/View/Admin.fxml", "Library Management");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Đăng Nhập");
                            alert.setContentText("Logging in...");
                            alert.show();
                            changescene(event, "/View/MainScene.fxml", "Home to Library");
                            alert.close();
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
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(formatter.parse(dateStr).getTime()); // Convert to java.sql.Date
    }

    public static void saveBookToDatabase(Book book) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn

        // Câu lệnh SQL không bao gồm book_id
        String sql = "INSERT INTO Books(isbn, title, author, publisher, published_date, language, category, description, cover_image_path, audio_path, count) " +
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
            pstmt.setInt(11, book.getCount());               // Số lượng sách

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
        public static ArrayList<Book> getBooksFromDatabase() {
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
                    String coverImagePath = rs.getString("cover_image_path");
                    String audioPath = rs.getString("audio_path");
                    int count = rs.getInt("count");

                    // Create and add the Book object to the list
                    Book newBook = new Book(isbn, title, author, category, description, language, publisher, publishedDate, coverImagePath, audioPath, count);
                    books.add(newBook);
                }

            } catch (SQLException e) {
                System.out.println("Error retrieving books from database: " + e.getMessage());
            }

            return books;
        }

}