package org.example.demo6;

import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PathCheck {
    public static void main(String[] args) {

        List<Book> books = DBUltis.searchBook("Harry");
        for (Book book : books) {
            // show bookunit.fxml

        }

    }
}
