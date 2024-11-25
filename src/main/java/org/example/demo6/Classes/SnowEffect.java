package org.example.demo6.Classes;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class SnowEffect {

    private final Pane snowPane;   // Nơi hiển thị tuyết
    private final Random random = new Random();

    public SnowEffect(Pane snowPane) {
        this.snowPane = snowPane;
    }

    // Phương thức tạo và hiển thị bông tuyết
    public void startSnowing(int numberOfSnowflakes) {
        for (int i = 0; i < numberOfSnowflakes; i++) {
            createSnowflake();
        }

    }

    // Tạo bông tuyết và animation rơi
    private void createSnowflake() {
        Circle snowflake = new Circle(random.nextDouble() * 3 + 2, Color.WHITE); // Kích thước ngẫu nhiên
        snowflake.setTranslateX(random.nextDouble() * snowPane.getWidth()); // Vị trí ngang ngẫu nhiên
        snowflake.setTranslateY(-10); // Bắt đầu từ trên màn hình

        // Animation cho bông tuyết
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(random.nextDouble() * 5 + 3)); // Thời gian rơi ngẫu nhiên
        transition.setNode(snowflake);
        transition.setFromY(-10); // Từ trên
        transition.setToY(snowPane.getHeight() + 10); // Xuống dưới
        transition.setOnFinished(e -> snowPane.getChildren().remove(snowflake)); // Xóa bông tuyết khi ra khỏi màn hình

        // Thêm vào giao diện và chạy animation
        snowPane.getChildren().add(snowflake);
        transition.play();
    }
}