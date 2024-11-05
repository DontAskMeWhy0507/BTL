package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.demo6.Classes.Book;
import org.example.demo6.Controller.Scene.BookUnitController;
import org.example.demo6.Classes.apiGoogleBooks;

import java.io.IOException;
import java.util.List;

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


        tieuThuyet = bookTieuThuyet();
        try {
            for (int i = 0; i < 10; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/View/Scene/Book.fxml"));
                VBox cardBox = fxmlLoader.load();
                BookUnitController bookUnitController = fxmlLoader.getController();
                bookUnitController.setData(tieuThuyet.get(i));
                cardLayOut1.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        anime = Anime();
        try {
            for (int i = 0; i < 10; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/View/Scene/Book.fxml"));
                VBox cardBox = fxmlLoader.load();
                BookUnitController bookUnitController = fxmlLoader.getController();
                bookUnitController.setData(anime.get(i));
                cardLayOut2.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}