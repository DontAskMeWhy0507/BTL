package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRippler;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.Music;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.demo6.Controller.GeneralController.changescene;

public class LoginController implements Initializable {
    @FXML
    private StackPane switchScene;

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
    private TextField showPassword;

    @FXML
    private JFXCheckBox remember;

    @FXML
    private JFXCheckBox showPass;

    @FXML
    private JFXButton buttonForgotPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private JFXRippler loginRippler;

    @FXML
    private ImageView img;

    @FXML
    void getPassword(ActionEvent event) {
        if (showPass.isSelected()) {
            showPassword.setText(tf_password.getText());
            tf_password.setVisible(false);
            showPassword.setVisible(true);
        } else {
            showPassword.setText(tf_password.getText());
            tf_password.setText(showPassword.getText());
            tf_password.setVisible(true);
            showPassword.setVisible(false);
        }
    }

    @FXML
    private void loadSignUp(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene/SignUp.fxml"));
            Scene scene = buttonSignUp.getScene();

            root.translateYProperty().set(scene.getHeight());
            switchScene.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 ->{
                switchScene.getChildren().remove(mainPane);
            });
            timeline.play();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

    @FXML
    private void loadForgotPassword(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene/ForgotPassword.fxml"));
            Scene scene = buttonForgotPassword.getScene();

            root.translateYProperty().set(-scene.getHeight());
            switchScene.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 ->{
                switchScene.getChildren().remove(mainPane);
            });
            timeline.play();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginRippler = new JFXRippler(buttonLogin);
        loginRippler.getStyleClass().add("loginRippler");
        loginRippler.setRipplerFill(Paint.valueOf("white"));
        loginRippler.setRipplerRadius(60);
        mainPane.getChildren().add(loginRippler);

        AnchorPane.setTopAnchor(loginRippler, 475.0);
        AnchorPane.setLeftAnchor(loginRippler, 185.0);
    }


    public void setStage(Stage stage) {
        img.fitWidthProperty().bind(stage.widthProperty());
        img.fitHeightProperty().bind(stage.heightProperty());
    }

    public void loginToHome(ActionEvent event) {
        // Tạo và hiển thị cảnh báo

        try {
            // Giả lập độ trễ khi đăng nhập (thay bằng logic thực tế)
            Library.logIn(event, tf_username.getText(), tf_password.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
            Throwable cause = ex.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

    public void Enter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            loginToHome(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }
}
