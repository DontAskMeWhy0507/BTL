package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;

import java.io.IOException;
import java.util.List;

public class SearchPageController {

    @FXML
    private GridPane bookContainer;

    private List<Book> searchResults;

    public void setSearchResults(List<Book> searchResults) {
        this.searchResults = searchResults;
        displaySearchResults();
    }

    private void displaySearchResults() {
        bookContainer.getChildren().clear();
        int columns = 7;
        int rows = 6;
        int bookCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= searchResults.size()) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnitController1 controller = loader.getController();
                    controller.setData(searchResults.get(bookCount));
                    bookContainer.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}