package org.example.demo6.Controller.LoginScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class ForgotPasswordController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField email;

    @FXML
    private TextField maSv;

    @FXML
    private Button submit;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField confirmPassword;
    @FXML
    private Text nhapMoi;
    @FXML
    private Text nhapLai;


    @FXML
    private AnchorPane ap1;

    @FXML
    private AnchorPane ap;
    public void switchChangePassword(ActionEvent event) {

        ap.setVisible(true);
        ap1.setVisible(false);

    }

}

