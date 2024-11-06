package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.Classes.DBUltis;
import com.jfoenix.controls.JFXTextField;


public class LoginController {

    @FXML
    private JFXButton switchLogin;

    @FXML
    private JFXButton buttonSignUp;

    @FXML
    private Text LoginText;

    @FXML
    private Text WelcomeText;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private JFXCheckBox remember;

    @FXML
    private JFXButton buttonForgotPassword;

    @FXML
    private Button buttonLogin;


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
