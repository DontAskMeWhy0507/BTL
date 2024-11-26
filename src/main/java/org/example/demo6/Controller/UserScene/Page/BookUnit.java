package org.example.demo6.Controller.UserScene.Page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo6.Classes.Book;
import org.example.demo6.Controller.UserScene.MainSceneUser;
import org.example.demo6.Controller.UserScene.Page.BookPreview;

import java.io.File;
import java.io.IOException;

import static org.example.demo6.Controller.UserScene.MainSceneUser.staticMainScrollPane1;


public class BookUnit {
    private Book currentBook;

    @FXML
    private Label authorBook;

    @FXML
    private ImageView imageBook;

    @FXML
    private Label nameBook;

    @FXML
    public void switchToBookDetails(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/BookPreview.fxml"));
            Parent root = fxmlLoader.load();
            // Lấy controller của trang chi tiết
            BookPreview bookPreviewController = fxmlLoader.getController();
            bookPreviewController.setBookData(currentBook);  // Truyền đối tượng sách sang trang chi tiết

            MainSceneUser.setMainContent1(root);
            staticMainScrollPane1.setFitToWidth(true);
            staticMainScrollPane1.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Book book) {
        this.currentBook = book;
        nameBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
        if (book.getCoverImagePath() != null) {
            imageBook.setImage(new Image(book.getCoverImagePath()));
        } else {
            imageBook.setImage(new Image(getClass().getResourceAsStream("/Image/Icon/heart.png")));
        }
    }

    public void setDataAll(Book book) {
        this.currentBook = book;
        nameBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
        if (book.getCoverImagePath() != null) {
            imageBook.setImage(new Image(new File(book.getCoverImagePath()).toURI().toString()));
        } else {
            imageBook.setImage(new Image(getClass().getResourceAsStream("/Image/Icon/heart.png")));
        }
    }
}
