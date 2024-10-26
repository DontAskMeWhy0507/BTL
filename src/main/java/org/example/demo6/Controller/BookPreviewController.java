// src/main/java/org/example/demo6/Controller/BookPreviewController.java
package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo6.Book;

public class BookPreviewController {

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label bookAuthorLabel;

    @FXML
    private Label bookPublisherLabel;

    @FXML
    private Label bookPublishedDateLabel;

    @FXML
    private Label bookCategoryLabel;

    @FXML
    private Label bookDescriptionLabel;

    @FXML
    private ImageView bookCoverImage;

    public void setBookDetails(Book book) {
        bookTitleLabel.setText(book.getTitle());
        bookAuthorLabel.setText(book.getAuthor());
        bookPublisherLabel.setText(book.getPublisher());
        bookPublishedDateLabel.setText(book.getPublishedDate());
        bookCategoryLabel.setText(book.getCategory());
        bookDescriptionLabel.setText(book.getDescription());

        if (book.getCoverImagePath() != null) {
            bookCoverImage.setImage(new Image(book.getCoverImagePath()));
        } else {
            bookCoverImage.setImage(new Image(getClass().getResourceAsStream("/Image/heart.png")));
        }
    }
}
