package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.example.demo6.Classes.User;

public class AdminController {

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
    private TableColumn<User, String> tf_date;

    @FXML
    private TableView<User> tableView;

    private ObservableList<User> data;

    @FXML
    public void initialize() {
        tf_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tf_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        tf_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tf_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        tf_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tf_date.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tf_avatar.setCellValueFactory(new PropertyValueFactory<>("pathToProfilePicture"));


        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        String url = "jdbc:sqlite:database//LibraryMain";
        String query = "SELECT * FROM USERS";
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String dateString = rs.getString("date");
                Date parsedDate = inputFormatter.parse(dateString);
                data.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("avatar"),
                        outputFormatter.format(parsedDate)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            data.remove(selectedUser);
            // Implement the logic to delete the user from the database
            String url = "jdbc:sqlite:database//LibraryMain";
            String query = "DELETE FROM USERS WHERE id = " + selectedUser.getId();

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void loadDatabase() {
        data.clear();
        loadDataFromDatabase();
    }
}