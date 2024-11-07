package org.example.demo6.Controller.AdminScene.Page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo6.Classes.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
}