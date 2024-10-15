package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private VBox dropdownMenu; // The VBox holding the menu items

    @FXML
    private void toggleMenu() {
        dropdownMenu.setVisible(!dropdownMenu.isVisible());
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene1(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/scene1.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene2(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/scene2.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchSignUp(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/SignUp.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchLogin(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/Login.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchForgotPass(ActionEvent event) {
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource("/View/ForgotPassword.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
}