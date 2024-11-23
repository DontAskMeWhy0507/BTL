package org.example.demo6.Controller.UserScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.scene.image.Image;
import org.example.demo6.Classes.Review;
import org.example.demo6.Classes.User;


public class CommentItemController {



    @FXML
    private ImageView avatar;

    @FXML
    private Label usernameText;

    @FXML
    private Label commentText;

    @FXML
    private Label datePost;



    @FXML
    private HBox reactionBox;



    @FXML
    private Label rating;

    private int likes = 0;

    // Hiển thị các biểu tượng cảm xúc
    @FXML
    private void showReaction(MouseEvent event) {
        reactionBox.setVisible(true);
    }

    // Ẩn khu vực cảm xúc
    @FXML
    private void hideReaction(MouseEvent event) {
        reactionBox.setVisible(false);
    }

    // Xử lý khi người dùng chọn cảm xúc




    public void setData(String avatarPath, String username, String comment, String date, int ratingValue) {
        // Đặt avatar
        if (avatarPath != null) {
            avatar.setImage(new Image(getClass().getResource(avatarPath).toExternalForm()));
        } else {
            avatar.setImage(new Image(getClass().getResource("/Image/avatar.png").toExternalForm()));
        }

        // Đặt các giá trị khác
        usernameText.setText(username);
        commentText.setText(comment);
        datePost.setText(date);
        rating.setText(String.valueOf(ratingValue));
    }

}
