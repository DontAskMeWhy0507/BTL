package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;

import java.io.IOException;
import java.util.List;

public class SearchPageController {

    @FXML
    private GridPane databaseResults;  // GridPane for database results

    @FXML
    private GridPane apiResults;  // GridPane for API results

    private List<Book> databaseSearchResults;
    private List<Book> apiSearchResults;

    // Set the search results for both database and API
    public void setSearchResults(List<Book> databaseResults, List<Book> apiResults) {
        this.databaseSearchResults = databaseResults;
        this.apiSearchResults = apiResults;
        displayDatabaseResults();
        displayApiResults();
    }

    // Display the database results in the first GridPane
    private void displayDatabaseResults() {
        databaseResults.getChildren().clear();
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

        if (databaseSearchResults == null || databaseSearchResults.isEmpty()) {
            System.out.println("No books available to display.");
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
                    // setDataAll is a method for book from database
                    controller.setDataAll(databaseSearchResults.get(bookCount));
                    databaseResults.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Display the API results in the second GridPane
    private void displayApiResults() {
        apiResults.getChildren().clear();
        int columns = 6;
        int rows = 6;
        int bookCount = 0;

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
                    apiResults.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
