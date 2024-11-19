package org.example.demo6.Classes;

import javafx.event.ActionEvent;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public interface Database {
    void signUp(String id, String username, String password, String email);
    User logIn(String username, String password);
    void saveBookToDatabase(Book book);
    List<Book> getBooksFromDatabase();
    void updateUserInDatabase(User user);
    List<Book> searchBook(String search);
    void BorrowBook (Transaction transaction); // Phương thức này cần được triển khai
    Transaction getTransaction(User user, Book book);
    void returnBook(Transaction transaction);
    Date stringToDate(String dateStr) throws ParseException;
}
