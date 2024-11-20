package org.example.demo6.Controller.UserScene.Page;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;

public class CommentController {
    @FXML
    private Button likeButton;

    @FXML
    private HBox reactionBox;

    @FXML
    private HBox selectedIconBox;

    @FXML
    private ImageView selectedIcon;

    @FXML
    private void initialize() {
        // Khi di chuột vào nút "Thích", hiển thị HBox chứa các biểu tượng
        likeButton.setOnMouseEntered(event -> reactionBox.setVisible(true));

        // Khi di chuột ra khỏi HBox chứa biểu tượng, ẩn nó đi
        reactionBox.setOnMouseExited(event -> reactionBox.setVisible(false));
    }

    @FXML
    private void selectReaction(javafx.scene.input.MouseEvent event) {
        // Lấy ImageView được chọn
        ImageView selected = (ImageView) event.getSource();

        // Lấy hình ảnh từ ImageView được chọn
        Image selectedImage = selected.getImage();

        // Hiển thị biểu tượng đã chọn ở khu vực comment
        selectedIcon.setImage(selectedImage);
        selectedIconBox.setVisible(true);

        // Ẩn HBox chứa các biểu tượng cảm xúc
        reactionBox.setVisible(false);
    }
}

