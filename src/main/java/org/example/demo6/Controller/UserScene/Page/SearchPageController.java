package org.example.demo6.Controller.UserScene.Page;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchPageController {

    @FXML
    private GridPane databaseResults;  // GridPane for database results

    @FXML
    private GridPane apiResults;  // GridPane for API results

    private List<Book> databaseSearchResults;
    private List<Book> apiSearchResults;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);  // Create a thread pool with 2 threads

    /**
     * hiển thị kết quả tra cứu
     * @param databaseResults từ database
     * @param apiResults từ api
     */
    public void setSearchResults(List<Book> databaseResults, List<Book> apiResults) {
        this.databaseSearchResults = databaseResults;
        this.apiSearchResults = apiResults;

        Task<Void> displayDatabaseTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayDatabaseResults();
                return null;
            }
        };

        Task<Void> displayApiTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayApiResults();
                return null;
            }
        };

        executor.submit(displayDatabaseTask);
        executor.submit(displayApiTask);
    }

    /**
     * hiển thị kết quả từ database
     */
    private void displayDatabaseResults() {
        Platform.runLater(() -> databaseResults.getChildren().clear());
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

            if (databaseSearchResults == null || databaseSearchResults.isEmpty()) {
                Platform.runLater(() -> System.out.println("No books available in the database."));
                return;
            }



        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= databaseSearchResults.size()) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnit controller = loader.getController();
                    // setDataAll is a method for book from database
                    controller.setDataAll(databaseSearchResults.get(bookCount));


                    int finalCol = col;
                    int finalRow = row;
                    Platform.runLater(() -> databaseResults.add(bookPane, finalCol, finalRow));
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * hiển thị kết quả từ api
     */
    private void displayApiResults() {
        Platform.runLater(() -> apiResults.getChildren().clear());
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= apiSearchResults.size()) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnit controller = loader.getController();
                    controller.setData(apiSearchResults.get(bookCount));

                    int finalCol = col;
                    int finalRow = row;
                    Platform.runLater(() -> apiResults.add(bookPane, finalCol, finalRow));
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
