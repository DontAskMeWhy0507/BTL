package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.example.demo6.Classes.Book;
import org.example.demo6.DBUltis;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AllPageController {

    @FXML
    private GridPane bookGrid; // Ensure this matches the fx:id in FXML

    private ArrayList<Book> books;

    @FXML
    public void initialize() {
        // Fetch books from the database
        books = DBUltis.getBooksFromDatabase();
        // Populate the GridPane with book buttons
        loadBooksIntoGrid();
        System.out.println("AllPageController initialized");
    }

    private void loadBooksIntoGrid() {
        int row = 0;
        int column = 0;

        for (Book book : books) {
            Button bookButton = new Button();
            bookButton.setPrefSize(100, 150); // Set preferred size of the button

            // Load the cover image and set it in the Button
            ImageView coverImageView = new ImageView();
            String correctedPath = "@../../" + book.getCoverImagePath();
            File imageFile = new File(correctedPath);

            System.out.println("Loading image from: " + imageFile.getAbsolutePath());


            if (imageFile.exists()) {
                // Use a try-catch to handle possible Image loading issues
                try {
                    Image image = new Image(imageFile.toURI().toString());
                    coverImageView.setImage(image);
                    coverImageView.setFitHeight(100);
                    coverImageView.setFitWidth(70);
                    //coverImageView.setPreserveRatio(true);
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + imageFile.getPath());
                }
            } else {
                System.err.println("Image file does not exist: " + imageFile.getPath());
            }

            // Add the ImageView to the Button
            bookButton.setGraphic(coverImageView);
            bookGrid.add(bookButton, column, row); // Add the button to the GridPane
            column++;
            if (column >= 5) { // Assuming 5 columns
                column = 0;
                row++;
            }

            // Optional: Set an action for the button
            bookButton.setOnAction(event -> {
                System.out.println("Book selected: " + book.getTitle());
                // Implement additional actions if needed
            });
        }
    }
}
