package org.example.demo6.Classes;
public class Admin extends User {
    public Admin(String username, int id, String password, String email, String pathToProfilePicture) {
        super(username, id, password, email, pathToProfilePicture);
    }

    public void searchBook() {

    }

    public void viewUsers() {

    }

    public void addBook() {

    }

    public void removeBook() {

    }

    public void updateBook() {

    }

    @Override
    public void settings () {
        //  view history books borrowed, books returned, comments, ratings.
        //  change preferences
        //  add admin
        //  remove admin
    }



}

