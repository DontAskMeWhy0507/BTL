package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.example.demo6.Classes.Music;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HelloApplication extends Application {
    private Music music;

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        Font font = Font.loadFont(getClass().getResourceAsStream("/Font/Merriweather/Merriweather-Regular.ttf"), 20);
        Font font1 = Font.loadFont(getClass().getResourceAsStream("/Font/DancingScript/DancingScript-SemiBold.ttf"), 20);
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("/View/LoginScene/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Library management system");
        stage.setScene(scene);
        stage.show();

        music = new Music("src/main/resources/Sound/jingle-bells.mp3");
        music.loop();
    }

    public static void main(String[] args) {
        launch();
    }
}