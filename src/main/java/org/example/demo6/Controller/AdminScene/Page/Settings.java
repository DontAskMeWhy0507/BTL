package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;
import org.example.demo6.Controller.AdminScene.MainSceneClass;

import java.io.File;

public class Settings {
    Library lib = Library.getInstance();
    private MainSceneClass mainSceneController;

    public void setMainSceneController(MainSceneClass mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    private ImageView avatarImageView;
    private File avatarFile;
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

    public void confirmAvatarSelection() {
        String avatarPath = avatarFile.getPath();

        // Replace backslashes with forward slashes to standardize the path format
        avatarPath = avatarPath.replace("\\", "/");

        lib.getCurrentUser().setPathToProfilePicture(avatarPath);
        System.out.println("Avatar updated to: " + lib.getCurrentUser().getPathToProfilePicture());
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();

    }


    private void setAvatar(String imagePath) {
        Image avatarImage = new Image(getClass().getResourceAsStream(imagePath));
        avatarImageView.setImage(avatarImage);
        avatarFile = new File(imagePath);
    }

    // Methods to handle updating username, email, and password
    @FXML
    private void handleChangeUsername() {
        String newUsername = usernameField.getText();
        lib.getCurrentUser().setUsername(newUsername);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    @FXML
    private void handleChangeEmail() {
        String newEmail = emailField.getText();

        lib.getCurrentUser().setEmail(newEmail);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    @FXML
    private void handleChangePassword() {
        String newPassword = passwordField.getText();
        // Add logic to update the password
        lib.getCurrentUser().setPassword(newPassword);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }
}
