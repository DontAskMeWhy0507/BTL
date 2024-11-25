package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.SnowEffect;

import org.example.demo6.Classes.Music;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HelloApplication extends Application {

    private Library library = Library.getInstance();    // Singleton instance of Library
    private Music music = Music.getInstance();
    // Singleton instance of Music
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        Font font1 = Font.loadFont(getClass().getResourceAsStream("/Font/Merriweather/Merriweather-Regular.ttf"), 20);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/Font/DancingScript/DancingScript-SemiBold.ttf"), 20);

        Parent root = FXMLLoader.load(HelloApplication.class.getResource("/View/LoginScene/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Library management system");
        stage.setScene(scene);
        stage.show();

        StackPane mainPane = new StackPane();
        Pane snowPane = new Pane();
        mainPane.getChildren().addAll(root, snowPane);
        SnowEffect snowEffect = new SnowEffect(snowPane);
        snowEffect.startSnowing(100);

        stage.setOnCloseRequest(e -> {
            library.logOut(null);
        });
        music.loop();
    }

    public static void main(String[] args) {
        launch();
    }
}