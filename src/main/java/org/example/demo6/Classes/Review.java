package org.example.demo6.Classes;

public class Review {
    private String comment;
    private int rating;
    private User user;

    // Constructor
    public Review(String comment, int rating, User user) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
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
}