package org.example.demo6.Classes;

import java.sql.Timestamp;

public class Review {
    private String comment;
    private int rating;
    private User user;
    private Book currentbook;

    // Constructor
    public Review(String comment, int rating, User user) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
    }

    public Review(String comment, int rating, User user, Book currentbook) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.currentbook = currentbook;
    }

    public Review(String comment, int rating, int userId, String isbn, Timestamp timestamp) {
        this.comment = comment;
        this.rating = rating;
        this.user = new User(userId);
        this.currentbook = new Book(isbn);
    }

    // Getters and Setters
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return currentbook;
    }

    public void setBook(Book book) {
        this.currentbook = book;
    }



}