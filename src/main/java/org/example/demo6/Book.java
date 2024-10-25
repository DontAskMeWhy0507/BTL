package org.example.demo6;

public class Book {
    private String isbn;
    private String title;              // Tên sách
    private String author;             // Tác giả
    private String category;           // Thể loại
    private String description;        // Mô tả
    private String language;           // Ngôn ngữ
    private String publisher;          // Nhà xuất bản
    private String publishedDate;      // Ngày xuất bản
    private String coverImagePath;     // Đường dẫn đến ảnh bìa
    private String audioPath;          // Đường dẫn đến file audio

    // Constructor
    public Book(String isbn, String title, String author, String category, String description,
                String language, String publisher, String publishedDate,
                String coverImagePath, String audioPath) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.coverImagePath = coverImagePath;
        this.audioPath = audioPath;      // Có thể là null nếu không có
    }

    // Getters và Setters

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

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

    public String getAudioPath() { return audioPath; }
    public void setAudioPath(String audioPath) { this.audioPath = audioPath; }
}


