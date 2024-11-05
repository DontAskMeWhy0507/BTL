package org.example.demo6.Classes;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private int id;
    private User user;
    private Book book;
    private LocalDate  dateBorrowed;
    private LocalDate  dueDate;
    private LocalDate  dateReturned;
    private boolean isReturned;

    public Transaction(User user, Book book, LocalDate  dateBorrowed, LocalDate  dueDate) {
        this.user = user;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = null;
        this.isReturned = false;
    }

    public void returnBook(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
        this.isReturned = true;
    }

    public boolean isOverdue() {
        return Objects.requireNonNullElseGet(dateReturned, LocalDate::now).isAfter(dueDate);
    }

    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(LocalDate dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
