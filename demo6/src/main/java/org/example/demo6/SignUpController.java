package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button buttonSign_Up;
    @FXML
    private Button buttonLog_in;
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_repassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_id.getText().isEmpty() && !tf_username.getText().isEmpty()
                        && !tf_password.getText().isEmpty() && !tf_repassword.getText().isEmpty()) {
                    if (!tf_password.getText().equals(tf_repassword.getText())) {
                        System.out.println("Password not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password not match");
                        alert.show();
                    } else {
                        DBUltis.signUp(event ,tf_id.getText(), tf_username.getText()
                                , tf_password.getText());
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
                DBUltis.changescene(event, "/View/Login.fxml", "Login!");
            }
        });
    }
}
