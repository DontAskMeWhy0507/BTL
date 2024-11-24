package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckComment extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the CommentItem.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/View/UserScene/Page/CommentItem.fxml"));

        // Create a scene with the loaded FXML
        Scene scene = new Scene(root);

        // Set the scene to the stage
        stage.setScene(scene);

        // Set the title of the stage
        stage.setTitle("Comment Item");

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}