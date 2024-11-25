package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.Music;
import org.example.demo6.Classes.SnowEffect;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HelloApplication extends Application {

    private Library library = Library.getInstance();
    private Music music = Music.getInstance();

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("/Font/Merriweather/Merriweather-Regular.ttf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/Font/DancingScript/DancingScript-SemiBold.ttf"), 20);

        // Load giao diện chính
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("/View/LoginScene/Login.fxml"));

        // Tạo một StackPane để chứa giao diện chính và hiệu ứng tuyết
//        StackPane mainPane = new StackPane();
//        Pane snowPane = new Pane(); // Lớp chứa tuyết
//        mainPane.getChildren().addAll(root, snowPane);

        // Thiết lập giao diện chính
        Scene scene = new Scene(root);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
//        // Tạo hiệu ứng tuyết
//        SnowEffect snowEffect = new SnowEffect(snowPane);
//        snowEffect.startSnowing(100); // 100 bông tuyết

        // Xử lý sự kiện khi đóng cửa sổ
        stage.setOnCloseRequest(e -> library.logOut(null));

        // Chạy nhạc nền
        music.loop();
    }

    public static void main(String[] args) {
        launch();
    }
}