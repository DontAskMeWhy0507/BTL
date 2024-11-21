package org.example.demo6.Controller.UserScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Review;

import java.io.IOException;
import java.util.List;

public class CommentSeenPageController {

    @FXML
    private VBox commentsContainer;

    @FXML
    private Button likeButton;

    @FXML
    private void initialize() {
        loadComments();
    }

    private void loadComments() {
        DBUltis dbUltis = new DBUltis();
        List<Review> reviews = dbUltis.getAllReviews(); // Lấy tất cả reviews từ database

        // Xóa tất cả các bình luận cũ nếu có
        commentsContainer.getChildren().clear();

        // Duyệt qua danh sách reviews và thêm vào commentsContainer
        for (Review review : reviews) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Comment.fxml"));
                AnchorPane commentBox = loader.load(); // Tải comment FXML
                CommentController controller = loader.getController();
                controller.setCommentData(review); // Gán dữ liệu cho mỗi bình luận
                commentsContainer.getChildren().add(commentBox); // Thêm vào container
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
