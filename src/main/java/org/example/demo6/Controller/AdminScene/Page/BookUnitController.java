package org.example.demo6.Controller.AdminScene.Page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo6.Classes.Book;
import org.example.demo6.Controller.AdminScene.Page.BookPreviewController;
import org.example.demo6.Controller.AdminScene.MainSceneClass;

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
    private Book currentBook;

    private boolean isFavorite = false;






    public void setData(Book book) {
        this.currentBook = book;
        nameBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
        if (book.getCoverImagePath() != null) {
            imageBook.setImage(new Image(book.getCoverImagePath()));
        } else {
            imageBook.setImage(new Image(getClass().getResourceAsStream("/Image/heart.png")));
        }
    }



    @FXML
    public void switchToBookDetails(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/BookPreview.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy controller của trang chi tiết
            BookPreviewController bookPreviewController = fxmlLoader.getController();
            bookPreviewController.setBookData(currentBook);  // Truyền đối tượng sách sang trang chi tiết

            MainSceneClass.setMainContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}