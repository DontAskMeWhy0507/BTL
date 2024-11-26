package org.example.demo6.Controller.UserScene.Page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;

import java.io.IOException;
import java.util.List;

public class AllUser {
    DBUltis DBUltis = new DBUltis();

    @FXML
    private GridPane bookGrid;

    private List<Book> books;

    @FXML
    public void initialize() {


        books = DBUltis.searchBookByTransaction("SELECT books.* FROM books " +
                "LEFT JOIN BookTransaction ON books.isbn = BookTransaction.book_id " +
                "WHERE BookTransaction.user_id = " + Library.getInstance().getCurrentUser().getId() + " " +
                "AND BookTransaction.status = 'Borrowed' " +
                "GROUP BY books.isbn"
        );
        loadBooksIntoGrid();
    }

    // thêm sách vào bảng
    private void loadBooksIntoGrid() {
        int columns = 7;
        int rows = 6;
        int bookCount = 0;

        // Check if the books list is empty
        if (books == null || books.isEmpty()) {
            System.out.println("No books available to display.");
            return;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bookCount >= books.size()) {
                    return;
                }

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Book.fxml"));
                    Pane bookPane = loader.load();

                    BookUnit controller = loader.getController();
                    controller.setDataAll(books.get(bookCount));

                    bookGrid.add(bookPane, col, row);
                    bookCount++;
                } catch (IOException e) {
                    System.err.println("Failed to load book at index " + bookCount + ": " + e.getMessage());
                }
            }
        }
    }
}