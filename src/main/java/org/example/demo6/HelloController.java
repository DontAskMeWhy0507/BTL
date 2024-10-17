package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private VBox dropdownMenu; // The VBox holding the menu items

    @FXML
    private void toggleMenu() {
        dropdownMenu.setVisible(!dropdownMenu.isVisible());
    }

    @FXML
    private Label welcomeText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchChangePass(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/ChangePassword.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void clickTopBooks(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/TopBooks.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void clickSearchButton(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/Search.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }
    public void returnHome(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/Home.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }





    @FXML
    private Button buttonLogOut;


    public void logOut(ActionEvent event) {
        try {
            DBUltis.changescene(event, "/View/Login.fxml", "Login!");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }

}