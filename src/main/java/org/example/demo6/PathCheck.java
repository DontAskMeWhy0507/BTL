package org.example.demo6;

import org.example.demo6.Classes.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.application.Application;
import javafx.concurrent.Task ;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
public class PathCheck  {

    public static void main(String[] args) {
        List<Book> books;
        DBUltis dbUltis = new DBUltis();
        books = dbUltis.searchBookByTransaction("SELECT books.* FROM books " +
                "LEFT JOIN BookTransaction ON books.isbn = BookTransaction.book_id " +
                "WHERE BookTransaction.user_id = 125 " +
                "AND BookTransaction.status = 'Borrowed' " +
                "GROUP BY books.isbn"
        );
        while (!books.isEmpty()) {
            System.out.println("Book: " + books.get(0).getTitle());
        }
    }
}

