package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Review;
import org.example.demo6.Classes.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentController {

    @FXML
    private VBox commentList;

    /**
     * Hàm để hiển thị tất cả các bình luận cho một cuốn sách.
     *
     * @param book Đối tượng Book chứa thông tin sách cần hiển thị bình luận.
     */
    public void displayComments(Book book) {
        if (book == null) {
            System.err.println("No book specified. Cannot display comments.");
            return;
        }

        DBUltis reviewDao = new DBUltis();
        List<Review> reviews = reviewDao.getReviewsByISBN(book.getIsbn());

        // Xóa các bình luận cũ khỏi giao diện
        commentList.getChildren().clear();

        if (reviews.isEmpty()) {
            Label noCommentsLabel = new Label("No comments available for this book.");
            commentList.getChildren().add(noCommentsLabel);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Review review : reviews) {
            try {
                // Tải từng bình luận và thêm vào giao diện
                addCommentToListAdmin(review, dateFormat);
            } catch (IOException e) {
                System.err.println("Error loading comment item for review ID " + review.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Thêm một bình luận vào danh sách hiển thị.
     *
     * @param review      Đối tượng Review chứa thông tin bình luận.
     * @param dateFormat  Định dạng ngày tháng.
     * @throws IOException Nếu xảy ra lỗi khi tải FXML.
     */
    private void addCommentToListAdmin(Review review, SimpleDateFormat dateFormat) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/CommentItem.fxml"));
        AnchorPane commentItem = loader.load();

        // Lấy controller và thiết lập dữ liệu
        CommentItemController controller = loader.getController();
        User user = DBUltis.getUserById(review.getUserid());
        String formattedDate = dateFormat.format(review.getTimestamp());

        controller.setData(
                user != null ? user.getPathToProfilePicture() : null,
                user != null ? user.getUsername() : "Unknown",
                review.getComment(),
                formattedDate,
                review.getRating()
        );

        commentList.getChildren().add(commentItem);
    }

    /**
     * Thiết lập dữ liệu sách cho controller và hiển thị bình luận.
     *
     * @param book Đối tượng Book cần hiển thị bình luận.
     */
    public void setCommentData(Book book) {
        // Biến lưu trữ sách hiện tại
        displayComments(book);

    }
}
