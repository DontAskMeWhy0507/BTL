package org.example.demo6.Classes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    enum status {
        Borrowed,
        Returned,
        Overdue
    }
    private int id;
    private User user;
    private Book book;
    private LocalDateTime dateBorrowed;
    private LocalDateTime  dueDate;
    private LocalDateTime  dateReturned;
    private status status;

    public Transaction(User user, Book book, LocalDateTime  dateBorrowed, LocalDateTime  dueDate) {
        this.user = user;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = null;
        this.status = status.Borrowed;
    }

    public Transaction(int id, User user, Book book, LocalDateTime  dateBorrowed, LocalDateTime  dueDate, LocalDateTime  dateReturned, status status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = dateReturned;
        this.status = status;
    }

    public void returnBook(LocalDateTime dateReturned) {
        this.dateReturned = dateReturned;
        if (isOverdue()) {
            this.status = status.Overdue;
        } else {
            this.status = status.Returned;
        }
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate);
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

    public LocalDateTime getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(LocalDateTime dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDateTime dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Transaction.status getStatus() {
        return status;
    }

    public void setStatus(Transaction.status status) {
        this.status = status;
    }

}
