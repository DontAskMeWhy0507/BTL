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
        books =  dbUltis.searchBookByTransaction("SELECT * FROM books ORDER BY published_date DESC LIMIT 10");
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).getTitle());
        }
    }
}

