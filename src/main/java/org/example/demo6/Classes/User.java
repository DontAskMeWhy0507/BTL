package org.example.demo6.Classes;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int id;
    private String password;
    private String email;
    private String pathToProfilePicture;


    public User(String username, int id, String password, String email, String pathToProfilePicture) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPathToProfilePicture() { return pathToProfilePicture; }
    public void setPathToProfilePicture(String pathToProfilePicture) { this.pathToProfilePicture = pathToProfilePicture; }


    // books
    

    public void account() {
        // change password
        // change email
        // change username
        // change profile picture
    }

    public void settings () {
        //  view history books borrowed, books returned, comments, ratings.
        //  change preferences
    }

}
