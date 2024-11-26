package org.example.demo6.Controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.HelloApplication;
import org.example.demo6.Classes.SnowEffect;  // Import your SnowEffect class

import java.io.IOException;

public class GeneralController {
    public static void changescene(ActionEvent event, String fxmlFile, String title) {
        try {
            // Load the new scene's root node
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent newRoot = loader.load();

            // Create a new main pane and add the root of the new scene
            Pane mainPane = new Pane();
            mainPane.getChildren().add(newRoot); // Add the new scene root to the main pane

            // Get the current stage and scene
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene currentScene = stage.getScene();

            // Save the current scene root for blur effect
            Parent currentRoot = currentScene.getRoot();

            // Apply blur effect to the current scene
            GaussianBlur blur = new GaussianBlur(0);
            currentRoot.setEffect(blur);

            // Timeline for increasing blur
            Timeline blurTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(blur.radiusProperty(), 15))
            );

            // Fade in the new scene after the blur transition is finished
            Scene newScene = new Scene(mainPane);
            newRoot.setOpacity(0); // Start fully transparent
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Set stage properties
            stage.setTitle(title);

            // Adjust the stage size and apply the new scene after the blur effect
            blurTimeline.setOnFinished(e -> {
                // Apply the new scene to the stage
                stage.setScene(newScene);
                stage.sizeToScene(); // Resize the stage to fit the new scene

                // Start the fade-in effect for the new scene
                fadeIn.play();

                // Reapply snow effect to the new scene after fade-in
                SnowEffect snowEffect = new SnowEffect(mainPane); // Recreate snow effect for the new scene
                snowEffect.startSnow(400); // Start 200 snowflakes falling
            });

            // Start the blur effect transition
            blurTimeline.play();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Scene Load Error");
            alert.setContentText("Failed to load " + fxmlFile);
            alert.showAndWait();
        }
    }
}
