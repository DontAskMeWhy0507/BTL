package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.demo6.Book;
import org.example.demo6.apiGoogleBooks;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController {

    @FXML
    private HBox cardLayOut;
    @FXML
    private HBox cardLayOut1;
    @FXML
    private HBox cardLayOut2;
    private List<Book> topBooks;
    private List<Book> tieuThuyet;
    private List<Book> anime;

    private List<Book> topBooks() {
        try {
            return apiGoogleBooks.searchBooks1("bestsellers");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<Book> bookTieuThuyet() {
        try {
            return apiGoogleBooks.searchBooks1("Tiểu thuyết");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    private List<Book> Anime() {
        try {
            return apiGoogleBooks.searchBooks1("Anime");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    public void initialize() {
        topBooks = topBooks();
        loadBooksToLayout(cardLayOut, topBooks);

        tieuThuyet = bookTieuThuyet();
        loadBooksToLayout(cardLayOut1, tieuThuyet);

        anime = Anime();
        loadBooksToLayout(cardLayOut2, anime);
    }

    private void loadBooksToLayout(HBox layout, List<Book> books) {
        try {
            for (int i = 0; i < Math.min(books.size(), 10); i++) { // Kiểm tra số lượng sách
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/View/Book.fxml"));
                VBox cardBox = fxmlLoader.load();
                BookUnitController bookUnitController = fxmlLoader.getController();
                bookUnitController.setData(books.get(i));

                // Declare a final variable for the current book
                final Book currentBook = books.get(i);

                // Add the click event to the cardBox
                cardBox.setOnMouseClicked(event -> {
                    // Open book preview with the final variable
                });

                layout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}