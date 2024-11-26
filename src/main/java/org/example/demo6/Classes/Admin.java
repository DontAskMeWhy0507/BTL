package org.example.demo6.Classes;

import javafx.scene.control.Alert;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Admin extends User {
    private DBUltis dbUltis = new DBUltis();

    public Admin(int id, String username, String password, String email, LocalDate dateOfBirth, String profilePicture, String role, Streak streak) {
        super(id, username, password, email, dateOfBirth,profilePicture, role, streak);
    }

    public void upLoadBook (Book book, File selectedFile, File selectedCover, File selectedAudio) {
        // Thư mục đích để lưu file
        String UPLOAD_DIRECTORY_BOOKS = "Uploaded/Books";
        String UPLOAD_DIRECTORY_AUDIO = "Uploaded/AudioBooks";
        String UPLOAD_DIRECTORY_IMAGE = "Uploaded/BookCovers";

        if (dbUltis.findQuery("SELECT * FROM books WHERE isbn = '" + book.getIsbn() + "'")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book already exists");
            alert.setContentText("The book with the ISBN " + book.getIsbn() + " already exists in the database.");
            alert.showAndWait();
            return;
        }


        // thêm file sách
        if (selectedFile != null) {
            UpDownFile.uploadFile(selectedFile, UPLOAD_DIRECTORY_BOOKS);
            book.setBookPath("Uploaded/Books/" + selectedFile.getName());
        }
        // thêm ảnh bìa
        if (selectedCover != null) {
            UpDownFile.uploadFile(selectedCover, UPLOAD_DIRECTORY_IMAGE);
            book.setCoverImagePath("Uploaded/BookCovers/" + selectedCover.getName());
        }
        if (selectedAudio != null) {
            UpDownFile.uploadFile(selectedAudio, UPLOAD_DIRECTORY_AUDIO);
            book.setAudioPath("Uploaded/AudioBooks/" + selectedAudio.getName());
        }
        DBUltis DBUltis = new DBUltis();
        //
        try {
            DBUltis.saveBookToDatabase(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return dbUltis.getUsers();
    }

    // thêm người dùng
    public void addUsers(User user) {
        dbUltis.loadQuery("INSERT INTO users (username, password, role, email, avatar, last_access, streak, date_of_birth, longest_streak) " +
                "VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getRole() + "', '" +
                user.getEmail() + "', '" + user.getPathToProfilePicture() + "', '" + LocalDate.now().toString() + "', 0, '" +
                user.getDateOfBirth().toString() + "', 0)");

    }

    // xóa người dùng
    public void removeUser(User user) {
        dbUltis.loadQuery("DELETE FROM users WHERE id = " + user.getId());
    }

    // xóa sách
    public void deleteBook(Book book) {
        dbUltis.loadQuery("DELETE FROM books WHERE isbn = " + book.getIsbn());
    }

}

