package org.example.demo6.Controller.UserScene.Page;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.apiGoogleBooks;
import org.example.demo6.Controller.UserScene.Page.BookUnit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePageUser {
    DBUltis dbUltis = new DBUltis();

    @FXML
    private HBox cardLayOut1;
    @FXML
    private GridPane cardLayOut2;
    private List<Book> topBooks;
    private List<Book> newestBooks;

    private final ExecutorService executor2 = Executors.newFixedThreadPool(2); // Tạo thread pool với 2 luồng

    private List<Book> topBooks() {
        try {
            return apiGoogleBooks.searchBooks1("bestsellers");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Book> newestBooks() {
        try {
            return dbUltis.searchBookByTransaction("SELECT * FROM books ORDER BY published_date DESC LIMIT 10");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void displayTopBooks() {
        Platform.runLater(() -> cardLayOut2.getChildren().clear());
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

        if (topBooks != null) {

                for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    if (bookCount >= topBooks.size()) {
                        break;
                    }
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Book.fxml"));
                        Pane bookPane = fxmlLoader.load();
                        BookUnit bookUnitController = fxmlLoader.getController();
                        bookUnitController.setData(topBooks.get(bookCount));

                        int finalCol = column;
                        int finalRow = row;

                        Platform.runLater(() -> cardLayOut2.add(bookPane, finalCol, finalRow));
                        bookCount++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void displayNewestBooks() {
        Platform.runLater(() -> cardLayOut1.getChildren().clear());
        if (newestBooks != null) {
            for (int i = 0; i < 10; i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/View/UserScene/Page/Book.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    BookUnit bookUnitController = fxmlLoader.getController();
                    bookUnitController.setDataAll(newestBooks.get(i));
                    cardLayOut1.getChildren().add(cardBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    public void initialize() {
        newestBooks = newestBooks();
        topBooks = topBooks();
        Task <Void> displayTopBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayTopBooks();
                return null;
            }
        };

        Task <Void> displayNewestBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayNewestBooks();
                return null;
            }
        };

        executor2.submit(displayTopBooksTask);
        executor2.submit(displayNewestBooksTask);
        }
}