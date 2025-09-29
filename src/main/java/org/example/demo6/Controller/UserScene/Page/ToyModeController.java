package org.example.demo6.Controller.UserScene.Page;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller cho chế độ Toy Mode - một trò chơi đơn giản với hiệu ứng hình học tương tác
 */
public class ToyModeController {
    
    @FXML
    private Canvas toyCanvas;
    
    @FXML
    private Pane toyPane;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button stopButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Label scoreLabel;
    
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private List<ToyParticle> particles;
    private Random random;
    private int score = 0;
    private boolean isRunning = false;
    
    @FXML
    public void initialize() {
        particles = new ArrayList<>();
        random = new Random();
        
        if (toyCanvas != null) {
            gc = toyCanvas.getGraphicsContext2D();
            setupCanvas();
        }
        
        updateScoreDisplay();
    }
    
    /**
     * Thiết lập canvas và sự kiện chuột
     */
    private void setupCanvas() {
        toyCanvas.setWidth(800);
        toyCanvas.setHeight(500);
        
        // Sự kiện click chuột để tạo hiệu ứng
        toyCanvas.setOnMousePressed(this::handleMouseClick);
        
        // Vẽ nền ban đầu
        clearCanvas();
    }
    
    /**
     * Xử lý sự kiện click chuột
     */
    private void handleMouseClick(MouseEvent event) {
        if (isRunning) {
            double x = event.getX();
            double y = event.getY();
            
            // Tạo burst của các particle tại vị trí click
            createParticleBurst(x, y);
            
            // Tăng điểm
            score += 10;
            updateScoreDisplay();
        }
    }
    
    /**
     * Tạo burst của particles tại vị trí cho trước
     */
    private void createParticleBurst(double x, double y) {
        int particleCount = 8 + random.nextInt(12); // 8-19 particles
        
        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount + random.nextDouble() * 0.5;
            double speed = 2 + random.nextDouble() * 4;
            
            ToyParticle particle = new ToyParticle(
                x, y,
                Math.cos(angle) * speed,
                Math.sin(angle) * speed,
                Color.hsb(random.nextDouble() * 360, 0.8, 0.9),
                3 + random.nextDouble() * 5
            );
            
            particles.add(particle);
        }
    }
    
    /**
     * Bắt đầu toy mode
     */
    @FXML
    public void startToyMode() {
        if (!isRunning) {
            isRunning = true;
            startGameLoop();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
    }
    
    /**
     * Dừng toy mode
     */
    @FXML
    public void stopToyMode() {
        if (isRunning) {
            isRunning = false;
            if (gameLoop != null) {
                gameLoop.stop();
            }
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }
    
    /**
     * Xóa canvas
     */
    @FXML
    public void clearCanvas() {
        particles.clear();
        score = 0;
        updateScoreDisplay();
        
        if (gc != null) {
            gc.setFill(Color.rgb(20, 30, 50)); // Nền xanh đậm
            gc.fillRect(0, 0, toyCanvas.getWidth(), toyCanvas.getHeight());
            
            // Vẽ grid nhẹ
            gc.setStroke(Color.rgb(40, 50, 70));
            gc.setLineWidth(0.5);
            
            for (int i = 0; i <= toyCanvas.getWidth(); i += 50) {
                gc.strokeLine(i, 0, i, toyCanvas.getHeight());
            }
            
            for (int i = 0; i <= toyCanvas.getHeight(); i += 50) {
                gc.strokeLine(0, i, toyCanvas.getWidth(), i);
            }
        }
    }
    
    /**
     * Khởi động game loop
     */
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }
    
    /**
     * Cập nhật logic game
     */
    private void update() {
        // Cập nhật tất cả particles
        particles.removeIf(particle -> {
            particle.update();
            return particle.shouldRemove();
        });
        
        // Tự động tạo particles ngẫu nhiên
        if (random.nextDouble() < 0.02) { // 2% chance mỗi frame
            createParticleBurst(
                random.nextDouble() * toyCanvas.getWidth(),
                random.nextDouble() * toyCanvas.getHeight()
            );
        }
    }
    
    /**
     * Vẽ tất cả elements
     */
    private void render() {
        // Làm mờ nền thay vì xóa hoàn toàn để tạo hiệu ứng trail
        gc.setFill(Color.rgb(20, 30, 50, 0.1));
        gc.fillRect(0, 0, toyCanvas.getWidth(), toyCanvas.getHeight());
        
        // Vẽ tất cả particles
        for (ToyParticle particle : particles) {
            particle.render(gc);
        }
    }
    
    /**
     * Cập nhật hiển thị điểm số
     */
    private void updateScoreDisplay() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }
    
    /**
     * Class đại diện cho một particle trong toy mode
     */
    private static class ToyParticle {
        private double x, y;
        private double velocityX, velocityY;
        private Color color;
        private double radius;
        private double life;
        private double maxLife;
        
        public ToyParticle(double x, double y, double velocityX, double velocityY, Color color, double radius) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.color = color;
            this.radius = radius;
            this.maxLife = 60 + Math.random() * 120; // 1-3 seconds at 60fps
            this.life = maxLife;
        }
        
        public void update() {
            // Cập nhật vị trí
            x += velocityX;
            y += velocityY;
            
            // Áp dụng gravity nhẹ
            velocityY += 0.1;
            
            // Giảm velocity theo thời gian (friction)
            velocityX *= 0.99;
            velocityY *= 0.99;
            
            // Giảm life
            life--;
            
            // Giảm kích thước theo thời gian
            radius = Math.max(0.5, radius * 0.995);
        }
        
        public void render(GraphicsContext gc) {
            double alpha = Math.max(0, life / maxLife);
            Color renderColor = Color.color(
                color.getRed(),
                color.getGreen(), 
                color.getBlue(),
                alpha
            );
            
            gc.setFill(renderColor);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            
            // Thêm highlight
            gc.setFill(Color.color(1, 1, 1, alpha * 0.3));
            gc.fillOval(x - radius * 0.3, y - radius * 0.3, radius * 0.6, radius * 0.6);
        }
        
        public boolean shouldRemove() {
            return life <= 0 || radius < 0.5;
        }
    }
}