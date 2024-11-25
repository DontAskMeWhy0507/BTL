package org.example.demo6.Classes;

public class Comment {
    private int id;
    private int postId;
    private int userId;
    private String content;
    private Integer replyToCommentId; // Có thể null
    private String isbn;
    private String timestamp;

    // constructor
    public Comment(int id, int postId, int userId, String content, Integer replyToCommentId, String isbn, String timestamp) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.replyToCommentId = replyToCommentId;
        this.isbn = isbn;
        this.timestamp = timestamp;
    }

    // Getters và Setters
    public int getId() { return id; }
    public int getPostId() { return postId; }
    public int getUserId() { return userId; }
    public String getContent() { return content; }
    public Integer getReplyToCommentId() { return replyToCommentId; }
    public String getIsbn() { return isbn; }
    public String getTimestamp() { return timestamp; }
}

