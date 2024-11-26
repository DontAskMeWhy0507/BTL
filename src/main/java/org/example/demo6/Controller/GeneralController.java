package org.example.demo6.Controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.LibraryApplication;

import java.io.IOException;

public class GeneralController {
    public static void changescene(ActionEvent event, String fxmlFile, String title) {
        try {
            // Load the new scene's root node
            FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource(fxmlFile));
            Parent newRoot = loader.load();

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

            // Fade in the new scene
            Scene newScene = new Scene(newRoot);
            newRoot.setOpacity(0); // Start fully transparent
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Adjust the stage size to fit the new scene before the fade-in
            blurTimeline.setOnFinished(e -> {
                // Apply the new scene to the stage
                stage.setScene(newScene);
                stage.sizeToScene(); // Resize stage to fit the new scene
                stage.setTitle(title);

                // Play fade-in transition for the new scene
                fadeIn.play();
            });

            // Start the blur transition
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
