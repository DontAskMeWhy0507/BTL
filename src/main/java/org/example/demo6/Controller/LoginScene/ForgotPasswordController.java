package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.Objects;

import static org.example.demo6.Controller.GeneralController.changescene;

public class ForgotPasswordController {
    private Stage stage;
    private Scene scene;
    private Parent root;

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
        resetPass.setVisible(true);
        forgetPass.setVisible(false);
    }

    public void signIn(ActionEvent event) {
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

