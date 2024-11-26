package org.example.demo6.Controller.UserScene.Page;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchPageController extends org.example.demo6.Controller.AdminScene.Page.SearchPageController {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private List<Book> databaseSearchResults;
    private List<Book> apiSearchResults;

    @FXML
    private GridPane databaseResults;  // GridPane for database results

    @FXML
    private GridPane apiResults;  // GridPane for API results

    // Create a thread pool with 2 threads

    // Set the search results for both database and API
    public void setSearchResults(List<Book> databaseResults, List<Book> apiResults) {
        this.databaseSearchResults = databaseResults;
        this.apiSearchResults = apiResults;
        displayDatabaseResults();
        displayApiResults();
    }

    // Display the database results in the first GridPane
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

    // Display the API results in the second GridPane
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
