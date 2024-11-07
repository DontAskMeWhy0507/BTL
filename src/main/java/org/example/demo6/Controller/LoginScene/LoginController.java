package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.Classes.DBUltis;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton buttonSignUp;

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

    @FXML
    private JFXRippler loginRippler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font1 = Font.loadFont(getClass().getResourceAsStream("/Font/Merriweather/Merriweather-Regular.ttf"), 20);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/Font/DancingScript/DancingScript-SemiBold.ttf"), 20);

        loginRippler = new JFXRippler(buttonLogin);
        loginRippler.getStyleClass().add("loginRippler");
        loginRippler.setRipplerFill(Paint.valueOf("white"));
        loginRippler.setRipplerRadius(60);
        mainPane.getChildren().add(loginRippler);

        AnchorPane.setTopAnchor(loginRippler, 475.0);
        AnchorPane.setLeftAnchor(loginRippler, 185.0);
    }

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
            DBUltis.changescene(event, "/View/LoginScene/SignUp.fxml", "Sign Up!");
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
            DBUltis.changescene(event, "/View/LoginScene/ForgotPassword.fxml", "ForgotPassword!");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

}
