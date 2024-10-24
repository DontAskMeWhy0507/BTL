package org.example.demo6.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminController {

    @FXML
    private TableColumn<BookUser, Integer> tf_ID;

    @FXML
    private TableColumn<BookUser, String> tf_book;

    @FXML
    private TableColumn<BookUser, String> tf_day;

    @FXML
    private TableColumn<BookUser, String> tf_email;

    @FXML
    private TableColumn<BookUser, String> tf_name;

    @FXML
    private TableView<BookUser> tableView;

    private ObservableList<BookUser> data;

    @FXML
    public void initialize() {
        tf_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tf_book.setCellValueFactory(new PropertyValueFactory<>("title"));
        tf_day.setCellValueFactory(new PropertyValueFactory<>("date"));
        tf_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tf_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        String url = "jdbc:sqlite:database//LibraryMain";
        String query = "SELECT * FROM BooksUser";
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String dateString = rs.getString("date");
                Date parsedDate = inputFormatter.parse(dateString);
                data.add(new BookUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("title"),
                        outputFormatter.format(parsedDate)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}