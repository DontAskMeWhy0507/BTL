package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.util.Duration;
import javafx.scene.text.Text;

import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Controller.GeneralController;

import java.io.IOException;

import static org.example.demo6.Controller.GeneralController.changescene;

public class ForgotPasswordController extends GeneralController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    DBUltis dbUltis = new DBUltis();

    @FXML
    private TextField email;

    @FXML
    private TextField maSv;

    @FXML
    private Button submit;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private AnchorPane forgetPass;

    @FXML
    private AnchorPane resetPass;

    @FXML
    private TextField showPass;

    @FXML
    private TextField showRePass;

    @FXML
    private Button returnToLogin;

    @FXML
    private JFXCheckBox confirmPass;
    
    @FXML
    void showPassword(ActionEvent event) {
        if (confirmPass.isSelected()) {
            showPass.setText(newPassword.getText());
            showRePass.setText(confirmPassword.getText());

            newPassword.setVisible(false);
            confirmPassword.setVisible(false);
            showPass.setVisible(true);
            showRePass.setVisible(true);
        } else {
            newPassword.setText(showPass.getText());
            confirmPassword.setText(showRePass.getText());

            newPassword.setVisible(true);
            confirmPassword.setVisible(true);
            showPass.setVisible(false);
            showRePass.setVisible(false);
        }
    }

    public void switchChangePassword(ActionEvent event) {
        boolean check = dbUltis.findQuery("SELECT * FROM users WHERE email = '" + email.getText() + "' AND id = '" + maSv.getText() + "'");
        if (check) {

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), forgetPass);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        FadeTransition fade1 = new FadeTransition(Duration.seconds(0.5), resetPass);
        fade1.setFromValue(0.0);
        fade1.setToValue(1.0);

        fade.setOnFinished(e -> {
            forgetPass.setVisible(false);
            resetPass.setVisible(true);
            fade1.play();
        });

        fade.play();

            resetPass.setVisible(true);
            forgetPass.setVisible(false);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Email or ID is incorrect");
            alert.showAndWait();
        }

    }

    public void signIn(ActionEvent event) {
        if (newPassword.getText().equals(confirmPassword.getText())) {
            dbUltis.loadQuery("UPDATE users SET password = '" + newPassword.getText() + "' WHERE email = '" + email.getText() + "' AND id = '" + maSv.getText() + "'");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Change password successfully");
            alert.showAndWait();
            try {
                changescene(event, "/View/LoginScene/Login.fxml", "Log in");
            } catch (Exception e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password and Confirm Password are not the same");
            alert.showAndWait();
        }
    }

    public void returnLogin(ActionEvent event) {
        try {
            changescene(event, "/View/LoginScene/Login.fxml", "Log in");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }
}


