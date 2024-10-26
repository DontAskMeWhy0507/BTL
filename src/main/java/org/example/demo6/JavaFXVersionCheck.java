package org.example.demo6;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXVersionCheck extends Application {
    @Override
    public void start(Stage primaryStage) {
        System.out.println("JavaFX Version: " + System.getProperty("javafx.version"));
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}