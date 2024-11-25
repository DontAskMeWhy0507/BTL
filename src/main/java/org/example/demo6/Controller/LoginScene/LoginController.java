package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRippler;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.Classes.Library;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private boolean isTransitioning = false;
    private boolean isTransitionPass = false;

    // hiện mật khẩu
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

    // chuyển qua đăng ký
    @FXML
    private void loadSignUp(ActionEvent event) throws IOException {
        if (isTransitioning) return;
        isTransitioning = true;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene/SignUp.fxml"));

            Pane animatedPane = new Pane();
            animatedPane.getChildren().add(root);

            Scene scene = buttonSignUp.getScene();
            animatedPane.setPrefSize(scene.getWidth(), scene.getHeight());

            animatedPane.translateYProperty().set(scene.getHeight());// Start off-screen

            // Ensure the image is added only once and stays still
            if (!switchScene.getChildren().contains(img)) {
                switchScene.getChildren().add(0, img); // Add the image at the back
            }

            switchScene.getChildren().add(animatedPane);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(animatedPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);

            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                // Remove the mainPane after the transition
                switchScene.getChildren().remove(mainPane);
                isTransitioning = false; // Reset the flag
                buttonSignUp.setDisable(false); // Re-enable the button
            });

            timeline.play();

            StackPane.setAlignment(img, Pos.CENTER_RIGHT); // Align the image

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
            isTransitioning = false;
        }
    }

    // chuyển qua quên mật khẩu
    @FXML
    private void loadForgotPassword(ActionEvent event) throws IOException {
        if (isTransitionPass) return;
        isTransitionPass = true;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScene/ForgotPassword.fxml"));

            Pane animatedPane = new Pane();
            animatedPane.getChildren().add(root);

            Scene scene = buttonForgotPassword.getScene();
            animatedPane.setPrefSize(scene.getWidth(), scene.getHeight());

            animatedPane.translateYProperty().set(-scene.getHeight()); // Start off-screen

            if (!switchScene.getChildren().contains(img)) {
                switchScene.getChildren().add(0, img); // Add the image at the back
            }

            // Add the animatedPane to the switchScene
            switchScene.getChildren().add(animatedPane);

            // Create the transition animation
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(animatedPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);

            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                switchScene.getChildren().remove(mainPane);
                isTransitionPass = false;
            });

            timeline.play();

            StackPane.setAlignment(img, Pos.CENTER_RIGHT);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
            isTransitionPass = false; // Reset the flag in case of error
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

    // ảnh
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

    // enter để nhập
    public void Enter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            loginToHome(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }
}