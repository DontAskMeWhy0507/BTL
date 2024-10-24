package org.example.demo6;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


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
        try {
            DBUltis.logIn(event, tf_username.getText(), tf_password.getText());
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
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
