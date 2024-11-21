package org.example.demo6.Controller.UserScene.Page;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.Review;

public class CommentController {
    @FXML
    private Label usernameText;

    @FXML
    private Label commentText;

    @FXML
    private ImageView selectedIcon;

    @FXML
    private HBox selectedIconBox;

    @FXML
    private HBox reactionBox;

    @FXML
    private ImageView likeIcon, loveIcon, hahaIcon, wowIcon, sadIcon, angryIcon;

    @FXML
    private Label datePost;

    @FXML
    private Button likeButton;

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

    public void setCommentData(int id, int userId, String content, Integer replyToCommentId, String timestamp) {
        this.usernameText.setText("User " + userId); // Thay bằng tên người dùng từ database nếu cần
        this.commentText.setText(content);
        this.datePost.setText(timestamp);
    }

    public void setCommentData(Review review) {
        this.usernameText.setText("User " + review.getUser().getId()); // Replace with username if available
        this.commentText.setText(review.getComment());
    }

    public void setCommentData(Book book) {
        this.usernameText.setText("User " + book.getIsbn()); // Replace with username if available
        this.commentText.setText(book.getTitle());
    }
}

