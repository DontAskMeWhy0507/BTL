package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Settings {

    @FXML
    private ImageView avatarImageView;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button updateUsernameButton;

    @FXML
    private Button updateEmailButton;

    @FXML
    private Button updatePasswordButton;

    // Methods for avatar selection
    @FXML
    private void selectAvatar1() {
        setAvatar("/Image/Avatar/Clove.png");
    }

    @FXML
    private void selectAvatar2() {
        setAvatar("/Image/Avatar/Gekko.png");
    }

    @FXML
    private void selectAvatar3() {
        setAvatar("/Image/Avatar/Iso.png");
    }

    @FXML
    private void selectAvatar4() {
        setAvatar("/Image/Avatar/Jett.png");
    }

    @FXML
    private void selectAvatar5() {
        setAvatar("/Image/Avatar/Neon.png");
    }
    @FXML
    private void selectAvatar6() {
        setAvatar("/Image/Avatar/Omen.png");
    }

    @FXML
    private void selectAvatar7() {
        setAvatar("/Image/Avatar/Reyna.png");
    }

    @FXML
    private void selectAvatar8() {
        setAvatar("/Image/Avatar/Sage.png");
    }


    private void setAvatar(String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        avatarImageView.setImage(image);
    }

    // Methods to handle updating username, email, and password
    @FXML
    private void handleChangeUsername() {
        String newUsername = usernameField.getText();
        // Add logic to update the username
        System.out.println("Username updated to: " + newUsername);
    }

    @FXML
    private void handleChangeEmail() {
        String newEmail = emailField.getText();
        // Add logic to update the email
        System.out.println("Email updated to: " + newEmail);
    }

    @FXML
    private void handleChangePassword() {
        String newPassword = passwordField.getText();
        // Add logic to update the password
        System.out.println("Password updated.");
    }
}
