package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpController implements Initializable {

    @FXML
    private Button buttonSign_Up;
    @FXML
    private Button buttonLog_in;
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_repassword;
    @FXML
    private Label password_check;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_id.getText().isEmpty() && !tf_username.getText().isEmpty()
                        && !tf_password.getText().isEmpty() && !tf_repassword.getText().isEmpty()) {
                    if (!tf_password.getText().equals(tf_repassword.getText())) {
//                        System.out.println("Password not match");
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Password not match");
//                        alert.show();
                        password_check.setText("Mat khau khong trung khop!");
                    } else if (!isPasswordStrong(tf_password.getText())) {
//                        System.out.println("Password is not strong enough");
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
//                        alert.show();
                        password_check.setText("Mat khau phai co 8 ki tu, in hoa, in thuong va ki tu dac biet");
                    } else {
                        DBUltis.signUp(event, tf_id.getText(), tf_username.getText(), tf_password.getText());
                    }
                } else {
                    System.out.println("Please fill all the fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the fields");
                    alert.show();
                }
            }
        });

        buttonLog_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUltis.changescene(event, "/View/Login.fxml", "Login!");
            }
        });
    }

    private boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String specialChars = "(.*[!@#$%^&*(),.?\":{}|<>].*)";
        return Pattern.matches(upperCaseChars, password) &&
                Pattern.matches(lowerCaseChars, password) &&
                Pattern.matches(numbers, password) &&
                Pattern.matches(specialChars, password);
    }
}