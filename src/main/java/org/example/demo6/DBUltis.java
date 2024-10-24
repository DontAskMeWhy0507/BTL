package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.sql.*;

public class DBUltis {
    public static void saveBookToDatabase(Book book) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn

        String sql = "INSERT INTO books(title, author, category, description, language, publisher, published_date, cover_image_path)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getCategory());
            pstmt.setString(4, book.getDescription());
            pstmt.setString(5, book.getLanguage());
            pstmt.setString(6, book.getPublisher());
            pstmt.setString(7, book.getPublishedDate());
            pstmt.setString(8, book.getCoverImagePath());

            pstmt.executeUpdate();
            System.out.println("Book saved to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
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
            }
            else {
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
                            changescene(event, "/View/MemberTable.fxml", "Member Table");
                        } else {
                            changescene(event, "/View/MainScene.fxml", "Home to Library");
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
}
