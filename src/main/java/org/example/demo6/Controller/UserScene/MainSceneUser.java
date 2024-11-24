package org.example.demo6.Controller.UserScene;

import com.google.errorprone.annotations.FormatMethod;
import com.jfoenix.controls.JFXSlider;
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
import org.example.demo6.Controller.AdminScene.Page.ChatAIController;
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
    private ProgressIndicator loadingIndicator;

    @FXML
    private ImageView avatar;

    @FXML
    private ImageView sound;

    @FXML
    private Slider volumeSlider;

    private Music music;

    private boolean isMuted;

    public static ScrollPane staticMainScrollPane1;

    @FXML
    public void initialize() {
        setUser();
        staticMainScrollPane1 = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();

        music = Music.getInstance();
        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            music.setVolume(newValue.doubleValue());
        });
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
        String searchQuery = SearchField.getText();

        // Hiển thị trạng thái tải trong khi tìm kiếm
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

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

    public static void setMainContent(Parent content) {
        staticMainScrollPane1.setContent(content);
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
    public void ChatAI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/ChatAI.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            ChatAIController chatAIController = loader.getController();

            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
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

    public void muteSound(ActionEvent event) {
        isMuted = !isMuted;

        if (isMuted) {
            volumeSlider.setValue(0);
            sound.setImage(new Image(String.valueOf(getClass().getResource("/Image/Icon/mute.png"))));
        } else {
            volumeSlider.setValue(50);
            sound.setImage(new Image(String.valueOf(getClass().getResource("/Image/Icon/volume.png"))));
        }
    }
}
