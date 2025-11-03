package org.example.demo6.Classes;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.example.demo6.Controller.AdminScene.MainSceneClass;
import org.example.demo6.Controller.GeneralController;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.demo6.Controller.GeneralController.changescene;

public class Library {
    DBUltis dbUltis = new DBUltis();
    private static Library instance = null;

    private Library() {}

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    private static User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    // đăng ký
    public void signUp(ActionEvent event, String id, String username, String password, String email) {
        if (dbUltis.signUp(id, username, password, email)) {
            changescene(event, "/View/LoginScene/Login.fxml", "Login!");
        }
    }

    // đăng nhập
    public static void logIn(ActionEvent event, String username, String password) {
        // Lấy đối tượng Library duy nhất
        Library library = Library.getInstance();
        DBUltis DBUltis = new DBUltis();
        // Đăng nhập và thiết lập người dùng hiện tại
        User loggedInUser = DBUltis.logIn(username, password);
        if (loggedInUser != null) {
            library.setCurrentUser(loggedInUser);  // Thiết lập người dùng hiện tại trong đối tượng Library duy nhất
            currentUser.getStreak().updateStreak();
            if (currentUser.getRole().equals("Admin")) {
                GeneralController.changescene(event, "/View/AdminScene/MainScene.fxml", "Home to Library");
            } else {
                GeneralController.changescene(event, "/View/UserScene/MainSceneUser.fxml", "Home to Library");
            }
        } else {
            // Xử lý nếu đăng nhập thất bại
            System.out.println("Login failed. Please try again.");
        }
    }

    // đăng xuất
    public static void logOut(ActionEvent event) {
        Library library = Library.getInstance();
        if (library.getCurrentUser() == null) {
            return;
        }
        currentUser.getStreak().updateStreak();

        DBUltis dbUltis = new DBUltis();
        dbUltis.updateUserStreak(
            library.getCurrentUser().getId(),
            LocalDate.now(),
            library.getCurrentUser().getStreak().getStreak(),
            library.getCurrentUser().getStreak().getLongestStreak()
        );

        library.setCurrentUser(null);
        if (event == null) {
            return;
        }
        GeneralController.changescene(event, "/View/LoginScene/Login.fxml", "Login");
    }
}
