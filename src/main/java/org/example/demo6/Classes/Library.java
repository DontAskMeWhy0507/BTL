package org.example.demo6.Classes;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import org.example.demo6.Controller.AdminScene.MainSceneClass;
import org.example.demo6.Controller.GeneralController;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Library {
    DBUltis DBUltis = new DBUltis();
    private static Library instance = null;

    private Library() {}

    public static Library getInstance() {
        return (instance == null) ? new Library() : instance;
    }

    // Current user (admin or user)
    private static User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }


    public static void logIn(ActionEvent event, String username, String password) {
        // Lấy đối tượng Library duy nhất
        Library library = Library.getInstance();
        DBUltis DBUltis = new DBUltis();
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
        DBUltis DBUltis = new DBUltis();
        //
        try {
            DBUltis.saveBookToDatabase(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(Book book) {
        LocalDateTime dateBorrowed = LocalDateTime.now();
        LocalDateTime dueDate = dateBorrowed.plusDays(14);  // Mượn sách trong 14 ngày

        // Tạo một giao dịch mới
        Transaction transaction = new Transaction(currentUser, book, dateBorrowed, dueDate);
        try {
            DBUltis.BorrowBook(transaction);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void returnBook(Book Book) {
        // Tìm giao dịch mà người dùng đã mượn
        Transaction transaction = DBUltis.getTransaction(currentUser, Book);
        if (transaction != null) {
            transaction.returnBook(LocalDateTime.now());
            try {
                DBUltis.returnBook(transaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
