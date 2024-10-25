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
    private List<Book> topBooks;

    private List<Book> topBooks() {
        try {
            return apiGoogleBooks.searchBooks1("bestsellers");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void initialize() {
        topBooks = topBooks();
        try {
            for (int i = 0; i < 10; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/View/Book.fxml"));
                VBox cardBox = fxmlLoader.load();
                BookUnitController bookUnitController = fxmlLoader.getController();
                bookUnitController.setData(topBooks.get(i));
                cardLayOut.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}