package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.Music;
import org.example.demo6.Classes.SnowEffect;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LibraryApplication extends Application {

    private final Library library = Library.getInstance();    // Singleton instance of Library
    private final Music music = Music.getInstance();          // Singleton instance of Music

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        // Tải font
        Font.loadFont(getClass().getResourceAsStream("/Font/Merriweather/Merriweather-Regular.ttf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/Font/DancingScript/DancingScript-SemiBold.ttf"), 20);

        // Tải giao diện chính
        Parent root = FXMLLoader.load(LibraryApplication.class.getResource("/View/LoginScene/Login.fxml"));

        // Tạo Scene và Pane chính để hiển thị hiệu ứng tuyết
        Pane mainPane = new Pane();
        mainPane.getChildren().add(root); // Thêm giao diện chính vào Pane

        // Cài đặt Scene
        Scene scene = new Scene(mainPane);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();

        // Thêm hiệu ứng tuyết
        SnowEffect snowEffect = new SnowEffect(mainPane);
        snowEffect.startSnow(200); // Bắt đầu 200 bông tuyết rơi

        // Xử lý khi đóng ứng dụng
        stage.setOnCloseRequest(e -> library.logOut(null));

        // Phát nhạc nền
        music.loop();
    }

    public static void main(String[] args) {
        launch();
    }
}