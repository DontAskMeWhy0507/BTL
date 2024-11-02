// src/main/java/org/example/demo6/Controller/BookPreviewController.java
package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.FileUpload;
import org.example.demo6.Classes.Library;

import java.io.File;

import static org.example.demo6.Classes.FileUpload.downloadFileImage;

public class BookPreviewController {

    private Book currentBook;


    @FXML
    private Label bookTitleLabel;

    @FXML
    private Text bookAuthor;

    @FXML
    private Text bookPublishedDate;

    @FXML
    private Label bookCategoryLabel;

    @FXML
    private Text bookDescription;

    @FXML
    private ImageView bookCoverImage;


//    public void setBookDetails(Book book) {
//        bookTitle Label.setText(book.getTitle());
//        bookAuthorLabel.setText(book.getAuthor());
//        bookPublisherLabel.setText(book.getPublisher());
//        bookPublishedDateLabel.setText(book.getPublishedDate());
//        bookCategoryLabel.setText(book.getCategory());
//        bookDescriptionLabel.setText(book.getDescription());
//
//        if (book.getCoverImagePath() != null) {
//            bookCoverImage.setImage(new Image(book.getCoverImagePath()));
//        } else {
//            bookCoverImage.setImage(new Image(getClass().getResourceAsStream("/Image/heart.png")));
//        }
//    }



    public void setBookData(Book book) {
        currentBook = book;
        bookTitleLabel.setText(book.getTitle());
        bookDescription.setText(book.getDescription());
        bookAuthor.setText(book.getAuthor());
        bookPublishedDate.setText(book.getPublishedDate());

        // Check if cover image path is available and valid
        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            try {
                bookCoverImage.setImage(new Image(book.getCoverImagePath(), true));  // Use background loading
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
        String localPath = "Uploaded/BookCovers";
        File coverImageFile = FileUpload.downloadFileImage(imageURL,localPath);

        // Pass the cover image file correctly to the `upLoadBook` method
        Library.upLoadBook(currentBook, null, coverImageFile, null);
    }

}

