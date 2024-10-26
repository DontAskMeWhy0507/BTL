package org.example.demo6.Admin;

public class BookUser {
    private int id;
    private String name;
    private String email;
    private String title;
    private String date;

    public BookUser(int id, String name, String email, String title, String date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}