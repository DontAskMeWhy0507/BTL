package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static org.example.demo6.Controller.GeneralController.changescene;

public class ForgotPasswordController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private LoginController loginController;

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

    private boolean isTransition = false;

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
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void signIn(ActionEvent event) {
        if (isTransition) {
            return;
        }
        isTransition = true;

        try {
            StackPane switchScene = loginController.getSwitchScene();
            Pane mainPane = loginController.getMainPane();
            Pane currentPane = (Pane) switchScene.getChildren().get(switchScene.getChildren().size() - 1);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(currentPane.translateYProperty(), switchScene.getHeight(), Interpolator.EASE_BOTH);  // Move down off-screen
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);

            timeline.getKeyFrames().add(kf);

            // Cleanup after the transition
            timeline.setOnFinished(event1 -> {
                switchScene.getChildren().remove(currentPane);  // Remove the current pane
                if (!switchScene.getChildren().contains(mainPane)) {
                    switchScene.getChildren().add(mainPane);  // Add the original pane back
                }
                isTransition = false;  // Reset the flag
            });

            timeline.play();
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

