package org.example.demo6.Controller.UserScene.Page;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;
import org.example.demo6.Controller.UserScene.MainSceneUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Settings {
    DBUltis DBUltis = new DBUltis();
    Library lib = Library.getInstance();
    private MainSceneUser mainSceneController;

    public void setMainSceneController(MainSceneUser mainSceneController) {
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
    private TextField showPassword;

    @FXML
    private JFXCheckBox showPass;

    @FXML
    private ImageView avatarAdd1;
    @FXML
    private ImageView avatarAdd2;
    @FXML
    private ImageView avatarAdd3;

    boolean changeAvatar = false;
    boolean changeUsername = false;
    boolean changeEmail = false;
    boolean changePassword = false;

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

    @FXML
    private void selectAvatarAdd1(){
        setAvatar("/Image/Avatar/Skye1.png");
    }

    @FXML
    private void selectAvatarAdd2(){
        setAvatar("/Image/Avatar/Raze2.png");
    }

    @FXML
    private void selectAvatarAdd3(){
        setAvatar("/Image/Avatar/Killjoy3.png");
    }

    public void initialize() {
        // Set the current user's information
        avatarAdd1.setVisible(DBUltis.findQuery("SELECT 1 FROM users WHERE id = " + lib.getCurrentUser().getId() + " AND LONGEST_STREAK > 7"));
        avatarAdd2.setVisible(DBUltis.findQuery("SELECT 1 FROM users WHERE id = " + lib.getCurrentUser().getId() + " AND LONGEST_STREAK > 10"));
        avatarAdd3.setVisible(DBUltis.findQuery("SELECT 1 FROM users WHERE id = " + lib.getCurrentUser().getId() + " AND LONGEST_STREAK > 30"));
    }



    public void confirmAvatarSelection() {
        String avatarPath = avatarFile.getPath();

        // Replace backslashes with forward slashes to standardize the path format
        avatarPath = avatarPath.replace("\\", "/");

        lib.getCurrentUser().setPathToProfilePicture(avatarPath);
        System.out.println("Avatar updated to: " + lib.getCurrentUser().getPathToProfilePicture());
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
        changeAvatar = true;

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
        if (newUsername.equals("")) {
            return;
        }
        changeUsername = true;
        lib.getCurrentUser().setUsername(newUsername);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    @FXML
    private void handleChangeEmail() {
        String newEmail = emailField.getText();
        if (newEmail.equals("")) {
            return;
        }
        changeEmail = true;
        lib.getCurrentUser().setEmail(newEmail);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    @FXML
    void getPassword(ActionEvent event) {
        if (showPass.isSelected()) {
            showPassword.setText(passwordField.getText());
            passwordField.setVisible(false);
            showPassword.setVisible(true);
        } else {
            showPassword.setText(passwordField.getText());
            showPassword.setText(showPassword.getText());
            passwordField.setVisible(true);
            showPassword.setVisible(false);
        }
    }

    @FXML
    private void handleChangePassword() {
        String newPassword = passwordField.getText();
        if (newPassword.equals("")) {
            return;
        }
        changePassword = true;
        // Add logic to update the password
        lib.getCurrentUser().setPassword(newPassword);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    public void confirmChange() {
        confirmAvatarSelection();
        handleChangeUsername();
        handleChangeEmail();
        handleChangePassword();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Changes saved");
        alert.setContentText("You have successfully changed" +
                (changeAvatar ? " your avatar" : "") +
                (changeUsername ? ", your username" : "") +
                (changeEmail ? ", your email" : "") +
                (changePassword ? ", your password" : "") +
                ".");
        alert.showAndWait();
    }


}