package org.example.demo6.Controller.LoginScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import org.example.demo6.Classes.Library;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.example.demo6.Controller.GeneralController.changescene;

public class SignUpController implements Initializable {
    DBUltis dbUltis = new DBUltis();
    Library lib = Library.getInstance();

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
    private JFXCheckBox showPassword;

    @FXML
    void getPass (ActionEvent event) {
        if (showPassword.isSelected()) {
            showPass.setText(tf_password.getText());
            showRePass.setText(tf_repassword.getText());

            tf_password.setVisible(false);
            tf_repassword.setVisible(false);
            showPass.setVisible(true);
            showRePass.setVisible(true);
        } else {
            tf_password.setText(showPass.getText());
            tf_repassword.setText(showRePass.getText());

            tf_password.setVisible(true);
            tf_repassword.setVisible(true);
            showPass.setVisible(false);
            showRePass.setVisible(false);
        }
    }

    private boolean isTransitionLogin = false;

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createAcc = new JFXRippler(buttonSign_Up);
        createAcc.getStyleClass().add("signUpRippler");
        createAcc.setRipplerFill(Paint.valueOf("white"));
        createAcc.setRipplerRadius(60);
        mainPane.getChildren().add(createAcc);

        AnchorPane.setTopAnchor(createAcc, 600.0);
        AnchorPane.setLeftAnchor(createAcc, 200.0);

        buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_id.getText().isEmpty() && !tf_username.getText().isEmpty()
                        && !tf_password.getText().isEmpty() && !tf_repassword.getText().isEmpty()) {

                        lib.signUp(event, tf_id.getText(), tf_username.getText(), tf_password.getText(), tf_email.getText());

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing Information");
                    alert.setContentText("Please fill all the fields.");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/Style/Alert/Alert.css").toExternalForm());
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
//        String upperCaseChars = "(.*[A-Z].*)";
//        String lowerCaseChars = "(.*[a-z].*)";
//        String numbers = "(.*[0-9].*)";
//        String specialChars = "(.*[!@#$%^&*(),.?\":{}|<>].*)";
//        return Pattern.matches(upperCaseChars, password) &&
//                Pattern.matches(lowerCaseChars, password) &&
//                Pattern.matches(numbers, password) &&
//                Pattern.matches(specialChars, password);
        return true;
    }
    private boolean isEmailValid(String email) {
//        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        return Pattern.matches(emailPattern, email);
        return true;
    }

//    public void Enter(KeyEvent event) {
//        if(event.getCode() == KeyCode.ENTER) {
//            handle(new ActionEvent(event.getSource(), event.getTarget()));
//        }
//    }
}