package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}