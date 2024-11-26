package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.example.demo6.Classes.Admin;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.Streak;
import org.example.demo6.Classes.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController {
    Library library = Library.getInstance();

    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());
    private static final String DATABASE_URL = "jdbc:sqlite:database//LibraryMain";

    @FXML
    private TableColumn<User, Integer> tf_ID;

    @FXML
    private TableColumn<User, String> tf_name;

    @FXML
    private TableColumn<User, String> tf_password;

    @FXML
    private TableColumn<User, String> tf_role;

    @FXML
    private TableColumn<User, String> tf_email;

    @FXML
    private TableColumn<User, String> tf_avatar;

    @FXML
    private TableColumn<User, LocalDate> tf_date;

    // New columns for Streak and Last Access Date
    @FXML
    private TableColumn<User, Integer> tf_streak;

    @FXML
    private TableColumn<User, LocalDate> tf_lastLoginDate;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TextField tf_findName;

    private ObservableList<User> data;

    @FXML
    public void initialize() {
        setupTableColumns();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        loadDataFromDatabase();
    }

    // thêm cột
    private void setupTableColumns() {
        // Existing columns
        tf_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tf_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        tf_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tf_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        tf_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tf_date.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tf_avatar.setCellValueFactory(new PropertyValueFactory<>("pathToProfilePicture"));

        // New columns for Streak and Last Access Date
        tf_streak.setCellValueFactory(new PropertyValueFactory<>("IntStreak"));
        tf_lastLoginDate.setCellValueFactory(new PropertyValueFactory<>("LastLoginDate"));
    }

    // thêm data từ database
    private void loadDataFromDatabase() {
        String query = "SELECT * FROM USERS";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // New fields for Streak and Last Access Date
                User user = new User(rs.getInt("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                        rs.getString("AVATAR"),
                        rs.getString("ROLE"),
                        new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK")));

                // Add the new user to the data list
                data.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading data from database", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    // thêm người dùng
    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/AddUser.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error opening AddUser.fxml", e);
        }
    }

    // xóa người dùng
    @FXML
    private void handleDeleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText("User: " + selectedUser.getUsername());
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                if (library.getCurrentUser() instanceof Admin) {
                    Admin admin = (Admin) library.getCurrentUser();
                    admin.removeUser(selectedUser);
                    data.remove(selectedUser);

                } else {
                    System.err.println("Current user is not an admin.");
                }
            } else {
                alert.close();
            }
        }
    }

    // tải database
    @FXML
    public void loadDatabase() {
        data.clear();
        loadDataFromDatabase();
    }

    // tra theo tên
    @FXML
    public void searchName() {
        data.clear();
        String query = "SELECT * FROM USERS WHERE USERNAME LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + tf_findName.getText() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                        rs.getString("AVATAR"),
                        rs.getString("ROLE"),
                        new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK")));
                data.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching for user in database", e);
        }
    }

    public void entertoSeachName() {
        tf_findName.setOnAction(e -> searchName());
    }

    @FXML
    public void updateUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/AddUser.fxml"));
                Parent parent = loader.load();

                // Get the controller and set the selected user
                AddUser controller = loader.getController();
                controller.setUser(selectedUser);

                Stage stage = new Stage();
                stage.setTitle("Edit User");
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                // Refresh the table view after editing
                loadDataFromDatabase();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error opening AddUser.fxml", e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select a user in the table.");
            alert.showAndWait();
        }
    }
}
