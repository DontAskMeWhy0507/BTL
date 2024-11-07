package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
}