package org.example.demo6.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.DBUltis;


public class LoginController {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonSignUp;

    @FXML
    private JFXButton buttonForgotPassword;


    public void loginToHome(ActionEvent event) {
        // Tạo và hiển thị cảnh báo

        try {
            // Giả lập độ trễ khi đăng nhập (thay bằng logic thực tế)
            DBUltis.logIn(event, tf_username.getText(), tf_password.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
            Throwable cause = ex.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }



    public void signUp1(ActionEvent event) {
        try {
            DBUltis.changescene(event, "/View/SignUp.fxml", "Sign Up!");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

    public void switchForgotPassword(ActionEvent event) {
        try {
            DBUltis.changescene(event, "/View/ForgotPassword.fxml", "ForgotPassword!");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

}
