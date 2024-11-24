package org.example.demo6.Controller.UserScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Review;
import org.example.demo6.Classes.User;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class CommentController implements Initializable {

    @FXML
    private VBox commentList;



    // Hàm để hiển thị tất cả các bình luận
    public void displayComments() {
        DBUltis reviewDao = new DBUltis();
        List<Review> reviews = reviewDao.getAllReviews();

        // Clear old comments from the interface
        commentList.getChildren().clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Review review : reviews) {
            try {
                // Load FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/CommentItem.fxml"));
                AnchorPane commentItem = loader.load();

                // Get controller and set data
                CommentItemController controller = loader.getController();
                User user = DBUltis.getUserById(review.getUserid());
                String formattedDate = dateFormat.format(review.getTimestamp());
                controller.setData(
                        user != null ? user.getPathToProfilePicture() : null,
                        user != null ? user.getUsername() : "Unknown",
                        review.getComment(),
                        formattedDate,  // Convert Timestamp to String
                        review.getRating()
                );

                // Add to display list
                commentList.getChildren().add(commentItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayComments();
    }
}
