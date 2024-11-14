package org.example.demo6.Controller.AdminScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.AdminScene.Page.SearchPageController;
import javafx.scene.image.Image;
import org.example.demo6.Controller.AdminScene.Page.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.demo6.Controller.GeneralController.changescene;

public class MainSceneClass{
    DBUltis DBUltis = new DBUltis();
    // Singleton
    private static MainSceneClass instance;

    public static MainSceneClass getInstance() {
        if (instance == null) {
            instance = new MainSceneClass();
        }
        return instance;
    }

    @FXML
    private TextField SearchField;

    @FXML
    private VBox seeMoreProfile;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private Button avatarButton;

    @FXML
    private Label userName;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView avatar;

    // Method to update the avatar
    public void changeAvatar(String avatarPaths) {
        Image newAvatarImage = new Image(getClass().getResourceAsStream(avatarPaths));
        avatar.setImage(newAvatarImage);
    }



    public static ScrollPane staticMainScrollPane;
    @FXML
    void searchButton(ActionEvent event) throws IOException {
        List<Book> ApiResult = apiGoogleBooks.searchBooks1(SearchField.getText());
        List<Book> databaseResult = DBUltis.searchBook(SearchField.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/PageSearch.fxml"));
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
        staticMainScrollPane.setContent(content);
    }



    @FXML
    void moreButton() {
       seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }

//    public void resetView() {
//        changeAvatar(Library.getInstance().getCurrentUser().getPathToProfilePicture());
//        avatarButton.setText(Library.getInstance().getCurrentUser().getUsername());
//    }

    public void setUser() {
        changeAvatar(Library.getInstance().getCurrentUser().getPathToProfilePicture());
        avatarButton.setText(Library.getInstance().getCurrentUser().getUsername());
    }



    @FXML
    public void initialize() {
        setUser();
        staticMainScrollPane = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/MemberTable.fxml"));
            Parent homeView = loader.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(homeView);
            staticMainScrollPane = mainScrollPane;
            mainScrollPane.setFitToWidth(true);
            mainScrollPane.setFitToHeight(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/BookManageTable.fxml"));
            Parent homeView = loader.load();


            mainScrollPane.setContent(homeView);
            staticMainScrollPane = mainScrollPane;
            mainScrollPane.setFitToWidth(true);
            mainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showUpload() {
        try {
            System.out.println("Upload");
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Upload.fxml"));
            Parent UploadView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(UploadView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Library.getInstance().logOut(event);
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
    void changeToUserView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/UserScene/MainSceneUser.fxml"));
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

    @FXML
    void showSettings(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Settings.fxml"));
            Parent SettingView = loader1.load();
            Settings settingsController = loader1.getController();

            // Truyền đối tượng MainSceneClass vào SettingsController
            settingsController.setMainSceneController(this);  // this l
            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(SettingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void EnterToSearch(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            searchButton(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }



}
