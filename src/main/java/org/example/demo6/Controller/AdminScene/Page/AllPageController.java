package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;

import java.io.IOException;
import java.util.List;

public class AllPageController {
    DBUltis DBUltis = new DBUltis();

    @FXML
    private GridPane bookGrid;

    private List<Book> books;

    @FXML
    public void initialize() {
        // lấy sách từ database
        books = DBUltis.getBooksFromDatabase();
        // thêm sách vào bảng
        loadBooksIntoGrid();
    }

    // thêm sách vào bảng
    private void loadBooksIntoGrid() {
        int columns = 7;
        int rows = 6;
        int bookCount = 0;

        // bảng không có sách
        if (books == null || books.isEmpty()) {
            System.out.println("No books available to display.");
            return;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= books.size()) {
                    return; // không còn sách
                }

                try {
                    // FXML sách
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();

                    // controller sách
                    BookUnitController controller = loader.getController();
                    controller.setDataAll(books.get(bookCount));

                    // thêm sách
                    bookGrid.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    System.err.println("Failed to load book at index " + bookCount + ": " + e.getMessage());
                }
            }
        }
    }

}

