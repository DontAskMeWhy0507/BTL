package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.demo6.Classes.Admin;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.User;

public class UpdateUser extends AddUser {
    DBUltis dbUltis = new DBUltis();
    Library library = Library.getInstance();
    User user;

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


    // hiển thị mật khẩu

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

    // xác nhận thêm người dùng
    public void buttonOK() {
        try {
            // Kiểm tra tất cả các trường
            validateFields();

            int id = Integer.parseInt(tf_id.getText().trim());
            User user = new User(id, tf_username.getText(), tf_password.getText(),
                    tf_email.getText(), dateOfBirth.getValue(),
                    "/Image/Avatar/Gekko.png", choiceRole.getValue(), null);

            // Kiểm tra xem ID, username hoặc email đã tồn tại chưa
            checkDuplicateUser(id, tf_username.getText(), tf_email.getText());

            // Xử lý thêm hoặc cập nhật user
            if (library.getCurrentUser() instanceof Admin) {
                Admin admin = (Admin) library.getCurrentUser();
                if (dbUltis.findQuery("SELECT * FROM users WHERE id = '" + id + "'")) {
                    admin.updateProfile(user);
                    sucessLabel.setText("User updated successfully");
                } else {
                    admin.addUsers(user);
                    sucessLabel.setText("User added successfully");
                }
            } else {
                throw new Exception("Current user is not an admin.");
            }

        } catch (NumberFormatException e) {
            showAlert("Warning", "ID must be a number");
        } catch (IllegalArgumentException e) {
            showAlert("Warning", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    // Phương thức kiểm tra và ném ngoại lệ nếu trường không hợp lệ
    private void validateFields() throws IllegalArgumentException {
        if (tf_id.getText().isEmpty()) {
            throw new IllegalArgumentException("ID field is empty");
        }
        if (tf_username.getText().isEmpty()) {
            throw new IllegalArgumentException("Username field is empty");
        }
        if (tf_password.getText().isEmpty()) {
            throw new IllegalArgumentException("Password field is empty");
        }
        if (tf_email.getText().isEmpty()) {
            throw new IllegalArgumentException("Email field is empty");
        }
        if (choiceRole.getValue() == null) {
            throw new IllegalArgumentException("Role must be selected");
        }
        if (dateOfBirth.getValue() == null) {
            throw new IllegalArgumentException("Date of birth must be selected");
        }
    }

    // Phương thức kiểm tra trùng lặp và ném ngoại lệ
    private void checkDuplicateUser(int id, String username, String email) throws Exception {
        if (dbUltis.findQuery("SELECT * FROM users WHERE id = '" + id + "'" + "AND id != " + user.getId())) {
            throw new Exception("ID is already taken");
        }
        if (dbUltis.findQuery("SELECT * FROM users WHERE username = '" + username + "'" + "AND id != " + user.getId())) {
            throw new Exception("Username is already taken");
        }
        if (dbUltis.findQuery("SELECT * FROM users WHERE email = '" + email + "'" + "AND id != " + user.getId())) {
            throw new Exception("Email is already taken");
        }
    }

    // Phương thức hiển thị thông báo
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void setUser(User user) {
        this.user = user;
        tf_id.setText(String.valueOf(user.getId()));
        tf_username.setText(user.getUsername());
        tf_password.setText(user.getPassword());
        tf_email.setText(user.getEmail());
        choiceRole.setValue(user.getRole());
        dateOfBirth.setValue(user.getDateOfBirth());
    }


}


