package org.example.demo6.Controller;

import javafx.scene.control.Button;
import org.example.demo6.Book;
import org.example.demo6.DBUltis;
import org.sqlite.core.DB;

import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class AllPageController {

    @FXML
    private GridPane bookGrid;

    private ArrayList<Book> books;

    @FXML
    public void initialize() {
        books = DBUltis.getBooksFromDatabase(); // Fetch books from the database
        loadBooksIntoGrid(); // Populate the GridPane with book buttons
    }

    private void loadBooksIntoGrid() {
        int row = 0;
        int column = 0;

        for (Book book : books) {
            // Create a Button for each book
            Button bookButton = new Button();
            bookButton.setPrefSize(100, 150); // Set preferred size of the button

            // Load the cover image and set it in the Button
            ImageView coverImageView = new ImageView();
            File imageFile = new File(book.getCoverImagePath());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                coverImageView.setImage(image);
                coverImageView.setFitHeight(100); // Adjust as needed
                coverImageView.setFitWidth(70);   // Adjust as needed
                coverImageView.setPreserveRatio(true); // Maintain aspect ratio
            }

            // Add the ImageView to the Button
            bookButton.setGraphic(coverImageView);

            // Add the button to the GridPane
            bookGrid.add(bookButton, column, row);

            // Move to the next cell in the grid
            column++;
            if (column >= 3) { // Assume 3 columns; adjust as needed
                column = 0;
                row++;
            }

            // Optional: Set an action for the button (e.g., open book details)
            bookButton.setOnAction(event -> {
                System.out.println("Book selected: " + book.getTitle());
                // Implement action to display book details or other interactions
            });
        }
    }
}
