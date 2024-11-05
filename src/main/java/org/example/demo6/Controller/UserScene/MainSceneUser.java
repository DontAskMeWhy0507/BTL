package org.example.demo6.Controller.UserScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.apiGoogleBooks;
import org.example.demo6.Controller.AdminScene.Page.SearchPageController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    private static ScrollPane staticMainScrollPane;
    @FXML
    void searchButton() throws IOException {
        List<Book> searchResults = apiGoogleBooks.searchBooks1(SearchField.getText());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/PageSearch.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            SearchPageController searchPageController = loader.getController();
            searchPageController.setSearchResults(searchResults);

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
    void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DBUltis.changescene(event, "/View/LoginScene/Login.fxml", "Login!");
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
    void searchButton(ActionEvent event) {

    }

    @FXML
    void showAll(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        staticMainScrollPane = mainScrollPane;
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

    }

}
