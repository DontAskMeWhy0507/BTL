package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;

import java.io.IOException;
import java.sql.*;

public class BookManageTableController {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, Integer> tf_ISBN;

    @FXML
    private TableColumn<Book, String> tf_Title;

    @FXML
    private TableColumn<Book, String> tf_author;

    @FXML
    private TableColumn<Book, String> tf_category;

    @FXML
    private TableColumn<Book, String> tf_language;

    @FXML
    private TableColumn<Book, String> tf_publisher;

    @FXML
    private TableColumn<Book, Integer> tf_quantity;

    @FXML
    private TextField tf_findBook;

    private ObservableList<Book> data;

    @FXML
    public void initialize() {
        tf_ISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tf_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tf_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        tf_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        tf_language.setCellValueFactory(new PropertyValueFactory<>("language"));
        tf_publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        tf_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        String url = "jdbc:sqlite:database//LibraryMain";
        String query = "SELECT * FROM BOOKS";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                data.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Upload.fxml"));
            Parent uploadView = loader.load();

            PageUploadController pageUploadController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Upload");
            stage.setScene(new Scene(uploadView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            String url = "jdbc:sqlite:database//LibraryMain";
            String query = "DELETE FROM BOOKS WHERE ISBN = '" + selectedBook.getIsbn() + "'";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(query);
                data.remove(selectedBook);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadData(ActionEvent event) {
        data.clear();
        loadDataFromDatabase();
    }

    private void searchBookDatabase(String query) {
        String url = "jdbc:sqlite:database//LibraryMain";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + tf_findBook.getText() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchBookDatabase1() {
        data.clear();
        String query = "SELECT * FROM BOOKS WHERE TITLE LIKE ?";

        searchBookDatabase(query);
    }


    public void enterToSeachBookData() {
        tf_findBook.setOnAction(event -> searchBookDatabase1());
    }
}