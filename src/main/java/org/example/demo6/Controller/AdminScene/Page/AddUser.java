package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.demo6.Classes.DBUltis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class AddUser {
    DBUltis dbUltis = new DBUltis();

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Label sucessLabel;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_username;

    @FXML
    private CheckBox showPass;

    @FXML
    private TextField showPassword;

    @FXML
    void getPassword(ActionEvent event) {
        if (showPass.isSelected()) {
            showPassword.setText(tf_password.getText());
            tf_password.setVisible(false);
            showPassword.setVisible(true);
        } else {
            showPassword.setText(tf_password.getText());
            tf_password.setText(showPassword.getText());
            tf_password.setVisible(true);
            showPassword.setVisible(false);
        }
    }

    @FXML
    public void initialize() {
        choiceRole.setItems(FXCollections.observableArrayList("Admin", "User"));
    }

    @FXML
    public void buttonOK() {
        if (tf_id.getText().isEmpty() || tf_username.getText().isEmpty() || tf_password.getText().isEmpty() ||
                tf_email.getText().isEmpty() || choiceRole.getValue() == null || dateOfBirth.getValue() == null) {
            // Alert user to fill all fields
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();

        } else if (dbUltis.findQuery("SELECT * FROM users WHERE id = '" + tf_id.getText() + "'")) {
            // Alert user that id is already taken
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("ID is already taken");
            alert.showAndWait();

        } else if (dbUltis.findQuery("SELECT * FROM users WHERE username = '" + tf_username.getText() + "'")) {
            // Alert user that username is already taken
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Username is already taken");
            alert.showAndWait();

        } else if (dbUltis.findQuery("SELECT * FROM users WHERE email = '" + tf_email.getText() + "'")) {
            // Alert user that email is already taken
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Email is already taken");
            alert.showAndWait();

        }
        else {
            // Insert user into database
            dbUltis.loadQuery("INSERT INTO users (username, password, role, email, avatar, last_access, streak, date_of_birth, longest_streak) " +
                    "VALUES ('" + tf_username.getText() + "', '" + tf_password.getText() + "', '" + choiceRole.getValue() + "', '" +
                    tf_email.getText() + "', '" + "/Image/Avatar/Gekko.png" + "', '" + LocalDate.now().toString() + "', 0, '" +
                    dateOfBirth.getValue().toString() + "', 0)");
            sucessLabel.setText("User added successfully");
        }
    }
}
