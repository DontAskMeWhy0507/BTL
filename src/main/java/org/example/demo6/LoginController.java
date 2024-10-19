package org.example.demo6;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

//    @FXML
//    private Button buttonlogOut;
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        buttonlogOut.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DBUltis.changescene(event, "/View/Login.fxml", "Login!");
//            }
//        });
//    }
    private Stage stage;
    private Scene scene;
    private Parent root;

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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

}
