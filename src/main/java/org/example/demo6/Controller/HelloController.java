package org.example.demo6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.HelloApplication;

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




}