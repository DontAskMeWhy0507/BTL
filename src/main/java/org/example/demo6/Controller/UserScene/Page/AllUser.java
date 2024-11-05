package org.example.demo6.Controller.UserScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Controller.Scene.BookUnitController;

import java.io.IOException;
import java.util.List;

public class AllUser {

    @FXML
    private GridPane bookGrid; // Ensure this matches the fx:id in FXML

    private List<Book> books;

    @FXML
    public void initialize() {
        // Fetch books from the database
        books = DBUltis.getBooksFromDatabase();
        // Populate the GridPane with book buttons
        loadBooksIntoGrid();
    }

    private void loadBooksIntoGrid() {
        int columns = 7;
        int rows = 6;
        int bookCount = 0;

        // Check if the books list is empty
        if (books == null || books.isEmpty()) {
            System.out.println("No books available to display.");
            return;
        }

        // Load books into the grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= books.size()) {
                    return; // Exit both loops if no more books
                }

                try {
                    // Load the FXML for the book pane
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Scene/Book.fxml"));
                    Pane bookPane = loader.load();

                    // Get the controller and set the data for the book
                    BookUnitController controller = loader.getController();
                    controller.setDataAll(books.get(bookCount));

                    // Add the book pane to the grid
                    bookGrid.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    // Log the error with book count for better debugging
                    System.err.println("Failed to load book at index " + bookCount + ": " + e.getMessage());
                }
            }
        }
    }

}