package org.example.demo6.Controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo6.HelloApplication;

import java.io.IOException;

public class GeneralController {
    private static ImageView img;
    private static AnchorPane mainPane;

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
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Node sourceNode = (Node) event.getSource();

        if (stage != null) {
            stage.setTitle(title);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    public static void loadSceneWithAnimation(ActionEvent event, String fxmlPath, Duration duration, String direction, StackPane switchScene, boolean isTransitionPass) throws IOException {
        if (isTransitionPass) return;
        isTransitionPass = true;

        try {
            Parent root = FXMLLoader.load(GeneralController.class.getResource((fxmlPath)));
            Pane animatedPane = new Pane();
            animatedPane.getChildren().add(root);

            Scene scene = ((Node) event.getSource()).getScene();
            animatedPane.setPrefSize(scene.getWidth(), scene.getHeight());

            // Set initial position based on direction
            switch (direction) {
                case "UP":
                    animatedPane.translateYProperty().set(-scene.getHeight());
                    break;
                case "DOWN":
                    animatedPane.translateYProperty().set(scene.getHeight());
                    break;
            }

            if (!switchScene.getChildren().contains(img)) {
                switchScene.getChildren().add(0, img); // Add the image at the back if not present
            }

            switchScene.getChildren().add(animatedPane);

            // Create the transition animation
            Timeline timeline = new Timeline();
            KeyValue kv;

            kv = new KeyValue(animatedPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf = new KeyFrame(duration, kv);

            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                switchScene.getChildren().remove(mainPane);
            });

            timeline.play();

            StackPane.setAlignment(img, Pos.CENTER_RIGHT);

        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
            isTransitionPass = false; // Reset the flag in case of error
        }
    }
}
