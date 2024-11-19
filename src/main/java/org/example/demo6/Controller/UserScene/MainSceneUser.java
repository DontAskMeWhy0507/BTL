package org.example.demo6.Controller.UserScene;

import com.google.errorprone.annotations.FormatMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.UserScene.Page.SearchPageController;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.demo6.Controller.AdminScene.MainSceneClass.staticMainScrollPane;
import static org.example.demo6.Controller.GeneralController.changescene;

public class MainSceneUser {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField SearchField;

    @FXML
    private Button avatarButton;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox seeMoreProfile;


    @FXML
    private ImageView avatar;

    private static ScrollPane staticMainScrollPane1;

    public static void setMainContent(Parent content) {
        staticMainScrollPane1.setContent(content);
    }



    @FXML
    void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                changescene(event, "/View/LoginScene/Login.fxml", "Login!");
            } catch (Exception e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                }
            }
        }

    }

    @FXML
    void moreButton() {
        seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }

    @FXML
    public void searchButton(ActionEvent event) throws IOException {
        List<Book> ApiResult = apiGoogleBooks.searchBooks1(SearchField.getText());
        DBUltis dbUltis = new DBUltis();
        List<Book> databaseResult = dbUltis.searchBook(SearchField.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/PageSearch.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            SearchPageController searchPageController = loader.getController();
            searchPageController.setSearchResults(databaseResult, ApiResult);

            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeAvatar(String avatarPaths) {
        Image newAvatarImage = new Image(getClass().getResourceAsStream(avatarPaths));
        avatar.setImage(newAvatarImage);
    }

    public void setUser() {
        changeAvatar(Library.getInstance().getCurrentUser().getPathToProfilePicture());
        avatarButton.setText(Library.getInstance().getCurrentUser().getUsername());
    }

    public void showAll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/All.fxml"));
            Parent homeView = loader.load();


            setMainContent(homeView);
            staticMainScrollPane1.setFitToWidth(true);
            staticMainScrollPane1.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {
        setUser();
        staticMainScrollPane1 = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Home.fxml"));
            Parent homeView = loader.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(homeView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void showSettings(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Settings.fxml"));
            Parent SettingView = loader1.load();
            org.example.demo6.Controller.UserScene.Page.Settings settingsController = loader1.getController();

            // Create or get an instance of MainSceneUser
            MainSceneUser mainSceneUserInstance = this;

            // Pass the instance to the setMainSceneController method
            settingsController.setMainSceneController(mainSceneUserInstance);

            // Set the new content in the ScrollPane
            mainScrollPane.setContent(SettingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void changeAdminView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/AdminScene/MainScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }


    public void changeToStreak() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Streak.fxml"));
            Parent StreakView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(StreakView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
