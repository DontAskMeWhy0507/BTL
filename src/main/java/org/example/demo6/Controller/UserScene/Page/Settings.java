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

public class Settings extends org.example.demo6.Controller.AdminScene.Page.Settings {
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
            showPassword.setVisible(true);
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
        lib.getCurrentUser().setPassword(newPassword);
        DBUltis.updateUserInDatabase(lib.getCurrentUser());
        mainSceneController.setUser();
    }

    @FXML
    public void uploadAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Avatar");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Đường dẫn thư mục đích trong thư mục resources
                String destinationDir = "src/main/resources/Image/Avatar/";
                File destinationFolder = new File(destinationDir);

                // Kiểm tra nếu thư mục không tồn tại thì tạo mới
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                // Tạo tệp đích trong thư mục Avatar
                File destinationFile = new File(destinationDir + selectedFile.getName());

                // Copy ảnh vào thư mục Avatar
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn trong Library
                String relativePath = "/Image/Avatar/" + selectedFile.getName(); // Đường dẫn tương đối
                lib.getCurrentUser().setPathToProfilePicture(relativePath);
                DBUltis.updateUserInDatabase(lib.getCurrentUser());

                // Cập nhật hiển thị ảnh
                setAvatar(destinationFile.toURI().toString()); // Sử dụng URI của tệp đích

                // Hiển thị thông báo thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Avatar Uploaded");
                alert.setContentText("Your avatar has been updated successfully.");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Upload Failed");
                alert.setContentText("Failed to upload avatar. Please try again.");
                alert.showAndWait();
            }
        }
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