package org.example.demo6.Controller.UserScene.Page;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.UserScene.MainSceneUser;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BookPreview {
    private static final int descriptionLength = 200;

    private Book currentBook;
    private String descriptionTemp;

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
    private Rating ratingBook;

    @FXML
    private TextField commentInputField;

    @FXML
    private VBox commentsContainer;

    @FXML
    public Text fullDescription;

    @FXML
    private Text rateAvg;

    @FXML
    private void toggleDescription() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Description.fxml"));
        Parent parent = loader.load();
        BookPreview controller = loader.getController();
        controller.fullDescription.setText(descriptionTemp);
        Stage stage = new Stage();
        stage.setTitle("Description");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void borrowBook() {
        Library library = Library.getInstance();
        library.getCurrentUser().borrowBook(currentBook);
    }

    public void returnBook() {
        Library library = Library.getInstance();
        library.getCurrentUser().returnBook(currentBook);
    }

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
        rateAvg.setText(String.format("%.1f", avgRating));

        // Check if cover image path is available and valid
        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            try {
                if (book.getCoverImagePath().startsWith("http")) {
                    // Download the image from the URL and set it in the ImageView
                    Image coverImage = new Image(book.getCoverImagePath(), true);  // Use background loading
                    bookCoverImage.setImage(coverImage);
                } else {
                    // Load the image from the local file system
                    File coverImageFile = new File(book.getCoverImagePath());
                    Image coverImage = new Image(coverImageFile.toURI().toString());
                    bookCoverImage.setImage(coverImage);
                }


            } catch (Exception e) {
                // Log or handle error and set a default image in case of an invalid URL
                System.err.println("Error loading image: " + e.getMessage());
                bookCoverImage.setImage(new Image(getClass().getResourceAsStream("/Image/heart.jpg")));
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


    public void postComment() {
        User user = Library.getInstance().getCurrentUser();
        double ratingValue = ratingBook.getRating();
        String comment = commentInputField.getText(); // Replace with actual comment input

        // Create a new Review object
        Review review = new Review(comment, (int) ratingValue, user);

        // Save the review to the database
        DBUltis dbUltis = new DBUltis();
        dbUltis.saveReviewToDatabase(review, currentBook);

        System.out.println("Rating: " + ratingValue);
    }


    public void changeToSeenComments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/CommentSeenPage.fxml"));
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
