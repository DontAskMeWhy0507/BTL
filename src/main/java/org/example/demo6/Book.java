package org.example.demo6;



import java.util.ArrayList;

public class Book {
    private String title;
    private String author;
    private String category;
    private String description;
    private String language;
    private String publisher;
    private String publishedDate;
    private String coverImagePath; // Đường dẫn đến ảnh bìa

    // Constructor
    public Book(String title, String author, String category, String description, String language, String publisher, String publishedDate, String coverImagePath) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.coverImagePath = coverImagePath;
    }

    // Getters và Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getCoverImagePath() { return coverImagePath; }
    public void setCoverImagePath(String coverImagePath) { this.coverImagePath = coverImagePath; }
}

