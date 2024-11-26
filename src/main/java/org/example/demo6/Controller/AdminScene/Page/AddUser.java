package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.demo6.Classes.Admin;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class AddUser {
    DBUltis dbUltis = new DBUltis();
    Library library = Library.getInstance();

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
    @FXML
    public void buttonOK() {
        try {
            // Kiểm tra xem trường tf_id có rỗng không
            if (tf_id.getText().isEmpty()) {
                throw new NumberFormatException("ID field is empty");
            }

            // Kiểm tra xem tf_id có phải là số hợp lệ không
            int id = Integer.parseInt(tf_id.getText().trim());

            // Kiểm tra các trường khác
            if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty() ||
                     choiceRole.getValue() == null || dateOfBirth.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");
                alert.showAndWait();
            } else if (dbUltis.findQuery("SELECT * FROM users WHERE id = '" + tf_id.getText() + "'")) {

                // id đã tồn tại
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("ID is already taken");
                alert.showAndWait();

            } else if (dbUltis.findQuery("SELECT * FROM users WHERE username = '" + tf_username.getText() + "'")) {
                // tên đã tồn tại
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Username is already taken");
                alert.showAndWait();

            } else if (dbUltis.findQuery("SELECT * FROM users WHERE email = '" + tf_email.getText() + "'")) {
                // email đã tồn tại
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Email is already taken");
                alert.showAndWait();

//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Warning");
//                alert.setHeaderText(null);
//                alert.setContentText("ID is already taken");
//                alert.showAndWait();
                User user = new User(id, tf_username.getText(), tf_password.getText(), tf_email.getText(), dateOfBirth.getValue(), "/Image/Avatar/Gekko.png", choiceRole.getValue(), null);


                if (library.getCurrentUser() instanceof Admin) {
                    Admin admin = (Admin) library.getCurrentUser();
                    admin.updateProfile(user);
                    sucessLabel.setText("User added successfully");
                } else {
                    System.err.println("Current user is not an admin.");
                }
            } else {

                User user = new User(id, tf_username.getText(), tf_password.getText(), tf_email.getText(), dateOfBirth.getValue(), "/Image/Avatar/Gekko.png", choiceRole.getValue(), null);

                // Logic thêm user vào cơ sở dữ liệu
                User user = new User(id, tf_username.getText(), tf_password.getText(),
                        tf_email.getText(), dateOfBirth.getValue(), "/Image/Avatar/Gekko.png", choiceRole.getValue(), null);


                if (library.getCurrentUser() instanceof Admin) {
                    Admin admin = (Admin) library.getCurrentUser();
                    admin.addUsers(user);
                    sucessLabel.setText("User added successfully");
                } else {
                    System.err.println("Current user is not an admin.");
                }
            }

        }
        catch (Exception e) {
            // id là số
        } catch (NumberFormatException e) {
            // Thông báo lỗi nếu ID không hợp lệ
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("ID must be a number");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setUser(User user) {
        tf_id.setText(String.valueOf(user.getId()));
        tf_username.setText(user.getUsername());
        tf_password.setText(user.getPassword());
        tf_email.setText(user.getEmail());
        choiceRole.setValue(user.getRole());
        dateOfBirth.setValue(user.getDateOfBirth());
    }


}
