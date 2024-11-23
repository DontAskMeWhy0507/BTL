package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchPageController {

    @FXML
    private GridPane databaseResults;

    @FXML
    private GridPane apiResults;

    protected List<Book> databaseSearchResults;
    protected List<Book> apiSearchResults;

    private final ExecutorService executor = Executors.newFixedThreadPool(2); // Tạo thread pool với 2 luồng

    // Set search results and display them concurrently
    public void setSearchResults(List<Book> databaseResults, List<Book> apiResults) {
        this.databaseSearchResults = databaseResults;
        this.apiSearchResults = apiResults;

        // Task for displaying database results
        Task<Void> displayDatabaseTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayDatabaseResults();
                return null;
            }
        };

        // Task for displaying API results
        Task<Void> displayApiTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                displayApiResults();
                return null;
            }
        };

        // Submit tasks to executor
        executor.submit(displayDatabaseTask);
        executor.submit(displayApiTask);
    }

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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnitController controller = loader.getController();
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

    private void displayApiResults() {
        Platform.runLater(() -> apiResults.getChildren().clear());
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

        if (apiSearchResults == null || apiSearchResults.isEmpty()) {
            Platform.runLater(() -> System.out.println("No books available from the API."));
            return;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= apiSearchResults.size()) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnitController controller = loader.getController();
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

    public void shutdownExecutor() {
        executor.shutdown();
    }
}
