// src/main/java/org/example/demo6/Controller/BookUnitController.java
package org.example.demo6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo6.Book;

import java.io.IOException;

public class BookUnitController extends Node {

    @FXML
    private Label authorBook;

    @FXML
    private Button buttonFavorite;

    @FXML
    private ImageView heartImage;

    @FXML
    private ImageView imageBook;

    @FXML
    private Label nameBook;

    private boolean isFavorite = false;

    @FXML
    public void favorite(ActionEvent event) {
        if (isFavorite) {
            Image heart = new Image(getClass().getResourceAsStream("/Image/heartnone.png"));
            heartImage.setImage(heart);
            isFavorite = false;
        } else {
            Image heart = new Image(getClass().getResourceAsStream("/Image/heart.png"));
            heartImage.setImage(heart);
            isFavorite = true;
        }
    }

    public void setData(Book book) {
        nameBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
        if (book.getCoverImagePath() != null) {
            imageBook.setImage(new Image(book.getCoverImagePath()));
        } else {
            imageBook.setImage(new Image(getClass().getResourceAsStream("/Image/heart.png")));
        }
    }

    public void switchToBookDetails(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/BookPreview.fxml"));
            Parent root = fxmlLoader.load();
            MainSceneClass.setMainContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}