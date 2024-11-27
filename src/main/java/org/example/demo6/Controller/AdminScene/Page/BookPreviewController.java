// src/main/java/org/example/demo6/Controller/BookPreviewController.java
package org.example.demo6.Controller.AdminScene.Page;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.AdminScene.MainSceneClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class BookPreviewController extends BookUnitController {
    Library library = Library.getInstance();
    private static final int descriptionLength = 200;

    private Book currentBook;

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Text bookAuthor;

    @FXML
    private Text bookPublishedDate;

    @FXML
    private Text bookCategoryLabel;

    @FXML
    private Text bookPublisher;

    @FXML
    private Text bookLanguage;

    @FXML
    private Text bookDescription;

    @FXML
    private ImageView bookCoverImage;

    @FXML
    private JFXButton viewMoreButton;

    @FXML
    public Text fullDescription;

    @FXML
    private Label rateAvg;

    @FXML
    private Rating ratingBook;

    @FXML
    private TextField commentInputField;

    @FXML
    private VBox commentsContainer;

    @FXML
    private Button addData;

    private String descriptionTemp;

    private boolean isAPI;

    public void setAPI(boolean API) {
        isAPI = API;
        addData.setVisible(isAPI);
    }
    @FXML
    public void initialize() {
//        if (!isAPI) {
//            addData.setVisible(false);
//        } else {
//            addData.setVisible(true);
//        }
    }

    // hiển thị trang miêu tả đầy đủ
    @FXML
    private void toggleDescription() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Description.fxml"));
        Parent parent = loader.load();
        BookPreviewController controller = loader.getController();
        controller.fullDescription.setText(descriptionTemp);
        Stage stage = new Stage();
        stage.setTitle("Description");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    // mượn sách
    public void borrowBook() {
        Library library = Library.getInstance();
        library.getCurrentUser().borrowBook(currentBook);
    }

    // trả sách
    public void returnBook() {
        Library library = Library.getInstance();
        library.getCurrentUser().returnBook(currentBook);
    }

    // đăng tải comment
    public void postComment() {
        User user = Library.getInstance().getCurrentUser();
        double ratingValue = ratingBook.getRating();
        String comment = commentInputField.getText(); // Replace with actual comment input

        // Create a new Review object
        Review review = new Review(comment, (int) ratingValue, user);

        // Save the review to the database
        DBUltis dbUltis = new DBUltis();
        dbUltis.saveReviewToDatabase(review, currentBook);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText("Bạn đã đăng bình luận thành công.");
        alert.setContentText("Bạn đã đánh giá " + ratingValue + " sao cho cuốn sách này.");
        alert.showAndWait();

    }

    // miêu tả trong book preview
    public void setDescriptionLength (String description) {
        if (description.length() > descriptionLength) {
            descriptionTemp = description;
            bookDescription.setText(description.substring(0, descriptionLength) + "...");
            viewMoreButton.setVisible(true);
        } else {
            bookDescription.setText(description);
            viewMoreButton.setVisible(false);
        }
    }

    // setter dữ liệu sách
    public void setBookData(Book book) {
        currentBook = book;
        bookTitleLabel.setText(book.getTitle());
        setDescriptionLength(book.getDescription());
        bookAuthor.setText(book.getAuthor());
        bookPublishedDate.setText(book.getPublishedDate().toString());
        bookCategoryLabel.setText(book.getCategory());
        bookPublisher.setText(book.getPublisher());
        bookLanguage.setText(book.getLanguage());

        DBUltis dbUltis = new DBUltis();
        double avgRating = dbUltis.getAverageRatingForBook(book);
        rateAvg.setText(String.format("%.2f", avgRating));

        // kiểm tra ảnh và path
        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            try {
                if (book.getCoverImagePath().startsWith("http")) {
                    // Tải ảnh từ url
                    Image coverImage = new Image(book.getCoverImagePath(), true);  // Use background loading
                    bookCoverImage.setImage(coverImage);
                } else {
                    // tải ảnh từ local
                    File coverImageFile = new File(book.getCoverImagePath());
                    Image coverImage = new Image(coverImageFile.toURI().toString());
                    bookCoverImage.setImage(coverImage);
                }
            } catch (Exception e) {
                // ảnh không hợp lệ, thay bằng ảnh mặc định
                System.err.println("Error loading image: " + e.getMessage());
                bookCoverImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/heart.jpg"))));
            }
        } else {
            // Set a default image if the cover path is null or empty
            InputStream defaultImageStream = getClass().getResourceAsStream("/Image/heart.jpg");
            if (defaultImageStream != null) {
                bookCoverImage.setImage(new Image(defaultImageStream));
            } else {
                System.err.println("Default image not found");
            }
        }
    }

    // thêm sách vào database
    public void addToDatabase() {
        String imageURL = currentBook.getCoverImagePath();
        String localPath = "../Uploaded/BookCovers/" + currentBook.getTitle() + ".jpg";
        File coverImageFile = UpDownFile.downloadFileImage(imageURL,localPath);

        // Pass the cover image file correctly to the `upLoadBook` method
        User currentUser = library.getCurrentUser();
        if (currentUser instanceof Admin) {
            ((Admin) currentUser).upLoadBook(currentBook, null, coverImageFile, null);
        } else {
            System.err.println("Current user is not an admin.");
        }
    }

    // sang trang upload
    public void switchToUpLoad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Upload.fxml"));
            Parent root = loader.load();

            String imageURL = currentBook.getCoverImagePath();
            String localPath = "../Uploaded/BookCovers/" + currentBook.getTitle() + ".jpg";
            File coverImageFile = UpDownFile.downloadFileImage(imageURL,localPath);
            // Get the controller and pass the book data
            PageUploadController uploadController = loader.getController();
            uploadController.setBookData(currentBook);

            MainSceneClass.setMainContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // xem comment khác
    public void changeToSeenComments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/CommentSeenPage.fxml"));
            Parent parent = loader.load();
            CommentController controller = loader.getController();
            controller.setCommentData(currentBook);
            Stage stage = new Stage();
            stage.setTitle("Comments");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}