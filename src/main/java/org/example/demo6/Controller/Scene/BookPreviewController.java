// src/main/java/org/example/demo6/Controller/BookPreviewController.java
package org.example.demo6.Controller.Scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.UpDownFile;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.UpDownFile;
import org.example.demo6.Controller.AdminScene.Page.PageUploadController;

import java.io.File;
import java.io.IOException;


public class BookPreviewController {

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


    public void postComment() {
        // Add the comment to the current book
        // currentBook.addComment(commentTextArea.getText());
        // Update the book in the database
        // Library.updateBook(currentBook);
    }

    public void setBookData(Book book) {
        currentBook = book;
        bookTitleLabel.setText(book.getTitle());
        bookDescription.setText(book.getDescription());
        bookAuthor.setText(book.getAuthor());
        bookPublishedDate.setText(book.getPublishedDate());
        bookCategoryLabel.setText(book.getCategory());
        bookPublisher.setText(book.getPublisher());
        bookLanguage.setText(book.getLanguage());

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
                bookCoverImage.setImage(new Image(getClass().getResourceAsStream("/Image/UET.jpg")));
            }
        } else {
            // Set a default image if the cover path is null or empty
            bookCoverImage.setImage(new Image(getClass().getResourceAsStream("/Image/UET.jpg")));
        }
    }

    public void addToDatabase() {
        String imageURL = currentBook.getCoverImagePath();
        String localPath = "/Uploaded/BookCovers" + currentBook.getTitle() + ".jpg";
        File coverImageFile = UpDownFile.downloadFileImage(imageURL,localPath);

        // Pass the cover image file correctly to the `upLoadBook` method
        Library.upLoadBook(currentBook, null, coverImageFile, null);
    }

    public void switchToUpLoad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Upload.fxml"));
            Parent uploadView = loader.load();

            // Get the controller and pass the book data
            PageUploadController uploadController = loader.getController();
            uploadController.setBookData(currentBook);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(uploadView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



