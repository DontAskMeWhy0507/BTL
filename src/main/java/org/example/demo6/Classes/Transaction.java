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
    private LocalDate dateBorrowed;
    private LocalDate  dueDate;
    private LocalDate  dateReturned;
    private status status;

    public Transaction(User user, Book book, LocalDate  dateBorrowed, LocalDate  dueDate) {
        this.user = user;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = null;
        this.status = status.Borrowed;
    }

    public Transaction(int id, User user, Book book, LocalDate  dateBorrowed, LocalDate  dueDate, LocalDate  dateReturned, status status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = dateReturned;
        this.status = status;
    }

    public void returnBook(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
        if (isOverdue()) {
            this.status = status.Overdue;
        } else {
            this.status = status.Returned;
        }
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
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

    public Transaction.status getStatus() {
        return status;
    }

    public void setStatus(Transaction.status status) {
        this.status = status;
    }

}
