package org.example.demo6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.demo6.HelloApplication;

import java.io.IOException;

public class GeneralController {
    public static void changescene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading Error");
            alert.setContentText("Could not load the scene: " + fxmlFile);
            alert.show();
            return; // Exit the method if loading fails
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
