package org.example.demo6.Classes;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import org.example.demo6.Controller.AdminScene.MainSceneClass;
import org.example.demo6.Controller.GeneralController;

import java.io.File;
import java.time.LocalDate;

public class Library {
    private static Library instance = null;

    private Library() {}

    public static Library getInstance() {
        return (instance == null) ? new Library() : instance;
    }

    // Current user (admin or user)
    private User currentUser;

    // Non-static setCurrentUser method
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Getter for currentUser
    public User getCurrentUser() {
        return this.currentUser;
    }


    public static void logIn(ActionEvent event, String username, String password) {
        // Lấy đối tượng Library duy nhất
        Library library = Library.getInstance();

        // Đăng nhập và thiết lập người dùng hiện tại
        User loggedInUser = DBUltis.logIn(event, username, password);
        if (loggedInUser != null) {
            library.setCurrentUser(loggedInUser);  // Thiết lập người dùng hiện tại trong đối tượng Library duy nhất
            System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!");
            GeneralController.changescene(event, "/View/AdminScene/MainScene.fxml", "Home to Library");
        } else {
            // Xử lý nếu đăng nhập thất bại
            System.out.println("Login failed. Please try again.");
        }
    }

    public static void logOut(ActionEvent event) {
        Library library = Library.getInstance();
        library.setCurrentUser(null);
        GeneralController.changescene(event, "/View/LoginScene/Login.fxml", "Login");
    }






    public static void upLoadBook (Book book, File selectedFile, File selectedCover, File selectedAudio) {
        // Thư mục đích để lưu file
        String UPLOAD_DIRECTORY_BOOKS = "Uploaded/Books";
        String UPLOAD_DIRECTORY_AUDIO = "Uploaded/AudioBooks";
        String UPLOAD_DIRECTORY_IMAGE = "Uploaded/BookCovers";


        // Upload the book file
        if (selectedFile != null) {
            UpDownFile.uploadFile(selectedFile, UPLOAD_DIRECTORY_BOOKS);
            book.setBookPath("Uploaded/Books/" + selectedFile.getName());
        }
        // Upload the cover image
        if (selectedCover != null) {
            UpDownFile.uploadFile(selectedCover, UPLOAD_DIRECTORY_IMAGE);
            book.setCoverImagePath("Uploaded/BookCovers/" + selectedCover.getName());
        }
        if (selectedAudio != null) {
            UpDownFile.uploadFile(selectedAudio, UPLOAD_DIRECTORY_AUDIO);
            book.setAudioPath("Uploaded/AudioBooks/" + selectedAudio.getName());
        }

        //
        try {
            DBUltis.saveBookToDatabase(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook (User user, Book book) {
        Transaction transaction = new Transaction(user, book, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    public void returnBook (Transaction transaction) {
        transaction.returnBook(LocalDate.now());
    }








}
