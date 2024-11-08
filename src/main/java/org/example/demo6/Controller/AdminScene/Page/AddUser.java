package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddUser {

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
        } else {
            // Add user to database
            String url = "jdbc:sqlite:database//LibraryMain";
            String checkQuery = "SELECT COUNT(*) FROM USERS WHERE id = ?";
            String insertQuery = "INSERT INTO USERS (id, username, password, role, email, date, avatar) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                checkStmt.setInt(1, Integer.parseInt(tf_id.getText()));
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // ID already exists
                    sucessLabel.setText("Thất bại");
                } else {
                    // Add user to database
                    insertStmt.setInt(1, Integer.parseInt(tf_id.getText()));
                    insertStmt.setString(2, tf_username.getText());
                    insertStmt.setString(3, tf_password.getText());
                    insertStmt.setString(4, choiceRole.getValue());
                    insertStmt.setString(5, tf_email.getText());
                    insertStmt.setString(6, dateOfBirth.getValue().toString());
                    insertStmt.setString(7, ""); // Assuming avatar is not provided

                    insertStmt.executeUpdate();
                    sucessLabel.setText("Thành công");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
