package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import org.example.demo6.Classes.DBUltis;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.example.demo6.Controller.GeneralController.changescene;

public class SignUpController implements Initializable {
    DBUltis dbUltis = new DBUltis();

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button buttonSign_Up;

    @FXML
    private JFXButton buttonLog_in;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private PasswordField tf_repassword;

    @FXML
    private ImageView eye1;

    @FXML
    private ImageView eye2;

    @FXML
    private TextField showPass;

    @FXML
    private TextField showRePass;

    private boolean passVis1 = false;

    @FXML
    private TextField tf_email;

    @FXML
    private Label password_check;

    @FXML
    private JFXRippler createAcc;

    @FXML
    private void initialize() {
        showPass.setVisible(false);
        showRePass.setVisible(false);
    }

    @FXML
    private void setShowPass(MouseEvent event, PasswordField passwordField, TextField textField, ImageView eyeIcon) {
        boolean isPasswordVisible = passVis1;

        isPasswordVisible = !isPasswordVisible;

        passVis1 = isPasswordVisible;

        if (isPasswordVisible) {
            eyeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Image/Icon/hidePassword.png")).toExternalForm()));
            textField.setText(passwordField.getText());
            textField.setDisable(false);
            textField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            eyeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Image/Icon/viewPassword.png")).toExternalForm()));
            passwordField.setText(tf_password.getText());
            textField.setDisable(true);
            passwordField.setVisible(true);
            textField.setVisible(false);
        }
    }

    @FXML
    private void clickEye1(MouseEvent event) {
        setShowPass(event, tf_password, showPass, eye1);
    }

    @FXML
    private void clickEye2(MouseEvent event) {
        setShowPass(event, tf_repassword, showRePass, eye2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createAcc = new JFXRippler(buttonSign_Up);
        createAcc.getStyleClass().add("signUpRippler");
        createAcc.setRipplerFill(Paint.valueOf("white"));
        createAcc.setRipplerRadius(60);
        mainPane.getChildren().add(createAcc);

        AnchorPane.setTopAnchor(createAcc, 550.0);
        AnchorPane.setLeftAnchor(createAcc, 290.0);
        
        buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_id.getText().isEmpty() && !tf_username.getText().isEmpty()
                        && !tf_password.getText().isEmpty() && !tf_repassword.getText().isEmpty()) {
                    if (!tf_password.getText().equals(tf_repassword.getText())) {
//                        System.out.println("Password not match");
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Password not match");
//                        alert.show();
                        password_check.setText("Mat khau khong trung khop!");
                    } else if (!isPasswordStrong(tf_password.getText())) {
//                        System.out.println("Password is not strong enough");
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
//                        alert.show();
                        password_check.setText("Mat khau phai co 8 ki tu, in hoa, in thuong va ki tu dac biet");
                    } else if (!isEmailValid(tf_email.getText())) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Email khong hop le");
//                        alert.show();
                        password_check.setText("Email khong hop le");
                    }
                    else {
                        dbUltis.signUp(event, tf_id.getText(), tf_username.getText(), tf_password.getText(), tf_email.getText());
                    }
                } else {
                    System.out.println("Please fill all the fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the fields");
                    alert.show();
                }
            }
        });

        buttonLog_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changescene(event, "/View/LoginScene/Login.fxml", "Login!");
            }
        });
    }

    private boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String specialChars = "(.*[!@#$%^&*(),.?\":{}|<>].*)";
        return Pattern.matches(upperCaseChars, password) &&
                Pattern.matches(lowerCaseChars, password) &&
                Pattern.matches(numbers, password) &&
                Pattern.matches(specialChars, password);
    }
    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailPattern, email);
    }

//    public void Enter(KeyEvent event) {
//        if(event.getCode() == KeyCode.ENTER) {
//            handle(new ActionEvent(event.getSource(), event.getTarget()));
//        }
//    }
}