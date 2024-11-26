package org.example.demo6.Classes;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class SnowEffect {
    private final Pane snowPane;
    private final Random random = new Random();

    public SnowEffect(Pane snowPane) {
        this.snowPane = snowPane;
    }

    // Bắt đầu hiệu ứng tuyết rơi
    public void startSnow(int numberOfSnowflakes) {
        for (int i = 0; i < numberOfSnowflakes; i++) {
            Circle snowflake = createSnowflake(); // Tạo bông tuyết
            snowPane.getChildren().add(snowflake); // Thêm vào Pane
            animateSnowflake(snowflake); // Kích hoạt hiệu ứng
        }
    }

    // Tạo bông tuyết
    private Circle createSnowflake() {
        Circle snowflake = new Circle();
        snowflake.setRadius(1 + random.nextDouble() * 3); // Kích thước ngẫu nhiên (1 - 4 px)
        Color color = Color.rgb(255, 99, 71, random.nextDouble()); // Màu hoa phượng rơi (Red-Orange)
        snowflake.setFill(color);
        snowflake.setCenterX(random.nextInt((int) snowPane.getWidth())); // Vị trí ngang ngẫu nhiên
        snowflake.setCenterY(-random.nextInt(200)); // Xuất hiện từ trên màn hình
        return snowflake;
    }

    // Tạo hoạt ảnh rơi cho bông tuyết
    private void animateSnowflake(Circle snowflake) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(snowflake);
        transition.setFromY(-200); // Bắt đầu từ ngoài màn hình trên
        transition.setToY(snowPane.getHeight() + 200); // Rơi xuống ngoài màn hình dưới
        transition.setDuration(Duration.seconds(5 + random.nextInt(10))); // Thời gian rơi (5-15s)
        transition.setToX(random.nextDouble() * snowflake.getCenterX()); // Dịch ngang nhẹ khi rơi

        // Lặp lại hoạt ảnh khi kết thúc
        transition.setOnFinished(event -> {
            snowflake.setCenterX(random.nextInt((int) snowPane.getWidth())); // Đặt lại vị trí ngang
            snowflake.setCenterY(-random.nextInt(200)); // Đặt lại vị trí dọc
            animateSnowflake(snowflake); // Kích hoạt lại hiệu ứng
        });

        transition.play(); // Bắt đầu hoạt ảnh
    }
}