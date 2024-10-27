package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class SearchPageController {

    @FXML
    private GridPane bookContainer;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    public void initialize() {
        int columns = 7;
        int rows = 6;
        int bookCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= 20) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Book.fxml"));
                    Pane bookPane = loader.load();
                    BookUnitController controller = loader.getController();
                    bookContainer.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}