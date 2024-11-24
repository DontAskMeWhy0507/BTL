package org.example.demo6.Classes;

import java.sql.Timestamp;

public class Review {
    private int id;
    private String comment;
    private int rating;
    private int userid;
    private String isbn;
    private Timestamp timestamp;
    private String userAvatar;
    private int reactionCount;
    private String commentType;
    private String imageUrl;
    private User user;

    public Review(int id, String comment, int rating, int userid, String isbn, Timestamp timestamp,
                  String userAvatar, int reactionCount, String commentType, String imageUrl) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.userid = userid;
        this.isbn = isbn;
        this.timestamp = timestamp;
        this.userAvatar = userAvatar;
        this.reactionCount = reactionCount;
        this.commentType = commentType;
        this.imageUrl = imageUrl;
    }

    public Review(String comment, int ratingValue, User user) {
        this.comment = comment;
        this.rating = ratingValue;
        this.user = user;
    }

    public Review(int id, int rating) {
        this.id = id;
        this.rating = rating;
    }

    // Getters and Setters (bỏ qua để tiết kiệm không gian)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getReactionCount() {
        return reactionCount;
    }

    public void setReactionCount(int reactionCount) {
        this.reactionCount = reactionCount;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
